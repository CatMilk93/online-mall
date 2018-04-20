package lovely.baby.online.mall.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.CartItemDao;
import lovely.baby.online.mall.dao.ProductDao;
import lovely.baby.online.mall.model.CartItem;
import lovely.baby.online.mall.model.CartItemView;
import lovely.baby.online.mall.model.Product;
import lovely.baby.online.mall.service.CartItemViewService;

@RequiredArgsConstructor
@Service("cartItemViewService")
public class CartItemViewServiceImpl implements CartItemViewService {

    private final CartItemDao cartItemDao;

    private final ProductDao productDao;

    @Override
    public List<CartItemView> queryByUsername(String username) {
        List<CartItemView> cartItemViews = query(username);

        if (cartItemViews.isEmpty()) {
            return cartItemViews;
        }
        return Ordering.natural().reverse().onResultOf(CartItemView::getCreateTime).sortedCopy(cartItemViews);
    }

    private List<CartItemView> query(String username) {
        List<CartItem> cartItems = cartItemDao.queryByUsername(username);
        if (cartItems.isEmpty()) {
            return Lists.newArrayList();
        }

        List<CartItemView> result = cartItems.stream() //
                .map(it -> {
                    CartItemView cartItemView = new CartItemView();
                    BeanUtils.copyProperties(it, cartItemView);
                    return cartItemView;
                }) //
                .collect(Collectors.toList());

        List<Product> products = productDao
                .queryByIds(cartItems.stream().map(CartItem::getProductId).collect(Collectors.toSet()));
        if (products.isEmpty()) {
            return result;
        }
        Map<Integer, Integer> cartItemId2ProductIdMap = cartItems.stream()
                .collect(Collectors.toMap(CartItem::getId, CartItem::getProductId));
        Map<Integer, Product> pid2ProductMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        result.forEach(it -> it.setProduct(pid2ProductMap.get(cartItemId2ProductIdMap.get(it.getId()))));
        return result;
    }
}
