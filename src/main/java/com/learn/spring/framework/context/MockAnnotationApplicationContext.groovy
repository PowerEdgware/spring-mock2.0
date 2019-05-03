package com.learn.spring.framework.context

import com.learn.spring.framework.beans.MockBeanDefinition
import com.learn.spring.framework.beans.MockBeanPostProcessor

class MockAnnotationApplicationContext extends MockAbstractApplicationContext{

	String [] configLocations;
	
	MockBeanDefinitionReader reader;
	
//	MockAnnotationApplicationContext(String location){
//		String[] _locations=[location] as String[]
//		this('c');
//	}
	
	MockAnnotationApplicationContext(String... locations){
		this.configLocations=locations;
		this.reader=initBeanDefinitionReader()
		
		refresh()
	}
	
	def initBeanDefinitionReader(){
		def reader=new MockBeanDefinitionReader(configLocations)
		reader
	}
	
	@Override
	protected void postProcessBeanFactory(MockDefaultListableBeanFactory beanFactory) {
		//TODO beanFactory.addBeanPostProcessor(new MockBeanPostProcessor())
		
	}

	@Override
	public void loadBeanDefinitions(MockDefaultListableBeanFactory beanFactory) {
		List<MockBeanDefinition> beanDefinitions=reader.loadBeanDefinitions()
		beanDefinitions.each({beanDefinition-> 
			beanFactory.registryBeanDifinition(beanDefinition.factoryBeanName, beanDefinition)
		})
	}
}
