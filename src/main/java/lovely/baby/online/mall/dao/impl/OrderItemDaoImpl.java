package lovely.baby.online.mall.dao.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.dao.OrderItemDao;
import lovely.baby.online.mall.model.OrderItem;

@Slf4j
@RequiredArgsConstructor
@Repository("orderItemDao")
public class OrderItemDaoImpl implements OrderItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE = "INSERT INTO order_item(product_id, order_id, number) VALUES(:productId, :orderId, :number)";

    @Override
    public void batchSave(Collection<OrderItem> orderItems) {
        checkNotNull(orderItems);
        if (orderItems.isEmpty()) {
            return;
        }
        namedParameterJdbcTemplate.batchUpdate(SAVE, orderItems.stream() //
                .map(it -> new MapSqlParameterSource("productId", it.getProductId()) //
                        .addValue("orderId", it.getOrderId()) //
                        .addValue("number", it.getNumber())) //
                .toArray(SqlParameterSource[]::new));
    }

    private static final String COLUMN_NAMES = "id, order_id, product_id, number";

    private static final String QUERY_BY_ORDER_IDS = "SELECT " + COLUMN_NAMES
            + " FROM order_item WHERE order_id IN(:orderIds)";

    @Override
    public List<OrderItem> queryByOrderIds(Collection<Integer> orderIds) {
        checkNotNull(orderIds);
        if (orderIds.isEmpty()) {
            return Lists.newArrayList();
        }
        return namedParameterJdbcTemplate.query(QUERY_BY_ORDER_IDS, new MapSqlParameterSource("orderIds", orderIds),
                ORDER_ITEM_ROW_MAPPER);
    }

    private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, rowNum) -> new OrderItem() //
            .setId(rs.getInt("id")) //
            .setOrderId(rs.getInt("order_id")) //
            .setProductId(rs.getInt("product_id")) //
            .setNumber(rs.getInt("number"));

}
