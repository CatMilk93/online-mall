package lovely.baby.online.mall.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {

    private int id;

    private String name;

    private BigDecimal price;

    private Date createTime;

    private boolean hot;

    private Date updateTime;

    private int categoryId;

    private boolean available;
}
