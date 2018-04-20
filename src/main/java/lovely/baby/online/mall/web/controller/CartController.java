package lovely.baby.online.mall.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lovely.baby.online.mall.model.CartItemView;
import lovely.baby.online.mall.model.Product;
import lovely.baby.online.mall.service.CartItemService;
import lovely.baby.online.mall.service.CartItemViewService;
import lovely.baby.online.mall.service.ProductService;
import lovely.baby.online.mall.util.RequestDataHolder;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartItemViewService cartItemViewService;

    private final CartItemService cartItemService;

    private final ProductService productService;

    // 购物车页面
    @RequestMapping("user/product/cartItem")
    public ModelAndView cart() {
        List<CartItemView> cartItemViews = cartItemViewService.queryByUsername(RequestDataHolder.getUsername());
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("cartItemViews", cartItemViews);
        modelAndView.addObject("username", RequestDataHolder.getUsername());
        return modelAndView;
    }

    // 用户商品详情页添加购物车
    @RequestMapping("user/setCartProduct")
    public ModelAndView setCartProduct(int productId, int number) {
        Product product = productService.queryById(productId);
        if (product != null) {
            cartItemService.saveOrUpdateCartItem(number, RequestDataHolder.getUsername(), productId);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/productDetails");
        modelAndView.addObject("productId", productId);
        return modelAndView;
    }

    // 删除购物车商品
    @RequestMapping("/user/deleteCartProduct")
    public String deleteCartProduct(int id) {
        cartItemService.deleteById(id);
        return "redirect:/user/product/cartItem";
    }
}
