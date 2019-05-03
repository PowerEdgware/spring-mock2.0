package com.learn.spring.framework.beans

class MockBeanDefinition {
	//default public and auto generate getter/setter
	String beanClassName
	boolean lazyInit=false
	String factoryBeanName
	boolean isSingleton=true
	
	MockBeanDefinition(String beanName,boolean lazyInit,String factoryName,boolean isSingleton){
		this.beanClassName=beanName
		this.lazyInit=lazyInit
		this.factoryBeanName=factoryName
		this.isSingleton=isSingleton
	}
}
