package lovely.baby.online.mall.web.common;

import java.beans.PropertyEditorSupport;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import lovely.baby.online.mall.model.Gender;
import lovely.baby.online.mall.util.HasCodeUtils;

@ControllerAdvice
public class InitBinders {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Gender.class, new PropertyEditorSupport() {

            @Override
            public String getAsText() {
                return String.valueOf(((Gender) getValue()).getCode());
            }

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(HasCodeUtils.getInstance(Integer.parseInt(text), Gender.class));
            }
        });
    }
}
