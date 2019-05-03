package com.learn.spring.framework.demo

import com.learn.spring.framework.annotation.MockAutowired

class DemoController {

	@MockAutowired
	DemoService service;
}
