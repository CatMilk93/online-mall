package lovely.baby.online.mall.util;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestUtils {

    private final ImmutableSet<String> POTENTIAL_IP_HEADER_NAMES = ImmutableSet.of("X-Real-IP", "X-Forwarded-For");

    private final String UNKNOWN_IP = "unknown";

    private final String COMMA = ",";

    private final String LOCALHOST_IN_IPV4 = "127.0.0.1";

    private final String LOCALHOST_IN_IPV6 = "0:0:0:0:0:0:0:1";

    public String getIP(HttpServletRequest request) {
        boolean foundIpInHeader = false;
        String ip = null;
        for (String ipHeaderName : POTENTIAL_IP_HEADER_NAMES) {
            ip = request.getHeader(ipHeaderName);
            if (!Strings.isNullOrEmpty(ip) && !UNKNOWN_IP.equalsIgnoreCase(ip)) {
                foundIpInHeader = true;
                break;
            }
        }
        ip = (foundIpInHeader) ? ip : request.getRemoteAddr();
        // 最后一个为RemoteAddr
        ip = StringUtils.substringAfterLast(ip, COMMA);
        if (LOCALHOST_IN_IPV6.equals(ip)) {
            ip = LOCALHOST_IN_IPV4;
        }
        return ip;
    }

    private final ImmutableSet<String> POTENTIAL_REFERER_HEADER_NAMES = ImmutableSet.of("referer", "Referer");

    @Nullable
    public String getReferer(HttpServletRequest request) {
        for (String refererHeaderName : POTENTIAL_REFERER_HEADER_NAMES) {
            String referer = request.getHeader(refererHeaderName);
            if (!Strings.isNullOrEmpty(referer)) {
                return referer;
            }
        }
        return null;
    }

    private final String AJAX_REQUEST_IDENTIFICATION_HEADER_NAME = "x-requested-with";

    private final ImmutableSet<String> POTENTIAL_AJAX_REQUEST_URI_SUFFIXES = ImmutableSet.of(".json", ".jsonp");

    public boolean isAjaxRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (request.getHeader(AJAX_REQUEST_IDENTIFICATION_HEADER_NAME) != null) {
            return true;
        }
        for (String uriSuffix : POTENTIAL_AJAX_REQUEST_URI_SUFFIXES) {
            if (uri.endsWith(uriSuffix)) {
                return true;
            }
        }
        return false;
    }
}
