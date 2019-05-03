package com.learn.spring.framework.beans

trait MockBeanPostProcessor {

	def postProcessBeforeInintialization(Object bean,String beanName){
	}

	def postProcessAfterInintialization(Object bean,String beanName){
	}
}
