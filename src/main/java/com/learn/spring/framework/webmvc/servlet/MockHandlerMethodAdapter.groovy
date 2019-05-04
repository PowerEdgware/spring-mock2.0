package com.learn.spring.framework.webmvc.servlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.learn.spring.framework.webmvc.method.HandleMethod
import com.learn.spring.framework.webmvc.method.HandleMethod.MethodParameter
import java.lang.reflect.Method

class MockHandlerMethodAdapter implements MockHandlerAdapter{

	@Override
	public boolean support(Object handler) {
		handler instanceof HandleMethod
	}

	@Override
	public MockModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
		HandleMethod handleMethod=(HandleMethod)handler;

		Object[] _args=getMethodArgValues(handleMethod, req, resp)
		
		Object retValue=doInvoke(handleMethod.instance,handleMethod.method,_args)
		
		//TODO
		MockModelAndView mv=processRetValue(retValue)
		return mv
	}
	
	def doInvoke(Object handler,Method method,Object[] _args){
		method.accessible=true
		method.invoke(handler, _args)
	}
	
	MockModelAndView processRetValue(Object retValue){
		if(!(retValue instanceof MockModelAndView)){
			return null
		}
		return (MockModelAndView)retValue
	}

	private Object[] getMethodArgValues(HandleMethod hm, HttpServletRequest req, HttpServletResponse resp) {
		MethodParameter[] methodParameters = hm.methodParameters;
		Object[] args = new Object[methodParameters.length];
		for (int i = 0; i < methodParameters.length; i++) {
			MethodParameter methodParameter = methodParameters[i];
			if (HttpServletRequest.class.isAssignableFrom(methodParameter.parameterType)) {
				args[i] = req;
			} else if (HttpServletResponse.class.isAssignableFrom(methodParameter.parameterType)) {
				args[i] = resp;
			} else {
				String paramName = methodParameter.getParameterName();
				String[] values = req.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					args[i] = convertValueIfNecessary(methodParameter.getParameterType(), values[0]);
				}
			}
		}
		return args;
	}

	private Object convertValueIfNecessary(Class<?> parameterType, String value) {
		if (parameterType == String.class) {
			return value;
		} else if (parameterType == int.class || parameterType == Integer.class) {
			return Integer.parseInt(value);
		}
		// and so on
		return null;
	}
}
