package lovely.baby.online.mall.service;

import java.util.List;

import lovely.baby.online.mall.model.CartItemView;

public interface CartItemViewService {

    List<CartItemView> queryByUsername(String username);
}
