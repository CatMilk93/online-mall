package lovely.baby.online.mall.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItem {

    private int id;

    private String username;

    private int productId;

    private int number;

    private Date createTime;

    private Date updateTime;
}
