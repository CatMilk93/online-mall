package lovely.baby.online.mall.service;

import java.util.List;

import lovely.baby.online.mall.model.Product;

public interface ProductService {

    List<Product> showHotProduct();

    List<Product> showNewProduct();

    Product queryById(int pid);

    List<Product> queryByCategoryId(int categoryId, int page);
}
