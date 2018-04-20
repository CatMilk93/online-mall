package lovely.baby.online.mall.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemView {

    private int id;

    private String username;

    private int number;

    private Product product;

    private Date createTime;

    private Date updateTime;
}
