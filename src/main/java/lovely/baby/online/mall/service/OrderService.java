package lovely.baby.online.mall.service;

import java.util.Map;

public interface OrderService {

    void saveOrder(Map<Integer, Integer> productId2NumberMap, String username);
}
