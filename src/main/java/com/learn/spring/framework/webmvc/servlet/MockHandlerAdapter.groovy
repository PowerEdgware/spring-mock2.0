package com.learn.spring.framework.webmvc.servlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.learn.spring.framework.webmvc.method.HandleMethod

trait MockHandlerAdapter {
	
	abstract boolean support(Object handler)
	

	abstract MockModelAndView handle(HttpServletRequest req,HttpServletResponse resp,Object handler)
	
}
