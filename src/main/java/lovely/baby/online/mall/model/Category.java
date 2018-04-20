package lovely.baby.online.mall.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Category {

    private int id;

    private String name;

    private Date createTime;

    private Date updateTime;
}
