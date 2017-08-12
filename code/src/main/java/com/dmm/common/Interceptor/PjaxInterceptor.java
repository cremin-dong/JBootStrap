package com.dmm.common.Interceptor;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Enumeration;

/**
 * Created by cremin on 2017/8/7.
 */
@Configurable
public class PjaxInterceptor extends HandlerInterceptorAdapter {

    @Value("#{X_PJAX_VERSION}")
    private String X_PJAX_VERSION = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    /**
     * Controller 方法调用之后，页面渲染前执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            boolean isPjax = Boolean.parseBoolean(request.getHeader("X-PJAX"));// 值为true表示pjax请求，这是重点
            ModelMap model = modelAndView.getModelMap();
            model.addAttribute("X-PJAX-VERSION", X_PJAX_VERSION);// 设置当前页面的pjax版本
            model.addAttribute("isPjax", false);
            if (isPjax) {
                model.addAttribute("isPjax", true);
            }

            setBasePath(request);

        }
    }


    /**
     * 设定项目basePath
     * @param request
     */
    private void setBasePath(HttpServletRequest request) {
        if(request.getServletContext().getAttribute("basePath") == null){
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            request.getServletContext().setAttribute("basePath",basePath);
        }
    }

}