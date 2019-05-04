package com.learn.spring.framework.webmvc.servlet

import java.io.IOException

import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory

import com.learn.spring.framework.context.MockAbstractApplicationContext
import com.learn.spring.framework.context.MockAnnotationApplicationContext
import com.learn.spring.framework.webmvc.method.HandleMethod
import com.learn.spring.framework.webmvc.view.MockView
import com.learn.spring.framework.webmvc.view.MockViewResolver

class MockDispatcherServlet extends HttpServlet{

	static org.slf4j.Logger log=LoggerFactory.getLogger(MockDispatcherServlet.class)

	private String configLocation;
	private volatile MockAbstractApplicationContext appcontext;

	List<MockHandlerMapping> handlerMappings=[]
	List<MockHandlerAdapter> handlerAdapters=[]
	List<MockViewResolver> viewResolvers=[]

	void initApplicationContext(String loc){
		synchronized (this) {
			if(appcontext==null){
				this.configLocation=loc
				String[] locations=new String[1];
				locations[0]=this.configLocation
				appcontext=new MockAnnotationApplicationContext(locations)
			}
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		//初始化spring容器
		initApplicationContext(config.getInitParameter('configLocation'))

		//初始化策略组建
		initStrategies(appcontext)
	}

	//TODO  process request

	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException ,IOException {
		this.doPost(arg0, arg1)
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//preProcess
			doDispatch(req, resp);
			//post process
		} catch (Exception e) {
			resp.getWriter().println("500 Server Error " + Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取handler
		MockHandlerExecutionChain chain = getHandler(req);
		println chain
		if (chain == null) {
			// NOT FOUND
			processDispatchResult(req,resp,new MockModelAndView("404"));
			return;
		}
		MockHandlerAdapter handlerAdapter=getHandlerAdapter(chain.handler);

		chain.applyPrehandle(req, resp)

		MockModelAndView mv=handlerAdapter?.handle(req, resp, chain.handler);

		chain.applyPostHandle(req, resp)

		processDispatchResult(req, resp, mv)
	}

	MockHandlerExecutionChain getHandler(HttpServletRequest req){
		for(MockHandlerMapping mapping:handlerMappings){
			MockHandlerExecutionChain chain=mapping.getHanlder(req)
			if(log.debugEnabled){
				log.debug(
						" handler map [" + mapping + "] in MockDispatcherServlet with name '" + 'mock' + "'")
			}
			if(chain!=null){
				return chain
			}
		}
		return null
		//		this.handlerMappings?.find({
		//		})
	}

	MockHandlerAdapter getHandlerAdapter(Object handler){
		for(MockHandlerAdapter ad:handlerAdapters){
			if(log.debugEnabled){
				log.debug("Testing handler adapter [" + ad + "]")
			}
			if(ad.support(handler)){
				return ad
			}
		}
		return null
//		this.handlerAdapters?.find({
//		})
	}

	private void processDispatchResult(HttpServletRequest request,HttpServletResponse response,
			MockModelAndView mv) throws Exception {
		if(null == mv){ return;}

		if(this.viewResolvers.isEmpty()){ return;}
		if (this.viewResolvers != null) {
			for (MockViewResolver viewResolver : this.viewResolvers) {
				MockView view = viewResolver.resolveViewName(mv.getViewName());
				if (view != null) {
					view.render(request,response,mv.model);
					return;
				}
			}
		}
	}

	protected void initStrategies(MockAbstractApplicationContext  context) {
		//initMultipartResolver(context);
		//initLocaleResolver(context);
		//initThemeResolver(context);
		initHandlerMappings(context);
		initHandlerAdapters(context);
		//initHandlerExceptionResolvers(context);
		//initRequestToViewNameTranslator(context);
		initViewResolvers(context);
		//initFlashMapManager(context);
	}
	protected void initHandlerMappings(MockAbstractApplicationContext  context){

		MockHandlerMapping mapping=context.getBean(MockHandlerMapping.class);
		if(mapping==null){
			mapping=new MockHanlderMethodMapping(context)
		}
		handlerMappings<< mapping

	}

	protected void initHandlerAdapters(MockAbstractApplicationContext  context){
		MockHandlerAdapter adaptor=context.getBean(MockHandlerAdapter.class);
		if(adaptor==null){
			adaptor=new MockHandlerMethodAdapter()
		}
		handlerAdapters<< adaptor
	}

	protected void initViewResolvers(MockAbstractApplicationContext  context){
		String templateRoot = context.getMetaClass().invokeMethod(context, 'getProp', 'templatePath')
		println templateRoot
		String templateRootPath =MockDispatcherServlet.getResource('/'+templateRoot).file

		File templateRootDir = new File(templateRootPath);

		for (File template : templateRootDir.listFiles()) {
			this.viewResolvers.add(new MockViewResolver(templateRootDir));
		}
	}
}
