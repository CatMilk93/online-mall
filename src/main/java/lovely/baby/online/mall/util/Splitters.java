package lovely.baby.online.mall.util;

import com.google.common.base.Splitter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Splitters {

    public Splitter VERTICAL_SPLITTER = Splitter.on('|').trimResults().omitEmptyStrings();
}
