package com.learn.spring.framework.core

import com.learn.spring.framework.beans.MockBeanDefinition

interface MockBeanDefinitionRegistry {

	void registryBeanDifinition(String name,MockBeanDefinition beanDefinition)
}
