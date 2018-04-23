package lovely.baby.online.mall.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.dao.CartItemDao;
import lovely.baby.online.mall.model.CartItem;

@Slf4j
@RequiredArgsConstructor
@Repository("cartItemDao")
public class CartItemDaoImpl implements CartItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE = "INSERT IGNORE INTO cart_item (username, product_id, number) VALUES(:username, :productId, :number)";

    @Override
    public int save(CartItem cartItem) {
        return namedParameterJdbcTemplate.update(SAVE, new MapSqlParameterSource("username", cartItem.getUsername()) //
                .addValue("productId", cartItem.getProductId()) //
                .addValue("number", cartItem.getNumber()));
    }

    private static final String COLUMN_NAMES = "id, username, product_id, number, create_time, update_time";

    private static final String QUERY_BY_USERNAME = "SELECT " + COLUMN_NAMES
            + " FROM cart_item WHERE username = :username";

    @Override
    public List<CartItem> queryByUsername(String username) {
        return namedParameterJdbcTemplate.query(QUERY_BY_USERNAME, new MapSqlParameterSource("username", username),
                PRODUCT_CART_ROW_MAPPER);
    }

    private static final String QUERY_BY_USERNAME_AND_PRODUCT_ID = "SELECT " + COLUMN_NAMES
            + " FROM cart_item WHERE username = :username AND product_id = :productId";

    @Override
    public CartItem queryByUsernameAndProductId(String username, int productId) {
        CartItem cartItem = null;
        try {
            cartItem = namedParameterJdbcTemplate.queryForObject(QUERY_BY_USERNAME_AND_PRODUCT_ID,
                    new MapSqlParameterSource("username", username) //
                            .addValue("productId", productId),
                    PRODUCT_CART_ROW_MAPPER);
        } catch (IncorrectResultSizeDataAccessException ignore) {
        }
        return cartItem;
    }

    private static final RowMapper<CartItem> PRODUCT_CART_ROW_MAPPER = (rs, rowNum) -> new CartItem() //
            .setId(rs.getInt("id")) //
            .setUsername(rs.getString("username")) //
            .setProductId(rs.getInt("product_id")) //
            .setNumber(rs.getInt("number")) //
            .setCreateTime(rs.getTimestamp("create_time")) //
            .setUpdateTime(rs.getTimestamp("update_time"));

    private static final String DELETE_BY_ID = "DELETE FROM cart_item WHERE id = :id";

    @Override
    public void deleteById(int id) {
        namedParameterJdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource("id", id));
    }

    private static final String DELETE_BY_IDS = "DELETE FROM cart_item WHERE product_id in (:productIds) ";

    @Override
    public void deleteByIds(Set<Integer> productIds) {
        namedParameterJdbcTemplate.update(DELETE_BY_IDS, new MapSqlParameterSource("productIds", productIds));
    }

    private static final String UPDATE_NUMBER = "UPDATE cart_item SET number = number + :increment WHERE username = :username AND product_id = :productId";

    @Override
    public int updateNumber(int increment, String username, int productId) {
        return namedParameterJdbcTemplate.update(UPDATE_NUMBER, new MapSqlParameterSource("increment", increment) //
                .addValue("username", username) //
                .addValue("productId", productId));
    }
}
