package com.learn.spring.framework.webmvc.servlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MockHandlerExecutionChain {

	Object handler
	
	List<MockHandlerInterceptor> interceptors=[]
	
	boolean applyPrehandle(HttpServletRequest req,HttpServletResponse resp){
		interceptors?.each({interceptor->
			if(!interceptor.preHanlde(req, resp, handler)){
				return false
			}
		})
		true
	}
	
	void applyPostHandle(HttpServletRequest req,HttpServletResponse resp){
		//TODO
	}
	
	def getHandler(){
		this.handler
	}
}
