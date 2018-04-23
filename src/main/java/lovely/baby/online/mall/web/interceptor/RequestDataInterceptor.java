package lovely.baby.online.mall.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lovely.baby.online.mall.util.Constants;
import lovely.baby.online.mall.util.RequestDataHolder;
import lovely.baby.online.mall.util.RequestUtils;

@Component("requestDataInterceptor")
public class RequestDataInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        RequestDataHolder.setIP(RequestUtils.getIP(request));
        return true;
    }
}
