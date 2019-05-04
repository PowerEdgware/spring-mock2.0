package com.learn.spring.framework.demo

import java.time.LocalDateTime

import org.slf4j.LoggerFactory

class DemoService {

	static org.slf4j.Logger log=LoggerFactory.getLogger(DemoService.class)

	def someMethod(String msg,String name){
		LocalDateTime now=LocalDateTime.now();
		log.info("Revevied msg="+msg+" at="+now)
		'hi,'+name+' request Received at='+now
	}
}
