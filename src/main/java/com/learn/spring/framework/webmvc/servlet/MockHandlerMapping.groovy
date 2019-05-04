package com.learn.spring.framework.webmvc.servlet

import javax.servlet.http.HttpServletRequest

trait MockHandlerMapping {

	MockHandlerExecutionChain getHanlder(HttpServletRequest req){
		//TODO
		Object handler=getHandlerInternel(req);
		if(handler instanceof MockHandlerExecutionChain){
			return handler
		}
		MockHandlerExecutionChain chain=new MockHandlerExecutionChain(handler:handler,interceptors:[])
		chain
	}

	abstract def getHandlerInternel(HttpServletRequest req)
	
}
