package lovely.baby.online.mall.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements HasCode<Integer> {
    MALE(0), //
    FEMALE(1);

    private final Integer code;

}
