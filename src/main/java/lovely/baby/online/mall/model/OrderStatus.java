package lovely.baby.online.mall.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    SUBMITTED(0, "未付款"), //
    PAID(1, "已支付"), //
    DELIVERED(2, "已发货"), //
    RECEIVED(3, "已收货");

    private final int code;

    private final String desc;

    public static OrderStatus getInstance(int code) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getCode() == code) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("未找到code对应的OrderStatus枚举: code = " + code);
    }
}
