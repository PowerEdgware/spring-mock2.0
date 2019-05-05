package com.lear.groovy

class PropDemo {

	String name;
	private int id;

	static main(__args){
		PropDemo prop=new PropDemo();
		//println prop.dump()

		//println PropDemo.getProperties()
		prop.properties.each({  println it })
		println 'closure demo'
		def aClosure={ println this }
		//a closure always returns a value when called
		println aClosure.call()
		//	or	aClosure()
		println aClosure instanceof Closure
		println aClosure.class.name

		def concat1 = { String... _args -> _args.join('') }
		assert concat1('abc','def') == 'abcdef'

		def multiConcat = { int n, String... args ->
			args.join('')*n
		}
		assert multiConcat(2, 'abc','def') == 'abcdefabcdef'
	}
}
