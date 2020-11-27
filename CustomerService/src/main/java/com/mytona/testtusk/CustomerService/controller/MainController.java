package com.mytona.testtusk.CustomerService.controller;

import com.mytona.testtusk.CustomerService.pojo.Cart;
import com.mytona.testtusk.CustomerService.pojo.OrderCreate;
import com.mytona.testtusk.CustomerService.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

@Controller
@AllArgsConstructor
@SessionAttributes(types = Cart.class)
public class MainController {

    private final OrderService service;

    @GetMapping
    public String getIndex(Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        return "index";
    }

    @GetMapping("/orders")
    public String getAllOrders (Model model) {
        try {
            model.addAttribute("orders", service.getAllOrderInfo());
            return "orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/orders/{id}")
    public String getOrder (@PathVariable Long id, Model model) {
        try {
            model.addAttribute("order", service.getOrderInfo(id));
            return "order-open";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/create-order")
    public String getCreateForm () {
        return "create-order";
    }

    @GetMapping("/products")
    public String getAllProducts (Model model) {
        model.addAttribute("products", service.getAllProducts());
        return "products";
    }

    @GetMapping("/cart")
    public String getCart (Model model) {
        if (model.getAttribute("cart") == null){
            model.addAttribute(new Cart());
        }
        return "cart";
    }

    @PostMapping("/products/{id}/add")
    public String addProductToCart(Model model, @PathVariable Long id) {
        Cart cart = (Cart) model.getAttribute("cart");
        assert cart != null;
        cart.addProduct(service.getProduct(id));
        model.addAttribute(cart);
        return "redirect:/products";
    }

    @PostMapping("/cart/{id}/delete")
    public String deleteProductFromCart (Model model, @PathVariable Long id){
        Cart cart = (Cart) model.getAttribute("cart");
        assert cart != null;
        cart.deleteProduct(service.getProduct(id));
        model.addAttribute(cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/{id}/quantity")
    public String deleteProductFromCart (Model model, @PathVariable Long id, @RequestParam Integer quantity){
        Cart cart = (Cart) model.getAttribute("cart");
        assert cart != null;
        cart.setProductQuantity(service.getProduct(id), quantity);
        model.addAttribute(cart);
        return "redirect:/cart";
    }

    @PostMapping("/create-order")
    public String createOrder (Model model, @RequestParam String name, @RequestParam String email, @RequestParam String phone) {
        try {
            service.createOrder(new OrderCreate(name, email, phone, (Cart) model.getAttribute("cart")));
            model.addAttribute(new Cart());
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

    @PostMapping("/orders/{id}/edit")
    public String editOrder (Model model, @PathVariable Long id,@RequestParam String name, @RequestParam String email, @RequestParam String phone) {
        try {
            service.editOrder(id, new OrderCreate(name, email, phone, (Cart) model.getAttribute("cart")));
            return "redirect:/orders";
        }catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/confirm")
    public String confirmOrder (Model model, @PathVariable Long id) {
        try {
            service.confirmOrder(id);
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/pay")
    public String payOrder (Model model, @PathVariable Long id) {
        try {
            service.payOrder(id);
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/delivery")
    public String deliverOrder (Model model, @PathVariable Long id) {
        try {
            service.deliverOrder(id);
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder (Model model, @PathVariable Long id) {
        try {
            service.deleteOrder(id);
            return "redirect:/orders";
        } catch (RestClientException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }
}
