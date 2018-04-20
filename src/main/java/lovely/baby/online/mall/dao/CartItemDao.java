package lovely.baby.online.mall.dao;

import java.util.List;

import lovely.baby.online.mall.model.CartItem;

public interface CartItemDao {

    int save(CartItem cartItem);

    List<CartItem> queryByUsername(String username);

    CartItem queryByUsernameAndProductId(String username, int productId);

    void deleteById(int id);

    int updateNumber(int increment, String username, int productId);
}
