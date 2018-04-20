package lovely.baby.online.mall.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.CategoryDao;
import lovely.baby.online.mall.model.Category;
import lovely.baby.online.mall.service.CategoryService;

@RequiredArgsConstructor
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    private static final int CATEGORY_PAGE_SIZE = 5;

    @Override
    public List<Category> query(int page) {
        return this.categoryDao.query((page - 1) * CATEGORY_PAGE_SIZE, CATEGORY_PAGE_SIZE);
    }
}
