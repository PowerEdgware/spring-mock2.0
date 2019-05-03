package com.learn.spring.framework.context


import java.util.Map
import java.util.concurrent.ConcurrentHashMap
import com.learn.spring.framework.beans.MockBeanPostProcessor
import com.learn.spring.framework.beans.MockBeanWrapper
import com.learn.spring.framework.core.MockBeanFactory

trait MockAbstractBeanFactory implements MockBeanFactory{

	private List<MockBeanPostProcessor> beanPostProcessors=[] as ArrayList;

	Map<String,Object> singletionBeansMap=new ConcurrentHashMap(16);
	Map<String,MockBeanWrapper> wrappedBeansMap=new ConcurrentHashMap(16);

	def addBeanPostProcessor(MockBeanPostProcessor processor){
		List<MockBeanPostProcessor> beanPostProcessors=this.beanPostProcessors;
		beanPostProcessors.remove(processor)
		beanPostProcessors.add(processor)
		this
	}
	
	List<MockBeanPostProcessor> getBeanPostProcessors(){
		Collections.unmodifiableList(this.beanPostProcessors)
	}
}
