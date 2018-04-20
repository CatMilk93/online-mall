package lovely.baby.online.mall.dao.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.dao.OrdersDao;
import lovely.baby.online.mall.model.Order;
import lovely.baby.online.mall.model.OrderStatus;

@Slf4j
@RequiredArgsConstructor
@Repository("ordersDao")
public class OrdersDaoImpl implements OrdersDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE = "INSERT INTO orders(status, username) VALUES(:status, :username)";

    @Override
    public int save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SAVE, new MapSqlParameterSource("status", order.getStatus().getCode()) //
                .addValue("username", order.getUsername()), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    private static final String COLUMN_NAMES = "id, status, username, create_time, update_time";

    private static final String QUERY_BY_USERNAME = "SELECT " + COLUMN_NAMES
            + " FROM orders WHERE username = :username";

    @Override
    public List<Order> queryByUsername(String username) {
        checkNotNull(username);
        return namedParameterJdbcTemplate.query(QUERY_BY_USERNAME, new MapSqlParameterSource("username", username),
                ORDERS_ROW_MAPPER);
    }

    private static final RowMapper<Order> ORDERS_ROW_MAPPER = (rs, rowNum) -> new Order() //
            .setId(rs.getInt("id")) //
            .setStatus(OrderStatus.getInstance(rs.getInt("status"))) //
            .setCreateTime(rs.getTimestamp("create_time")) //
            .setUpdateTime(rs.getTimestamp("update_time")) //
            .setUsername(rs.getString("username"));

}
