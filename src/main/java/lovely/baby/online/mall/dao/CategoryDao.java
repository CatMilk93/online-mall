package lovely.baby.online.mall.dao;

import java.util.List;

import lovely.baby.online.mall.model.Category;

public interface CategoryDao {

    List<Category> query(int offset, int limit);
}
