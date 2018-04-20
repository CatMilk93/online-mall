package lovely.baby.online.mall.dao;

import lovely.baby.online.mall.model.User;

public interface UserDao {

    void save(User user);

    User queryByUsernameAndPwd(String username, String password);
}
