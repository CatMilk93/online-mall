package lovely.baby.online.mall.service;

import java.util.List;

import lovely.baby.online.mall.model.Category;

public interface CategoryService {

    List<Category> query(int page);
}
