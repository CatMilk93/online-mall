package lovely.baby.online.mall.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.dao.UserDao;
import lovely.baby.online.mall.model.User;
import lovely.baby.online.mall.service.UserService;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public void userSave(User user) {
        this.userDao.save(user);
    }

    @Override
    public User userVerify(String username, String password) {
        return userDao.queryByUsernameAndPwd(username, password);
    }
}
