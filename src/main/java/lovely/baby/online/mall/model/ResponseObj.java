package lovely.baby.online.mall.model;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;

import lombok.Data;
import lombok.experimental.Accessors;
import lovely.baby.online.mall.util.Constants;

@Data
@Accessors(chain = true)
public class ResponseObj<T> {

    private boolean ret;

    private int errCode;

    private String errMsg;

    private T data;

    public static <T> ResponseObj<T> createSuccessResponseObj(T data) {
        return new ResponseObj<T>().setRet(true).setErrCode(Constants.ResponseErrCodes.SUCCESS).setData(data);
    }

    public static <T> ResponseObj<T> createEmptySuccessResponseObj() {
        return new ResponseObj<T>().setRet(true).setErrCode(Constants.ResponseErrCodes.SUCCESS);
    }

    public static <T> ResponseObj<T> createFailureResponseObj(int errCode, String errMsg) {
        return new ResponseObj<T>().setRet(false).setErrCode(errCode).setErrMsg(errMsg);
    }

    private static final String RET_KEY = "ret";

    private static final String ERR_CODE_KEY = "errCode";

    private static final String ERR_MSG_KEY = "errMsg";

    private static final String DATA_KEY = "data";

    public ImmutableMap<String, ?> toMap() {
        ImmutableMap.Builder<String, Object> immutableMapBuilder = ImmutableMap.<String, Object> builder()
                .put(RET_KEY, ret).put(ERR_CODE_KEY, errCode);
        if (StringUtils.isNotBlank(errMsg)) {
            immutableMapBuilder.put(ERR_MSG_KEY, errMsg);
        }
        if (data != null) {
            immutableMapBuilder.put(DATA_KEY, data);
        }
        return immutableMapBuilder.build();
    }
}
