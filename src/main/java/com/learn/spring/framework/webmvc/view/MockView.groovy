package com.learn.spring.framework.webmvc.view

import java.util.regex.Matcher
import java.util.regex.Pattern

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MockView {

	static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
	File viewFile;
	public GPView(File viewFile){
		this.viewFile = viewFile;
	}
	public String getContentType(){
		return DEFAULT_CONTENT_TYPE;
	}

	void render(HttpServletRequest req,HttpServletResponse resp,Map<String,?> model){
		StringBuilder buf=new StringBuilder()
		viewFile.eachLine('utf-8',{line->
			line = new String(line.getBytes("ISO-8859-1"),"utf-8");
			Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(line);

			while (matcher.find()) {
				String paramName = matcher.group();
				paramName = paramName.replaceAll("￥\\{|\\}","");
				Object paramValue = model.get(paramName);
				if (null == paramValue) { continue; }
				//要把￥{}中间的这个字符串给取出来
				line = matcher.replaceFirst(paramValue.toString());
				matcher = pattern.matcher(line);
			}
			buf.append(line);
		})
	}

}
