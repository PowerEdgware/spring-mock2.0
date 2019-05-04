package com.learn.spring.framework.demo

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.learn.spring.framework.annotation.MockAutowired
import com.learn.spring.framework.annotation.MockController
import com.learn.spring.framework.annotation.MockRequestMapping
import com.learn.spring.framework.annotation.MockRequestParam
import com.learn.spring.framework.webmvc.servlet.MockModelAndView

@MockController
class DemoController {

	@MockAutowired
	DemoService service;

	@MockRequestMapping('/hello')
	MockModelAndView someMethod(HttpServletRequest req,HttpServletResponse resp,@MockRequestParam("name")String name){
		MockModelAndView mv=new MockModelAndView();
		mv.viewName='hello'
		mv.addModelPaparm("name", name)
				.addModelPaparm("msg", service.someMethod("hello", name))
				.addModelPaparm("token", UUID.randomUUID().toString())

		mv
	}
}
