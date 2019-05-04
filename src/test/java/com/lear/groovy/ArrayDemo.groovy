package com.lear.groovy

class ArrayDemo {

	static main(args){
		def list=[1, 3, 2]
		Integer[] arr=new Integer[list.size()]
		//		list.each({
		//			arr<<it
		//		})
		list.eachWithIndex{item,index->
			println index+' '+item
			arr[index]=item
		}
		println list.collect({ it}) ==list
		println arr
	}
}
