package lovely.baby.online.mall.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.CartItemDao;
import lovely.baby.online.mall.model.CartItem;
import lovely.baby.online.mall.service.CartItemService;

@RequiredArgsConstructor
@Repository("cartItemService")
public class CartItemServiceImpl implements CartItemService {

    private final CartItemDao cartItemDao;

    @Override
    public void saveOrUpdateCartItem(int increment, String username, int productId) {
        CartItem cartItem = cartItemDao.queryByUsernameAndProductId(username, productId);
        if (cartItem == null && cartItemDao.save(new CartItem() //
                .setUsername(username) //
                .setProductId(productId) //
                .setNumber(increment)) > 0) {
            return;
        }
        cartItemDao.updateNumber(increment, username, productId);
    }

    @Override
    public List<CartItem> queryByUsername(String username) {
        return cartItemDao.queryByUsername(username);
    }

    @Override
    public void deleteById(int id) {
        cartItemDao.deleteById(id);
    }
}
