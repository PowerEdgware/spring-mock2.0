package com.learn.spring.framework.context

import java.lang.reflect.Field
import java.util.List
import java.util.concurrent.ConcurrentHashMap

import com.learn.spring.framework.annotation.MockAutowired
import com.learn.spring.framework.beans.MockBeanDefinition
import com.learn.spring.framework.beans.MockBeanPostProcessor
import com.learn.spring.framework.beans.MockBeanWrapper
import com.learn.spring.framework.core.MockBeanDefinitionRegistry
import com.learn.spring.framework.util.StringUtils

class MockDefaultListableBeanFactory implements MockAbstractBeanFactory,MockBeanDefinitionRegistry{

	Map<String,MockBeanDefinition> beanDefinitinMap=new ConcurrentHashMap(16);

	@Override
	public void registryBeanDifinition(String name, MockBeanDefinition beanDefinition) {
		if(beanDefinitinMap.containsKey(name)){
			return;
		}
		beanDefinitinMap.putIfAbsent(name, beanDefinition)
	}
	
	void preInstantiateSingletons(){
		//instantiate bean
		if(beanDefinitinMap.empty){
			return
		}
		beanDefinitinMap.each({entry->
			if(!entry.value.lazyInit){
				getBean(entry.key)
			}
		})
	}
	
	String[] getBeanNames(){
		Set<String> sets=this.beanDefinitinMap.keySet()
		String[] names=new String[sets.size()]
		sets.eachWithIndex({elem,index-> 
			println elem+' '+index
			names[index]=elem
		})
		names
	}

	Object getBean(Class clazz){
		String beanName=StringUtils.lowerFirstLatter(clazz.simpleName);
		getBean(beanName)
	}

	@Override
	public Object getBean(String name) {
		def instance=getCachedInstace(name)
		if(instance!=null){
			return instance
		}
		instance=instantiateBean(name)
		if(instance==null){
			return null;
		}
		applyBeanPostProcessorBeforeInitialization(name,instance);

		MockBeanWrapper beanWrapper=new MockBeanWrapper(beanInstance:instance,wrappedClass:instance.class)
		this.wrappedBeansMap.putIfAbsent(name, beanWrapper)

		populateBean(name,instance)

		applyBeanPostProcessorAfterInitialization(name,instance);
		
		return wrappedBeansMap.get(name).beanInstance;
	}

	void applyBeanPostProcessorBeforeInitialization(String name,Object instance){
		List<MockBeanPostProcessor> processors=this.getBeanPostProcessors();
		processors?.each({processor-> 
			processor.postProcessBeforeInintialization(instance, name)
		})
	}

	void applyBeanPostProcessorAfterInitialization(String name,Object instance){
		List<MockBeanPostProcessor> processors=this.getBeanPostProcessors();
		processors?.each({processor->
			processor.postProcessAfterInintialization(instance, name)
		})
	}

	private void populateBean(String name,Object instance){
		Class<?> clazz=instance.class;
		Field[] fileds=clazz.declaredFields;
		fileds?.each({filed->
			if(filed.isAnnotationPresent(MockAutowired.class)){
				MockAutowired anno=filed.getAnnotation(MockAutowired.class)
				String value=anno.value();
				if(value.empty){
					value=filed.type.simpleName
				}
				filed.accessible=true;
				filed.set(instance, getCachedInstace(StringUtils.lowerFirstLatter(value)))
			}
		})
	}

	private def instantiateBean(String name){
		MockBeanDefinition mbd=this.beanDefinitinMap.get(name)
		String className=mbd?.beanClassName
		if(className==null||className?.empty){
			return null;
		}
		Object instance=Class.forName(className).newInstance();
		add2Cache(name,instance)
		instance
	}

	protected def add2Cache(String name,Object instance){
		this.singletionBeansMap.put(name, instance);
	}

	protected Object getCachedInstace(String beanName){
		return this.singletionBeansMap.get(beanName);
	}
}
