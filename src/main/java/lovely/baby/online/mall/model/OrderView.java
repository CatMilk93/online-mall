package lovely.baby.online.mall.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderView {

    private int id;

    private String username;

    private OrderStatus status;

    private Date createTime;

    private Date updateTime;

    private List<OrderItemView> items;
}
