package com.lear.groovy.traits
//similar to interface||Traits may implement interfaces, in which case the interfaces are declared using the implements keyword:
//Traits only support public and private methods.
trait Greeter {
	private String greetingMessage() {
		'Hello from a private method!'
	}

	String greet(){
		def msg=greetingMessage()
		println msg
		msg
	}

}
