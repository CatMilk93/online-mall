package lovely.baby.online.mall.dao.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.dao.ProductDao;
import lovely.baby.online.mall.model.Product;

@Slf4j
@RequiredArgsConstructor
@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE = "INSERT INTO product (name, price, hot, category_id, available) VALUES(:name, :price, :hot, :categoryId, :available)";

    @Override
    public void save(Product product) {
        namedParameterJdbcTemplate.update(SAVE, new MapSqlParameterSource("name", product.getName()) //
                .addValue("price", product.getPrice()) //
                .addValue("hot", product.isHot()) //
                .addValue("categoryId", product.getCategoryId()) //
                .addValue("available", product.isAvailable()));
    }

    private static final String COLUMN_NAMES = "id, name, price, hot, category_id, available, create_time, update_time";

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product() //
            .setId(rs.getInt("id")) //
            .setName(rs.getString("name")) //
            .setPrice(rs.getBigDecimal("price")) //
            .setHot(rs.getBoolean("hot")) //
            .setAvailable(rs.getBoolean("available")) //
            .setCategoryId(rs.getInt("category_id")) //
            .setCreateTime(rs.getTimestamp("create_time")) //
            .setUpdateTime(rs.getTimestamp("update_time"));

    private static final String QUERY_HOT_PRODUCTS = "SELECT " + COLUMN_NAMES
            + " FROM product WHERE hot = :hot AND available = :available ORDER BY update_time DESC Limit :limit";

    @Override
    public List<Product> queryHotProducts(int limit) {
        return namedParameterJdbcTemplate.query(QUERY_HOT_PRODUCTS, new MapSqlParameterSource("limit", limit) //
                .addValue("hot", true) //
                .addValue("available", true), PRODUCT_ROW_MAPPER);
    }

    private static final String QUERY_LATEST_PRODUCTS = "SELECT " + COLUMN_NAMES
            + " FROM product WHERE available = :available ORDER BY update_time DESC Limit :limit";

    @Override
    public List<Product> queryLatestProducts(int limit) {
        return namedParameterJdbcTemplate.query(QUERY_LATEST_PRODUCTS, new MapSqlParameterSource("limit", limit) //
                .addValue("available", true), PRODUCT_ROW_MAPPER);
    }

    private static final String QUERY_BY_CATEGORY_ID = "SELECT " + COLUMN_NAMES
            + " FROM product WHERE category_id = :categoryId AND available = :available LIMIT :limit OFFSET :offset";

    @Override
    public List<Product> queryByCategoryId(int categoryId, int offset, int limit) {
        return namedParameterJdbcTemplate.query(QUERY_BY_CATEGORY_ID,
                new MapSqlParameterSource("categoryId", categoryId) //
                        .addValue("available", true) //
                        .addValue("limit", limit) //
                        .addValue("offset", offset),
                PRODUCT_ROW_MAPPER);
    }

    private static final String QUERY_BY_ID = "SELECT " + COLUMN_NAMES
            + " FROM product WHERE id = :id AND available = :available";

    @Override
    public Product queryById(int id) {
        Product product = null;
        try {
            product = namedParameterJdbcTemplate.queryForObject(QUERY_BY_ID, new MapSqlParameterSource("id", id) //
                    .addValue("available", true), PRODUCT_ROW_MAPPER);
        } catch (IncorrectResultSizeDataAccessException exception) {
            log.info("未找到给定id对应的商品或该商品已下架: id = {}", id);
        }
        return product;
    }

    private static final String QUERY_BY_IDS = "SELECT " + COLUMN_NAMES
            + " FROM product WHERE id IN(:ids) AND available = :available";

    @Override
    public List<Product> queryByIds(Collection<Integer> ids) {
        checkNotNull(ids);
        if (ids.isEmpty()) {
            return Lists.newArrayList();
        }
        return namedParameterJdbcTemplate.query(QUERY_BY_IDS, new MapSqlParameterSource("ids", ids) //
                .addValue("available", true), PRODUCT_ROW_MAPPER);
    }
}
