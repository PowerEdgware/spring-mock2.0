package com.lear.groovy

class ConveniceTest {
	String name
	
	def walk(int miles){
		println("Walking $miles miles...")
	}

	static main(args) {
		//println "git help".execute().text
		//println "git help".execute().getClass().name
		println "java -v".execute().text
		
		println "abc".dump()
		
		//间接调用方法
		ConveniceTest test=new ConveniceTest();
		test.invokeMethod("walk", 50)
		
		//setname
		test.invokeMethod("setName", 'acc')
		
		println test.name
	}
	
	

}
