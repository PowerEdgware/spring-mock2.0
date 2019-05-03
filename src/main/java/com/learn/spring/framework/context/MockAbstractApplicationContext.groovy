package com.learn.spring.framework.context


import com.learn.spring.framework.core.MockBeanDefinitionRegistry
import com.learn.spring.framework.core.MockBeanFactory
import com.learn.spring.framework.util.StringUtils

abstract class MockAbstractApplicationContext implements MockApplicationContext{
	long startUpDate;

	private MockDefaultListableBeanFactory beanFactory

	MockAbstractApplicationContext(){
	}

	@Override
	long getStartupDate() {
		startUpDate
	}
	def updateStartUpTime(){
		this.startUpDate=System.currentTimeMillis()
	}

	final void refresh(){
		updateStartUpTime()

		MockBeanFactory beanFactory=getBeanFactory()

		refreshBeanFactry()

		postProcessBeanFactory(this.beanFactory)

		//instantiate
		instantiateNoneLazyBean()

		finishRefresh();
	}

	@Override
	MockBeanFactory getBeanFactory() {
		if(beanFactory==null){
			beanFactory=new MockDefaultListableBeanFactory()
		}
		beanFactory
	}
	def refreshBeanFactry(){
		loadBeanDefinitions(beanFactory)
		this
	}

	protected void postProcessBeanFactory(MockDefaultListableBeanFactory beanFactory){
	}


	void instantiateNoneLazyBean(){
		beanFactory.preInstantiateSingletons()
	}

	protected finishRefresh(){
	}

	public <T> T getBean(Class<T> clazz){
		String beanName=StringUtils.lowerFirstLatter(clazz.simpleName);
		getBeanFactory().getBean(beanName);
	}

	Object getBean(String name){
		getBeanFactory().getBean(name);
	}


	abstract void loadBeanDefinitions(MockDefaultListableBeanFactory beanFactory)
}
