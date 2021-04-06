package eon.hg.fap.core.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import eon.hg.fap.common.properties.ExternalProperties;
import eon.hg.fap.core.exception.ExceptionUtil;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.support.session.UserContext;
import eon.hg.fap.support.session.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller控制器全局拦截
 */
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter {

    public final static String REQUEST_MENU_KEY = "menu_id";
    public final static String REQUEST_CUR_MENU_KEY = "cur_menu_id";
    //执行较慢时间
    @Autowired
    private ExternalProperties externalProperties;

    private NamedThreadLocal<Long> beginTimeThreadLocal =  new NamedThreadLocal<>("ReqWatch-Mills");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();//1、开始时间
        beginTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
        refreshUserContext(request);
        log.info("start execute [{}:{}]",request.getRequestURI(),request.getMethod());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();//2、结束时间
        long beginTime = beginTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;//3、消耗的时间
        if( consumeTime > externalProperties.getSlow_req_millis() ) {
            log.warn("execute [{}] end too slow,cost: {} ms",request.getRequestURI(),consumeTime);
        }else {
            log.info("execute [{}] end,cost: {} ms",request.getRequestURI(),consumeTime);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        beginTimeThreadLocal.remove();
        // 清理用户上下文
        UserContextHolder.remove();
        // 清理异常上下文
        ExceptionUtil.remove();
    }

    public void refreshUserContext(HttpServletRequest request) {
        OnlineUser user = SecurityUserHolder.getOnlineUser();
        if (user==null) {
            return;
        }
        UserContext userContext = ReflectUtil.newInstance(externalProperties.getUser_context_class());
        String menuId = request.getParameter(REQUEST_MENU_KEY);
        if(StringUtil.isBlank(menuId) || "undefined".equals(menuId)) {
            menuId = request.getParameter(REQUEST_CUR_MENU_KEY);
            if(StringUtil.isBlank(menuId) || "undefined".equals(menuId)) {
                menuId = "-1";
            }
        }
        userContext.setOnlineUser(user);
        userContext.setMenuId(Convert.toLong(menuId));
        UserContextHolder.setUserContext(userContext);
    }

}
