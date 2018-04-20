package lovely.baby.online.mall.web.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.model.OrderItemView;
import lovely.baby.online.mall.model.OrderView;
import lovely.baby.online.mall.service.OrderService;
import lovely.baby.online.mall.service.OrderViewService;
import lovely.baby.online.mall.util.RequestDataHolder;
import lovely.baby.online.mall.util.Splitters;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    private final OrderViewService orderViewService;

    // 订单页面
    @RequestMapping("user/setProductInOrder")
    public String setProductInOrder(String[] productIds) {
        if (ArrayUtils.isEmpty(productIds)) {
            return "redirect:/user/order";
        }
        orderService.saveOrder(Arrays.stream(productIds) //
                .map(it -> {
                    List<String> blocks = Splitters.VERTICAL_SPLITTER.splitToList(it);
                    return Pair.of(Integer.parseInt(blocks.get(0)), Integer.parseInt(blocks.get(1)));
                }) //
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight)), RequestDataHolder.getUsername());
        return "redirect:/user/order";
    }

    @RequestMapping("/user/order")
    public ModelAndView order() {
        List<OrderView> orderViews = orderViewService.queryByUsername(RequestDataHolder.getUsername());

        ModelAndView modelAndView = new ModelAndView("order");
        modelAndView.addObject("username", RequestDataHolder.getUsername());
        modelAndView.addObject("orderView", orderViews);
        modelAndView.addObject("oid2TotalPriceMap",
                orderViews.stream().collect(Collectors.toMap(OrderView::getId, it -> {
                    List<OrderItemView> orderItemViews = it.getItems();
                    if (CollectionUtils.isEmpty(orderItemViews)) {
                        return BigDecimal.ZERO;
                    }
                    return orderItemViews.stream()
                            .map(orderItemView -> new BigDecimal(orderItemView.getNumber())
                                    .multiply(orderItemView.getProduct().getPrice()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                })));
        return modelAndView;
    }
}
