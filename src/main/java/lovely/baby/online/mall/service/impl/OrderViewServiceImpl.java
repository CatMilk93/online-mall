package lovely.baby.online.mall.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.OrderItemDao;
import lovely.baby.online.mall.dao.OrdersDao;
import lovely.baby.online.mall.dao.ProductDao;
import lovely.baby.online.mall.model.Order;
import lovely.baby.online.mall.model.OrderItem;
import lovely.baby.online.mall.model.OrderItemView;
import lovely.baby.online.mall.model.OrderView;
import lovely.baby.online.mall.model.Product;
import lovely.baby.online.mall.service.OrderViewService;

@RequiredArgsConstructor
@Service("orderViewService")
public class OrderViewServiceImpl implements OrderViewService {

    private final OrdersDao ordersDao;

    private final OrderItemDao orderItemDao;

    private final ProductDao productDao;

    @Override
    public List<OrderView> queryByUsername(String username) {
        List<OrderView> orderViews = queryByUsernameWithoutOrder(username);
        if (orderViews.isEmpty()) {
            return orderViews;
        }
        return Ordering.natural().reverse().onResultOf(OrderView::getCreateTime).sortedCopy(orderViews);
    }

    private List<OrderView> queryByUsernameWithoutOrder(String username) {
        List<Order> orderList = ordersDao.queryByUsername(username);
        if (orderList.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<Integer, OrderView> oid2OrderViewMap = orderList.stream().collect(Collectors.toMap(Order::getId, it -> {
            OrderView orderView = new OrderView();
            BeanUtils.copyProperties(it, orderView);
            return orderView;
        }));

        List<OrderItem> orderItems = orderItemDao
                .queryByOrderIds(orderList.stream().map(Order::getId).collect(Collectors.toList()));
        if (orderItems.isEmpty()) {
            return Lists.newArrayList(oid2OrderViewMap.values());
        }
        orderItems.forEach(it -> {
            OrderView orderView = oid2OrderViewMap.get(it.getOrderId());
            OrderItemView orderItemView = new OrderItemView();
            BeanUtils.copyProperties(it, orderItemView);
            if (orderView.getItems() != null) {
                orderView.getItems().add(orderItemView);
            } else {
                orderView.setItems(Lists.newArrayList(orderItemView));
            }
        });

        List<Product> products = productDao
                .queryByIds(orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toSet()));
        if (products.isEmpty()) {
            return Lists.newArrayList(oid2OrderViewMap.values());
        }
        Map<Integer, Integer> itemId2PidMap = orderItems.stream()
                .collect(Collectors.toMap(OrderItem::getId, OrderItem::getProductId));
        Map<Integer, Product> pid2ProductMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        oid2OrderViewMap.forEach((key, value) -> {
            List<OrderItemView> orderItemViews = value.getItems();
            if (CollectionUtils.isEmpty(orderItemViews)) {
                return;
            }
            orderItemViews.forEach(orderItemView -> orderItemView
                    .setProduct(pid2ProductMap.get(itemId2PidMap.get(orderItemView.getId()))));
        });

        return Lists.newArrayList(oid2OrderViewMap.values());
    }
}
