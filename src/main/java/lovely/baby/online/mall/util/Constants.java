package lovely.baby.online.mall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public final Logger BUSINESS_EXCEPTION_LOGGER = LoggerFactory.getLogger("businessExceptionLogger");

    @UtilityClass
    public class ResponseErrCodes {

        public final int SUCCESS = 0; // 操作成功

        public final int INVALID_PARAM = -1; // 参数不合法

        public final int SYS_BUSY = 5; // 系统错误
    }

    @UtilityClass
    public class CookieNames {

        public final String USERNAME = "username";
    }
}
