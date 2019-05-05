package com.lear.groovy

//A closure is an instance of the groovy.lang.Closure class
//A closure definition follows this syntax:
//{ [closureParameters -> ] statements }
class ClosureDemo {

	static main(args){
		//3. Delegation strategy
		//3.1. Groovy closures vs lambda expressions
		//Groovy defines closures as instances of the Closure class. It makes it very different from lambda expressions in Java 8.
		//3.2. Owner, delegate and this
		/*
		 *TODO 3.2.1. The meaning of this
		 * this: corresponds to the enclosing class where the closure is defined
		 In a closure, calling getThisObject will return the enclosing class where the closure is defined. It is equivalent to using an explicit this:
		 */

		//		def enc=[] as Enclosing
		//		enc.run()
		println '------------3.2.1. The meaning of this-------------------'
		def aClosure={this} //eq {getThisObject()}
		def rst= aClosure.call()
		println rst//class com.lear.groovy.ClosureDemo
		println rst instanceof Class//true
		def enclosingInstance=new ClosureDemo()
		println rst ==enclosingInstance

		def p = new Person(name:'Janice', age:74)
		assert p.dump() == 'Janice is 74 years old'

		/*
		 *TODO 3.2.2. Owner of a closure
		 * owner:corresponds to the enclosing object where the closure is defined, which may be either a class or a closure
		 * The owner of a closure is very similar to the definition of this in a closure with a subtle difference:
		 *  it will return the direct enclosing object, be it a closure or a class:
		 */
		println '------------3.2.2. Owner of a closure-------------------'
		def bClosure={getOwner()}
		println bClosure() //class com.lear.groovy.ClosureDemo

		def outerClosure={
			def innerClosure={owner}
			innerClosure.call()
		}
		println outerClosure.call()
		println outerClosure.call()==outerClosure //true

		/*
		 *TODO 3.2.3. Delegate of a closure 
		 * delegate:the delegate is a user defined object that a closure will use. By default, the delegate is set to owner
		 * The delegate of a closure can be accessed by using the delegate property or calling the getDelegate method.
		 */
		println '------------3.2.3. Delegate of a closure -------------------'
		def delegateC1={getDelegate()}
		def delegateC2={delegate}
		println delegateC1() //class com.lear.groovy.ClosureDemo

		println delegateC1.call()==this//true

		def enclosed={
			{-> delegate}.call()
		}
		println enclosed()==enclosed//true
		println enclosed() instanceof Closure//true

		//The delegate of a closure can be changed to any object.
		def delegate1=new Delegate1(name:'ABC')
		def delegate2=new Delegate2(name:'CDE')

		def upperCasedNameClosure={delegate.name.toLowerCase()}
		println upperCasedNameClosure()//com.lear.groovy.closuredemo

		upperCasedNameClosure.delegate=delegate1//change upperCasedNameClosure 's delegate to delegate1
		//do Call()
		println upperCasedNameClosure() //abc

		upperCasedNameClosure.delegate=delegate2//change upperCasedNameClosure 's delegate to delegate2
		//do Call()
		println upperCasedNameClosure() //cde

		//TODO 3.2.4. Delegation strategy
		/*
		 * in a closure, a property is accessed without explicitly setting a receiver object, then a delegation strategy is involved:
		 */
		/**
		 * Closure.OWNER_FIRST is the default strategy. If a property/method exists on the owner, then it will be called on the owner. If not, then the delegate is used.
		 Closure.DELEGATE_FIRST reverses the logic: the delegate is used first, then the owner
		 Closure.OWNER_ONLY will only resolve the property/method lookup on the owner: the delegate will be ignored.
		 Closure.DELEGATE_ONLY will only resolve the property/method lookup on the delegate: the owner will be ignored.
		 Closure.TO_SELF can be used by developers who need advanced meta-programming techniques and wish to implement a custom resolution strategy: the resolution will not be made on the owner or the delegate but only on the closure class itself. It makes only sense to use this if you implement your own subclass of Closure.
		 */
		println '------------3.2.4. Delegation strategy-------------------'
		def person = new Person1(name1:'Igor')
		def cl={name1.toUpperCase()}
		try {
			println cl() //use default delegate strategy output with err:Caught: groovy.lang.MissingPropertyException: No such property: name1 for class: com.lear.groovy.ClosureDemo
		} catch (Exception e) {
			//e.printStackTrace()
			println e
		}

		//change delegate to person
		cl.delegate=person
		println cl() //it is success output:IGOR
		
		//1.the default "owner first" strategy code
		def ownerPerson=new OwnerPerson(name:'Sarah')
		def ownerThing=new OwnerThing(name:'Teapot')
		assert ownerPerson.toString()=='My name is Sarah'//test ok
		//change delegate
		ownerPerson.pretty.delegate=ownerThing
		assert ownerPerson.toString() == 'My name is Sarah' //test OK
		//conclusion: 	there is no change in the result: name is first resolved on the owner of the closure
		
		//2.change the resolution strategy of the closure
		ownerPerson.pretty.resolveStrategy = Closure.DELEGATE_FIRST//
		//GroovyObjectSupport
		println ownerPerson.pretty.delegate //com.lear.groovy.OwnerThing@4ac3c60d
		assert ownerPerson.toString() == 'My name is Teapot'//test ok
		
		//3.delegate only demo
		def onlyPerson=new StrategyOnlyPerson(name:'allger',age:30)
		def onlyThing=new StrategyOnlyThing(name:'onlyThing')
		def personClosure=onlyPerson.fetchAge
		personClosure.delegate=onlyPerson
		assert personClosure() ==30
		
		personClosure.delegate=onlyThing
		
		assert personClosure() ==30
		
		//make strategy DELEGATE_ONLY
		personClosure.resolveStrategy=Closure.DELEGATE_ONLY
		personClosure.delegate=onlyPerson
		assert personClosure() ==30//OK
		
		personClosure.delegate=onlyThing
		try {
			personClosure.call()
		} catch (MissingPropertyException e) {
			println e//groovy.lang.MissingPropertyException: No such property: age for class: com.lear.groovy.StrategyOnlyThing
		}
		
		

	}


	/*
	 * 	in case of nested closures, like here cl being defined inside the scope of nestedClosures
	 then this corresponds to the closest outer class, not the enclosing closure!
	 */
	class NestedClosures {
		void run() {
			def nestedClosures = {
				def cl = { this }
				cl()
			}
			assert nestedClosures() == this
		}

		void run1() {
			def nestedClosures = {
				def cl = { owner }
				cl()
			}
			assert nestedClosures() == nestedClosures
		}
	}

	class Enclosing {
		void run() {
			def whatIsThisObject = { getThisObject() }
			assert whatIsThisObject() == this
			def whatIsThis = { this }
			assert whatIsThis() == this
		}
	}
}

class Person {
	String name
	int age
	String toString() { "$name is $age years old" }

	String dump() {
		def cl = {
			String msg = this.toString()
			println msg
			msg
		}
		cl()
	}
}

//delegate demo class
class Delegate1{
	String name
}

class Delegate2{
	String name
}
//3.2.4. Delegation strategy
class Person1{
	String name1
}

//Let’s illustrate the default "owner first" strategy with this code:
class OwnerPerson{
	String name
	def pretty={"My name is $name"}
	String toString(){
		//call closure
		pretty()
	}
}
class OwnerThing{
	String name
}
//end owner first

//strategy only demo
class StrategyOnlyPerson{
	String name
	int age
	def fetchAge={age}
}

class StrategyOnlyThing{
	String name
}