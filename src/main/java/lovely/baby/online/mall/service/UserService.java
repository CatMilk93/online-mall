package lovely.baby.online.mall.service;

import lovely.baby.online.mall.model.User;

public interface UserService {

    void userSave(User user);

    User userVerify(String username, String password);
}
