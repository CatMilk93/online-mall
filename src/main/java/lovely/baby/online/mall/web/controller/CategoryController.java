package lovely.baby.online.mall.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.model.Category;
import lovely.baby.online.mall.model.Product;
import lovely.baby.online.mall.service.CategoryService;
import lovely.baby.online.mall.service.ProductService;
import lovely.baby.online.mall.util.RequestDataHolder;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    private final ProductService productService;

    // 商品分类展示页面
    @RequestMapping("categoryList")
    public ModelAndView category(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "1") int id,
            @RequestParam(defaultValue = "1") int listPage) {
        List<Category> categories = this.categoryService.query(page);
        ModelAndView modelAndView = new ModelAndView("categoryList");
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("nextPage", page + 1);

        List<Product> productList = this.productService.queryByCategoryId(id, listPage);
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("categoryId", id);
        modelAndView.addObject("listPage", listPage + 1);
        modelAndView.addObject("username", RequestDataHolder.getUsername());

        return modelAndView;
    }
}
