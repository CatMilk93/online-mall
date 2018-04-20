package lovely.baby.online.mall.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

    private int id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String telephone;

    private String birthday;

    private Gender gender;

    private Date createTime;

    private Date updateTime;
}
