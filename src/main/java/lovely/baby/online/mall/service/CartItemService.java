package lovely.baby.online.mall.service;

import java.util.List;

import lovely.baby.online.mall.model.CartItem;

public interface CartItemService {

    void saveOrUpdateCartItem(int numbers, String username, int productId);

    List<CartItem> queryByUsername(String username);

    void deleteById(int id);
}
