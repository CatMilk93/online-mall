package lovely.baby.online.mall.service;

import java.util.List;

import lovely.baby.online.mall.model.OrderView;

public interface OrderViewService {

    List<OrderView> queryByUsername(String username);
}
