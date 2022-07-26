package com.example.ontapspring0205.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.example.ontapspring0205.dto.Cart;
import com.example.ontapspring0205.dto.OrderLine;
import com.example.ontapspring0205.entity.home.HomeEntity;
import com.example.ontapspring0205.repository.IHomeRepository;
import com.example.ontapspring0205.service.emailService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired private IHomeRepository productRepository;
    @Autowired private MailService mailService;

    public void addToCart(Long id,  HttpSession session) {
        HashMap<Long, OrderLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, OrderLine>) rawCart;
        } else {
            cart = new HashMap<>();
        }

        Optional<HomeEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            OrderLine orderLine = cart.get(id);
            if (orderLine == null) {
                cart.put(id, new OrderLine(product.get(), 1));
            } else {
                orderLine.increaseByOne();
                cart.put(id, orderLine);
            }
        }

        session.setAttribute("CART", cart);
    }

    public int countItemInCart(HttpSession session) {
        HashMap<Long, OrderLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, OrderLine>) rawCart;
            return cart.values().stream().mapToInt(OrderLine::getCount).sum();
        } else {
            return 0;
        }
    }

    public Cart getCart(HttpSession session) {
        HashMap<Long, OrderLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, OrderLine>) rawCart;
            return new Cart(
                    cart.values().stream().collect(Collectors.toList()),  //danh sách các mặt hàng mua
                    0.01f, //%Giảm giá
                    true   //Có tính thuế VAT không?
            );
        } else {
            return new Cart();
        }
    }

    public boolean checkout(Map<Long, OrderLine> rawCart, String email, Cart cart){
        Map<String, Object> content = new HashMap<>();
        content.put("rawCarts", rawCart.values());
        content.put("cart", cart);
        this.mailService.sendTextMail(MailService.MAIL_FROM, email, "Thư xác nhận đơn hàng", content, "authBuyProduct.html");
        return true;
    }
}
