package lovely.baby.online.mall.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.dao.CategoryDao;
import lovely.baby.online.mall.model.Category;

@Slf4j
@RequiredArgsConstructor
@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String COLUMN_NAMES = "id, name, create_time, update_time";

    // 页面展示分类
    private static final String QUERY_BY_PAGE = "SELECT " + COLUMN_NAMES + " FROM category LIMIT :limit OFFSET :offset";

    @Override
    public List<Category> query(int offset, int limit) {
        return namedParameterJdbcTemplate.query(QUERY_BY_PAGE, new MapSqlParameterSource("limit", limit) //
                .addValue("offset", offset), CATEGORY_ROW_MAPPER);
    }

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, rowNum) -> new Category() //
            .setId(rs.getInt("id")) //
            .setName(rs.getString("name")) //
            .setCreateTime(rs.getTimestamp("create_time")) //
            .setUpdateTime(rs.getTimestamp("update_time"));

}
