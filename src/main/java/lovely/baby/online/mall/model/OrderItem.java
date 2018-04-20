package lovely.baby.online.mall.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItem {

    private int id;

    private int productId;

    private int orderId;

    private int number;
}
