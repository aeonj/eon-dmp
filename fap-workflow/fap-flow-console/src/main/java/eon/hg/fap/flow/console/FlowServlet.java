package eon.hg.fap.flow.console;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.flow.console.handler.ServletHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FlowServlet extends HttpServlet{
	public static final String URL="/flow";

	private Map<String,ServletHandler> handlerMap=new HashMap<String,ServletHandler>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext applicationContext=getWebApplicationContext(config);
		Collection<ServletHandler> handlers=applicationContext.getBeansOfType(ServletHandler.class).values();
		for(ServletHandler handler:handlers){
			String url=handler.url();
			if(handlerMap.containsKey(url)){
				throw new RuntimeException("Handler ["+url+"] already exist.");
			}
			handlerMap.put(url, handler);
		}
	}
	
	protected WebApplicationContext getWebApplicationContext(ServletConfig config){
		return WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{
			String path=req.getContextPath()+URL;
			String uri=req.getRequestURI();
			String targetUrl=uri.substring(path.length());
			if(targetUrl.length()<1){
				resp.sendRedirect(req.getContextPath()+"/uflo/todo");
				return;
			}
			int slashPos=targetUrl.indexOf("/",1);
			if(slashPos>-1){
				targetUrl=targetUrl.substring(0,slashPos);
			}
			ServletHandler targetHandler=handlerMap.get(targetUrl);
			if(targetHandler==null){
				outContent(resp,"Handler ["+targetUrl+"] not exist.");
				return;
			}
			targetHandler.execute(req, resp);
		}catch(Exception ex){
			Throwable e=getCause(ex);
			resp.setCharacterEncoding("UTF-8");
			PrintWriter pw=resp.getWriter();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			String errorMsg = e.getMessage();
			if(StrUtil.isBlank(errorMsg)){
				errorMsg=e.getClass().getName();
			}
			pw.write(errorMsg);
			pw.close();
			throw new ServletException(ex);				
		}
	}
	
	private Throwable getCause(Throwable e){
		if(e.getCause()!=null){
			return getCause(e.getCause());
		}
		return e;
	}

	private void outContent(HttpServletResponse resp,String msg) throws IOException {
		resp.setContentType("text/html");
		PrintWriter pw=resp.getWriter();
		pw.write("<html>");
		pw.write("<header><title>Uflo Console</title></header>");
		pw.write("<body>");
		pw.write(msg);
		pw.write("</body>");
		pw.write("</html>");
		pw.flush();
		pw.close();
	}
}
