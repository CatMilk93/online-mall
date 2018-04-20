package lovely.baby.online.mall.util;

import lovely.baby.online.mall.model.HasCode;

public class HasCodeUtils {

    public static <CodeType, EnumType extends Enum<EnumType> & HasCode<CodeType>> EnumType getInstance(CodeType code,
            Class<EnumType> enumClass) {
        for (EnumType element : enumClass.getEnumConstants()) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        throw new IllegalArgumentException("未找到code对应的枚举: code = " + code + ", enumClass = " + enumClass);
    }
}
