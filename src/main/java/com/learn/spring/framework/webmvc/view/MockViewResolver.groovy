package com.learn.spring.framework.webmvc.view

class MockViewResolver {
	
	static defaultTemplateSuffix=".html"

	File templateRootDir
	String viewName
	
	MockViewResolver(File templateRootDir){
		this.templateRootDir=templateRootDir
	}
	
	MockView resolveViewName(String viewName){
		this.viewName=viewName
		viewName=viewName?.trim()
		if(viewName?.empty){
			return null
		}
		File template=new File(templateRootDir.path+'/'+viewName+defaultTemplateSuffix)
		
		new MockView(template)
	}
}
