package IOC_DITests

import com.learn.spring.framework.context.MockAnnotationApplicationContext
import com.learn.spring.framework.demo.DemoController
import com.learn.spring.framework.demo.DemoService



class AnnoAppContextTests {

	static main(args){
		String[] locations=['app.properties'];
		MockAnnotationApplicationContext ctx=new MockAnnotationApplicationContext(locations);
		String beanName='demoService';
		DemoService service= ctx.getBean(beanName);
		println service.class
		println service
		
		//DemoService service2=ctx.getBean(DemoService.class)
		
		DemoController controller=ctx.getBean(DemoController.class)
		
		println service==controller.service
	}
}
