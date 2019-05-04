package com.learn.spring.framework.webmvc.servlet

class MockModelAndView {

	String viewName
	private def model=[:]
	
	MockModelAndView addModelPaparm(String key,Object value){
		this.model.put(key, value)
		this
	}	
//	static main(args){
//		def model=[:]
//		println model.getClass()?.name
//		//java.util.LinkedHashMap
//	}
}
