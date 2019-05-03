package com.learn.spring.framework.util

abstract class StringUtils {

	static String lowerFirstLatter(String instr){
		if(instr?.empty){
			return null
		}
		char[]chars=instr.toCharArray();
		if(Character.isUpperCase(chars[0])){
			chars[0]+=32
		}
		String.valueOf(chars)
	}
}
