package com.learn.spring.framework

class LoadClassPathFile {

	static main(args){
		Properties props=new Properties()
		String location='app.properties'
		URL url=LoadClassPathFile.getResource('/'+location)
		new File(url.file).withInputStream{inStream->
			props.load(inStream)
			println inStream.dump()
			inStream.close()
		}
		println props
	}
}
