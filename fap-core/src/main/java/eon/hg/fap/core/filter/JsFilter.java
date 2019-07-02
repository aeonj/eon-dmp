package eon.hg.fap.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * js脚本注入转义过滤器
 * @author AEON
 *
 */
public class JsFilter implements Filter {

	 @Override
     public void destroy() {
    }

     @Override
     public void doFilter(ServletRequest req, ServletResponse res,
                          FilterChain chain) throws IOException, ServletException {
             HttpServletRequest request = (HttpServletRequest)req;
             JsRequest wrapRequest= new JsRequest(request,request.getParameterMap());
             chain.doFilter(wrapRequest, res);
    }

     @Override
     public void init(FilterConfig arg0) throws ServletException {
    }

}
