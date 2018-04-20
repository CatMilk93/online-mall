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
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    // 商品详情页
    @RequestMapping("productDetails")
    private ModelAndView product(@RequestParam(defaultValue = "1") int page, int productId) {
        Product product = this.productService.queryById(productId);
        ModelAndView modelAndView = new ModelAndView("productDetails");
        modelAndView.addObject("product", product);

        List<Category> categories = this.categoryService.query(page);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("nextPage", page + 1);
        modelAndView.addObject("username", RequestDataHolder.getUsername());
        modelAndView.addObject("productId", productId);

        return modelAndView;
    }

}
