package lovely.baby.online.mall.web.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.model.ResponseObj;
import lovely.baby.online.mall.model.exception.InvalidParamException;
import lovely.baby.online.mall.model.exception.MallException;
import lovely.baby.online.mall.util.Constants;
import lovely.baby.online.mall.util.RequestDataHolder;
import lovely.baby.online.mall.util.RequestUtils;

@Slf4j
@ControllerAdvice
public class ExceptionResolver {

    private static final ImmutableMap<Class<? extends Exception>, ResolverPropertiesHolder> EXCEPTION_CLASS_2_RESOLVER_PROPERTIES_HOLDER_MAP = ImmutableMap
            .<Class<? extends Exception>, ResolverPropertiesHolder> builder() //
            .put(InvalidParamException.class, new ResolverPropertiesHolder(Constants.ResponseErrCodes.INVALID_PARAM)) //
            .build();

    private static final ResolverPropertiesHolder DEFAULT_RESOLVER_PROPERTIES_HOLDER = new ResolverPropertiesHolder();

    @ExceptionHandler
    @ResponseBody
    protected Map<String, ?> handle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        (((ex instanceof MallException) && ((MallException) ex).isExpectedException())
                ? Constants.businessExceptionLogger
                : log).error("URL = {}, referer = {}, ip = {}", request.getRequestURI(),
                        RequestUtils.getReferer(request), RequestDataHolder.getIP(), ex);
        ResolverPropertiesHolder resolverPropertiesHolder = MoreObjects.firstNonNull(
                EXCEPTION_CLASS_2_RESOLVER_PROPERTIES_HOLDER_MAP.get(ex.getClass()),
                DEFAULT_RESOLVER_PROPERTIES_HOLDER);
        response.setStatus(resolverPropertiesHolder.getHttpStatus().value());
        return ResponseObj.createFailureResponseObj(resolverPropertiesHolder.getErrCode(),
                (resolverPropertiesHolder.isRetExceptionMsg()) ? ex.getMessage() : null).toMap();
    }

    @Getter
    private static class ResolverPropertiesHolder {

        private final HttpStatus httpStatus;

        private final int errCode;

        private final boolean retExceptionMsg;

        public ResolverPropertiesHolder(HttpStatus httpStatus, int errCode, boolean retExceptionMsg) {
            this.httpStatus = httpStatus;
            this.errCode = errCode;
            this.retExceptionMsg = retExceptionMsg;
        }

        public ResolverPropertiesHolder(int errcode) {
            this(HttpStatus.OK, errcode, false);
        }

        public ResolverPropertiesHolder() {
            this(Constants.ResponseErrCodes.SYS_BUSY);
        }
    }
}
