package lovely.baby.online.mall.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Charsets;

import lovely.baby.online.mall.util.AESEncryptUtils;
import lovely.baby.online.mall.util.Constants;
import lovely.baby.online.mall.util.RequestDataHolder;

@Component("loginInterceptor")
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (Constants.CookieNames.TOKEN.equals(cookie.getName())) {
                try {
                    RequestDataHolder.setUsername(AESEncryptUtils.decrypt(cookie.getValue(), Charsets.UTF_8));
                    return true;
                } catch (Exception e) {
                    break;
                }
            }
        }
        if (!request.getRequestURI().startsWith("/user")) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
