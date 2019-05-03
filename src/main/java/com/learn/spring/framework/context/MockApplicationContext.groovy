package com.learn.spring.framework.context

import com.learn.spring.framework.core.MockBeanFactory

interface MockApplicationContext extends MockBeanFactory{

	long getStartupDate();
	
	MockBeanFactory getBeanFactory()
	
}
