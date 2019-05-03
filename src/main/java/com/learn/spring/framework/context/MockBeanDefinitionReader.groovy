package com.learn.spring.framework.context

import org.codehaus.groovy.util.StringUtil

import com.learn.spring.framework.beans.MockBeanDefinition
import com.learn.spring.framework.util.StringUtils

class MockBeanDefinitionReader {
	
	String basePackageKey="basePackage"
	
	String[] locations 
	
	Properties props=new Properties()

	Set<String> beanClasses=new HashSet<>()
	
	MockBeanDefinitionReader(String ...locations){
		this.locations=locations
		doLoadScanPackage()
		
		doScan(props.getProperty(basePackageKey))
	}
	
	def doLoadScanPackage(){
		URL url=MockBeanDefinitionReader.getResource('/'+this.locations[0])
		new File(url.file).withInputStream{inStream->
			props.load(inStream)
			inStream.close()
		}
		this
	}
	
	def doScan(String basePackage){
		URL url=MockBeanDefinitionReader.getResource('/'+basePackage.replaceAll('\\.', '/'))
		File baseFile=new File(url.getFile())
		baseFile.listFiles().each{file->
			if(file.directory){
				doScan(basePackage+'.'+file.name)
			}else if(file.name.endsWith('.class')){
				String className=basePackage+'.'+file.name.replace('.class', '')
				beanClasses.add(className)
			}
		}
		this
	}
	
	
	List<MockBeanDefinition> loadBeanDefinitions(){
		List beanDefinitions=[]
		beanClasses.each({className-> 
			Class c=Class.forName(className);
			if(!c.isInterface()){
				String factoryBeanName=toLowercase(c.simpleName)
				beanDefinitions << doCreateBeanDefinition(factoryBeanName, className)
			}
		})
		beanDefinitions
	}
	private String toLowercase(String simpleName){
		StringUtils.lowerFirstLatter(simpleName)
	}
	
	private MockBeanDefinition doCreateBeanDefinition(String factoryBeanName,String beanClassName){
		MockBeanDefinition beandefinition=[beanClassName,false,factoryBeanName,true] as MockBeanDefinition
		return beandefinition
	}
}
