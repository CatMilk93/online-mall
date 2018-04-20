package lovely.baby.online.mall.web.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.model.Category;
import lovely.baby.online.mall.model.Product;
import lovely.baby.online.mall.model.User;
import lovely.baby.online.mall.service.CategoryService;
import lovely.baby.online.mall.service.ProductService;
import lovely.baby.online.mall.service.UserService;
import lovely.baby.online.mall.util.Constants.CookieNames;
import lovely.baby.online.mall.util.RequestDataHolder;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final ProductService productService;

    // 用户注册页面
    @RequestMapping("register")
    public String register() {

        return "register";
    }

    // 用户注册成功返回登录页面
    @RequestMapping("saveUser")
    public String saveRegister(User user) {
        this.userService.userSave(user);
        return "login";
    }

    // 登录页面
    @GetMapping("login")
    public String login() {

        return "login";
    }

    // 退出页面
    @RequestMapping(value = "user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            if (CookieNames.USERNAME.equals(cookie.getName())) {
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/indexView";
    }

    // 验证登录是否成功后跳转页面
    @PostMapping("login")
    public String verifyLogin(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpServletResponse response) {
        User user = userService.userVerify(username, password);
        if (user == null) {
            return "login";
        }
        Cookie cookie = new Cookie(CookieNames.USERNAME, user.getUsername());
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:indexView";
    }

    // 初始页
    @RequestMapping("indexView")
    public ModelAndView indexView(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("index");

        List<Category> categories = this.categoryService.query(page);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("nextPage", page + 1);

        List<Product> hotProducts = this.productService.showHotProduct();
        modelAndView.addObject("hotProducts", hotProducts);

        List<Product> newProducts = this.productService.showNewProduct();
        modelAndView.addObject("newProducts", newProducts);
        modelAndView.addObject("username", RequestDataHolder.getUsername());

        return modelAndView;
    }

}
