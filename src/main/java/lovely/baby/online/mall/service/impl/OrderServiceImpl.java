package lovely.baby.online.mall.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.OrderItemDao;
import lovely.baby.online.mall.dao.OrdersDao;
import lovely.baby.online.mall.model.Order;
import lovely.baby.online.mall.model.OrderItem;
import lovely.baby.online.mall.model.OrderStatus;
import lovely.baby.online.mall.service.OrderService;

@RequiredArgsConstructor
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final OrdersDao ordersDao;

    private final OrderItemDao orderItemDao;

    @Override
    @Transactional
    public void saveOrder(Map<Integer, Integer> productId2NumberMap, String username) {
        int orderId = ordersDao.save(new Order() //
                .setUsername(username) //
                .setStatus(OrderStatus.SUBMITTED));
        orderItemDao.batchSave(productId2NumberMap.entrySet().stream() //
                .map(it -> new OrderItem() //
                        .setOrderId(orderId) //
                        .setProductId(it.getKey()) //
                        .setNumber(it.getValue()))
                .collect(Collectors.toList()));
    }
}
