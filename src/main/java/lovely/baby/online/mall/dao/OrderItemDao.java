package lovely.baby.online.mall.dao;

import java.util.Collection;
import java.util.List;

import lovely.baby.online.mall.model.OrderItem;

public interface OrderItemDao {

    void batchSave(Collection<OrderItem> orderItems);

    List<OrderItem> queryByOrderIds(Collection<Integer> oids);
}
