package lovely.baby.online.mall.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lovely.baby.online.mall.model.ResponseObj;
import lovely.baby.online.mall.model.exception.InvalidParamException;

@Controller
@RequestMapping("test")
public class TestController {

    @ResponseBody
    @RequestMapping("json_api")
    public Map<String, ?> jsonApi() {
        return ResponseObj.createEmptySuccessResponseObj().toMap();
    }

    @RequestMapping("jsp")
    public String jspPage() {
        return "success";
    }

    @RequestMapping("throw_exception")
    public String throwException(int param) {
        throw new InvalidParamException("参数错误: param = %s", param);
    }
}
