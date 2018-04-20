package lovely.baby.online.mall.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.ProductDao;
import lovely.baby.online.mall.model.Product;
import lovely.baby.online.mall.service.ProductService;

@RequiredArgsConstructor
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private static final int HOT_PRODUCT_SIZE = 3;

    @Override
    public List<Product> showHotProduct() {
        return this.productDao.queryHotProducts(HOT_PRODUCT_SIZE);
    }

    private static final int NEW_PRODUCT_SIZE = 3;

    @Override
    public List<Product> showNewProduct() {
        return this.productDao.queryLatestProducts(NEW_PRODUCT_SIZE);
    }

    @Override
    public Product queryById(int productId) {
        return productDao.queryById(productId);
    }

    private static final int PRODUCT_LIST_PAGE_SIZE = 3;

    @Override
    public List<Product> queryByCategoryId(int categoryId, int page) {
        return this.productDao.queryByCategoryId(categoryId, (page - 1) * PRODUCT_LIST_PAGE_SIZE,
                PRODUCT_LIST_PAGE_SIZE);
    }
}
