package com.lear.groovy.a

import org.w3c.dom.NameList

import groovy.transform.PackageScope

class GroovyDemo {

	//@PackageScope//only package  visibility
	String name='abc'//the property is automatically made visible
	//Groovy will then generate the getters/setters appropriately. 
	//Instead, it is used to create a property, that is to say a private field, an associated getter and an associated setter.

	def static main(String[] args){

		GroovyDemo groovyDemo=new GroovyDemo();
		println groovyDemo.name
		//1、由{}包围起来的代码块就是闭包
		println {}//com.lear.groovy.GroovyDemo$_main_closure1@5e4c8041

		//带名字的闭包
		def aClosure = { println "Hello Closure!" }

		println aClosure;//   结论： 闭包是一个匿名内部类的对象。
		//com.lear.groovy.GroovyDemo$_main_closure1@3cd5c106

		//调用闭包
		aClosure.call();//Or aClosure();
		//结论：闭包是一个可执行的代码块。

		//参数化闭包
		def bClosure={ println("hello ${it}")//--it 是闭包的单个隐含参数。
		}
		//调用
		bClosure "clat"//输出：hello clat

		//2.闭包参数
		def cClosure={name,age->
			println "${name},${age}"
		}
		cClosure.call("allger",15)
		//闭包可以作为方法的参数
		def name="aaa"
		def dClosure={ println name }
		aMethod(dClosure)
		//闭包可以返回值
		def eClosure={num->
			return num*2;
		}
		println eClosure(5)

		//集合操作
		ClosureListAndString()

	}
	//闭包与集合、字符串
	static void ClosureListAndString(){
		//TODO 列表/映射/范围/String/
		def nameList=['abc', 'all', 'ols'];
		println nameList.class//默认 class java.util.ArrayList
		nameList.each{item-> println item +"," }

		//遍历Map,Range,GString
		def demoMap=[1:'abc',2:'defc']
		//Object
		println demoMap.class+" -----------"
		demoMap.each{entry->
			println entry.key+"\t"+entry.value
		}
		//遍历GString
		'allger'.each{w-> println w }
	}
	def static aMethod(Closure c){
		c()
	}
}
