package com.learn.spring.framework.webmvc.servlet

import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern

import javax.servlet.http.HttpServletRequest

import org.slf4j.LoggerFactory

import com.learn.spring.framework.annotation.MockController
import com.learn.spring.framework.annotation.MockRequestMapping
import com.learn.spring.framework.context.MockAbstractApplicationContext
import com.learn.spring.framework.webmvc.method.HandleMethod

class MockHanlderMethodMapping implements MockHandlerMapping{
	static org.slf4j.Logger log=LoggerFactory.getLogger(MockHandlerMapping.class)

	MockAbstractApplicationContext context

	private Map<String,HandleMethod> urlHandlerMethodMap=new ConcurrentHashMap(16);

	MockHanlderMethodMapping(MockAbstractApplicationContext context){
		this.context=context
		initHandlerMethods()
	}

	//寻找所有的handlerMethod
	protected void initHandlerMethods(){
		String[] beanNames=obtainApplicationContext().beanNames
		beanNames.each({
			String beanName=it
			Object beanInstance=obtainApplicationContext().getBean(beanName)
			Class beanType=beanInstance.class
			if(isHandler(beanType)){
				detectHandlerMethods(beanInstance)
			}
		})
	}

	private void detectHandlerMethods(Object handler){
		Class beanType=handler.class
		String baseUrl=''
		if(beanType.isAnnotationPresent(MockRequestMapping.class)){
			baseUrl+=beanType.getAnnotation(MockRequestMapping.class)
		}
		Method[]methods=beanType.declaredMethods
		methods.each({
			if(it.isAnnotationPresent(MockRequestMapping.class)){
				String value=it.getAnnotation(MockRequestMapping.class).value()
				String regex = ("/" + baseUrl + value.replaceAll("\\*",
						".*")).replaceAll("/+", "/");
				Pattern pattern = Pattern.compile(regex);

				registerHandlerMethod(regex, it, handler, pattern)
			}
		})
	}
	private void registerHandlerMethod(String url,Method method,Object instance,Pattern pattern){
		if(urlHandlerMethodMap.containsKey(url)){
			return
		}
		HandleMethod handleMethod=new HandleMethod(url,method,instance,pattern);
		if(log.infoEnabled){
			log.info("Mapping url='"+url+"' on method="+method )
		}
		urlHandlerMethodMap.put(url, handleMethod)
	}
	private boolean isHandler(Class beanType){
		return beanType?.isAnnotationPresent(MockController.class)
	}

	MockAbstractApplicationContext obtainApplicationContext(){
		this.context
	}

	@Override
	public Object getHandlerInternel(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		uri = uri.replace(contextPath, "").replaceAll("/+", "/");

		for(String url:urlHandlerMethodMap.keySet()){
			def hm=urlHandlerMethodMap.get(url);
			if(hm.pattern.matcher(uri).matches()){
				return hm
			}
		}
		return null;
	}

}
