package lovely.baby.online.mall.dao;

import java.util.Collection;
import java.util.List;

import lovely.baby.online.mall.model.Product;

public interface ProductDao {

    void save(Product product);

    List<Product> queryHotProducts(int limit);

    List<Product> queryLatestProducts(int limit);

    List<Product> queryByCategoryId(int categoryId, int offset, int limit);

    Product queryById(int id);

    List<Product> queryByIds(Collection<Integer> ids);
}
