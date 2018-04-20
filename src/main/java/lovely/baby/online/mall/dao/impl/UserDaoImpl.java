package lovely.baby.online.mall.dao.impl;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lovely.baby.online.mall.dao.UserDao;
import lovely.baby.online.mall.model.Gender;
import lovely.baby.online.mall.model.User;
import lovely.baby.online.mall.util.HasCodeUtils;

@Slf4j
@RequiredArgsConstructor
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE = "INSERT INTO user (username, password, name, email, telephone, birthday, gender) VALUES(:username, :password, :name, :email, :telephone, :birthday, :gender)";

    @Override
    public void save(User user) {
        namedParameterJdbcTemplate.update(SAVE, new MapSqlParameterSource("username", user.getUsername()) //
                .addValue("password", user.getPassword()) //
                .addValue("name", user.getName()) //
                .addValue("email", user.getEmail()) //
                .addValue("telephone", user.getTelephone()) //
                .addValue("birthday", user.getBirthday()) //
                .addValue("gender", user.getGender().getCode()));
    }

    private static final String COLUMN_NAMES = "id, username, password, name, email, telephone, birthday, gender, create_time, update_time";

    private static final String QUERY_BY_USERNAME_AND_PWD = "SELECT " + COLUMN_NAMES
            + " FROM user WHERE username = :username AND password = :password";

    @Override
    public User queryByUsernameAndPwd(String username, String password) {
        User user = null;
        try {
            user = namedParameterJdbcTemplate.queryForObject(QUERY_BY_USERNAME_AND_PWD,
                    new MapSqlParameterSource("username", username).addValue("password", password), USER_ROW_MAPPER);
        } catch (IncorrectResultSizeDataAccessException exception) {
            log.info("未查找到指定用户: username = {}, password = {}", username, password);
        }
        return user;
    }

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User() //
            .setId(rs.getInt("id")) //
            .setUsername(rs.getString("username")) //
            .setPassword(rs.getString("password")) //
            .setName(rs.getString("name")) //
            .setEmail(rs.getString("email")) //
            .setTelephone(rs.getString("telephone")) //
            .setBirthday(rs.getString("birthday")) //
            .setGender(HasCodeUtils.getInstance(rs.getInt("gender"), Gender.class)) //
            .setCreateTime(rs.getTimestamp("create_time")) //
            .setUpdateTime(rs.getTimestamp("update_time"));
}
