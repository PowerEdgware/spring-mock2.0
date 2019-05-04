package com.learn.spring.framework.webmvc.method

import java.lang.reflect.Executable
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.regex.Pattern

import com.learn.spring.framework.annotation.MockRequestParam

class HandleMethod {

	String url;
	Method method;
	Object instance;
	Pattern pattern;
	
	MethodParameter[] methodParameters;

	public HandleMethod(String url, Method method, Object instance, Pattern pattern) {
		this.url = url;
		this.method = method;
		this.instance = instance;
		this.pattern = pattern;
		// 解析方法参数，方便后续请求匹配||获取方法名称和顺序映射
		methodParameters = initMethodParameter();
	}
	private MethodParameter[] initMethodParameter() {
		Parameter[] parameters = this.method.getParameters();
		MethodParameter[] methodParameters = new MethodParameter[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			// 获取注解
			Parameter parameter = parameters[i];
			MethodParameter methodParameter = new MethodParameter(i);
			methodParameter.parameterType = parameter.getType();
			//TODO 不一定有name,需要asm动态获取并缓
			if (parameter.isAnnotationPresent(MockRequestParam.class)) {
				methodParameter.name = parameter.getAnnotation(MockRequestParam.class).value();
			}
			methodParameters[i] = methodParameter;

		}
		return methodParameters;
	}
	
	class MethodParameter {
		int index;
		Class<?> parameterType;
		String name;
		Executable executable;

		public MethodParameter(int i) {
			this.index = i;
			this.executable = HandleMethod.this.method;
		}

		public Class<?> getParameterType() {
			return this.parameterType;
		}

		public String getParameterName() {
			return this.name;
		}
	}
}
