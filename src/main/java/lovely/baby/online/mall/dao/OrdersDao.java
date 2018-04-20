package lovely.baby.online.mall.dao;

import java.util.List;

import lovely.baby.online.mall.model.Order;

public interface OrdersDao {

    int save(Order order);

    List<Order> queryByUsername(String username);
}
