package com.learn.spring.framework.webmvc.servlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


trait MockHandlerInterceptor {

	abstract boolean preHanlde(HttpServletRequest req,HttpServletResponse resp,Object hanlder);

	void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	MockModelAndView modelAndView){
	}
}
