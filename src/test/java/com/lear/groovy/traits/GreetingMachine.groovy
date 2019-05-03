package com.lear.groovy.traits

class GreetingMachine implements Greeter{

	def static main(args){
		GreetingMachine demo=new GreetingMachine();
		demo.greet()
		assert demo.greet() == "Hello from a private method!"

		try {
			demo.greetingMessage()
		} catch (MissingMethodException e) {
			e.printStackTrace()
		}

		//实例化trait?--> not allowed
		//Groovy:You cannot create an instance from the abstract interface 'com.lear.groovy.traits.Greeter'.
		//Greeter g=new Greeter();
		//g.greet()
	}
}
