package IOC_DITests.demoBean

import com.learn.spring.framework.annotation.MockAutowired

class DemoController {

	@MockAutowired
	private DemoService service;
}
