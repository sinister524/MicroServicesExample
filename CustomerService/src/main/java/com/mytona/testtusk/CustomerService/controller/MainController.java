package com.mytona.testtusk.CustomerService.controller;

import com.mytona.testtusk.CustomerService.pojo.OrderCreate;
import com.mytona.testtusk.CustomerService.pojo.OrderInfo;
import com.mytona.testtusk.CustomerService.pojo.auth.AuthInfo;
import com.mytona.testtusk.CustomerService.pojo.Cart;
import com.mytona.testtusk.CustomerService.pojo.auth.AuthRequest;
import com.mytona.testtusk.CustomerService.pojo.auth.RegistrationRequest;
import com.mytona.testtusk.CustomerService.service.AuthService;
import com.mytona.testtusk.CustomerService.service.OrderService;
import com.mytona.testtusk.CustomerService.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

@Controller
@AllArgsConstructor
@SessionAttributes(types = {Cart.class, AuthInfo.class, OrderInfo.class})
public class MainController {

    private final OrderService orderService;

    private final AuthService authService;

    private final ProductService productService;

    @GetMapping
    public String getIndex(Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        if (model.getAttribute("authInfo") == null){
            model.addAttribute(new AuthInfo());
        }
        return "index";
    }

    @GetMapping("/auth")
    public String getAuthForm() {
        return "auth";
    }

    @GetMapping("/registration")
    public String getRegistrationForm() {
        return "registration";
    }

    @PostMapping("/auth")
    public String auth (Model model, @RequestParam String login, @RequestParam String password) {
        try {
            AuthInfo authInfo = authService.logIn(new AuthRequest(login, password));
            model.addAttribute(authInfo);
            return "redirect:/";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

    @PostMapping("/registration")
    public String registration (Model model, @RequestParam String username, @RequestParam String email,
                                @RequestParam String phone, @RequestParam String password,
                                @RequestParam String firstName, @RequestParam String lastName) {
        try {
            AuthInfo authInfo = authService.registration(new RegistrationRequest(username, email, phone, password,
                    firstName, lastName));
            model.addAttribute(authInfo);
            return "redirect:/";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

    @GetMapping("/logout")
    public String logOut (Model model) {
        model.addAttribute(new AuthInfo());
        return "redirect:/";
    }


    @GetMapping("/orders")
    public String getAllOrders (Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        if (model.getAttribute("authInfo") == null){
            model.addAttribute(new AuthInfo());
        }
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            model.addAttribute("orders", orderService.getAllOrderInfo(authInfo.getJwtToken()));
            return "orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/orders/{id}")
    public String getOrder (@PathVariable Long id, Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        if (model.getAttribute("authInfo") == null){
            model.addAttribute(new AuthInfo());
        }
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            model.addAttribute("order", orderService.getOrderInfo(id, authInfo.getJwtToken()));
            return "order-open";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }


    @PostMapping("/orders/{id}/edit")
    public String editOrder (Model model, @PathVariable Long id, @RequestParam String name, @RequestParam String email,
                             @RequestParam String phone) {
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            OrderInfo editableOrder = (OrderInfo) model.getAttribute("order");
            assert editableOrder != null;
            Cart orderCart = editableOrder.getCart();
            orderService.editOrder(id, new OrderCreate(name, email, phone, orderCart),
                    authInfo.getJwtToken());
            return "redirect:/orders";
        }catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/confirm")
    public String confirmOrder (Model model, @PathVariable Long id) {
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            orderService.confirmOrder(id, authInfo.getJwtToken());
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/pay")
    public String payOrder (Model model, @PathVariable Long id) {
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            orderService.payOrder(id, authInfo.getJwtToken());
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/delivery")
    public String deliverOrder (Model model, @PathVariable Long id) {
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            orderService.deliverOrder(id, authInfo.getJwtToken());
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder (Model model, @PathVariable Long id) {
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            orderService.deleteOrder(id, authInfo.getJwtToken());
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }


    @GetMapping("/products")
    public String getAllProducts (Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        if (model.getAttribute("authInfo") == null){
            model.addAttribute(new AuthInfo());
        }
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @PostMapping("/products/{id}/add")
    public String addProductToCart(Model model, @PathVariable Long id) {
        Cart cart = (Cart) model.getAttribute("cart");
        assert cart != null;
        cart.addProduct(productService.getProduct(id));
        model.addAttribute(cart);
        return "redirect:/products";
    }

    @GetMapping("/cart")
    public String getCart (Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        if (model.getAttribute("authInfo") == null){
            model.addAttribute(new AuthInfo());
        }
        return "cart";
    }

    @PostMapping("/cart/{id}/delete")
    public String deleteProductFromCart (Model model, @PathVariable Long id){
        Cart cart = (Cart) model.getAttribute("cart");
        assert cart != null;
        cart.deleteProduct(productService.getProduct(id));
        model.addAttribute(cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/{id}/quantity")
    public String deleteProductFromCart (Model model, @PathVariable Long id, @RequestParam Integer quantity){
        Cart cart = (Cart) model.getAttribute("cart");
        assert cart != null;
        cart.setProductQuantity(productService.getProduct(id), quantity);
        model.addAttribute(cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/create-order")
    public String getCreateForm () {
        return "create-order";
    }

    @PostMapping("/cart/create-order")
    public String createOrder (Model model, @RequestParam String name, @RequestParam String email, @RequestParam String phone) {
        try {
            AuthInfo authInfo = (AuthInfo) model.getAttribute("authInfo");
            assert authInfo != null;
            orderService.createOrder(authInfo.getJwtToken(), new OrderCreate(name, email, phone, (Cart) model.getAttribute("cart")));
            model.addAttribute(new Cart());
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

}
