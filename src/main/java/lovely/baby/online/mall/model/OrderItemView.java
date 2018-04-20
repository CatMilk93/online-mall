package lovely.baby.online.mall.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemView {

    private int id;

    private int number;

    private Product product;
}
