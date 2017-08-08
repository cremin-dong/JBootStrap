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

    @Value("#{X-PJAX-Version}")
    private String X_PJAX_VERSION = "";


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
            model.addAttribute("X_PJAX_VERSION", X_PJAX_VERSION);// 设置当前页面的pjax版本
            model.addAttribute("isPjax", false);
            if (isPjax) {
                model.addAttribute("isPjax", true);
            }
        }
    }
}