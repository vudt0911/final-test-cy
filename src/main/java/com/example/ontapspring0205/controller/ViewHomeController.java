package com.example.ontapspring0205.controller;

import com.example.ontapspring0205.dto.HomeDto;
import com.example.ontapspring0205.repository.IHomeRepository;
import com.example.ontapspring0205.service.CartService;
import com.example.ontapspring0205.service.HomeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("user")
public class ViewHomeController {
    @Autowired
    HomeService homeService;

    @Autowired private IHomeRepository homeRepository;
    @Autowired private CartService cartService;

    @GetMapping("home/page/{page}")
    public ModelAndView ListHomePages(@PathVariable("page") int page,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("user/userLoginSuccess");
        PageRequest pageRequest = PageRequest.of(page - 1, 4);
        Page<HomeDto> homePage = homeService.getHomesPage(pageRequest);

        modelAndView.addObject("homes", homeService.getHomes(pageRequest));
        modelAndView.addObject("cartCount", cartService.countItemInCart(session));

        int totalPages = homePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("homes", homePage);
        return modelAndView;
    }

    @GetMapping("/buy/{id}")
    public String buy(@PathVariable(name = "id") Long id, HttpSession session, Model model) {
        cartService.addToCart(id, session);
        return "redirect:/user/home/page/1";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        return "user/checkout";
    }

    @GetMapping("/filter/{page}")
    public ModelAndView getHomeByCategory(@RequestParam("nameCategory") Long categoryId, @PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("user/userLoginSuccess");
        PageRequest pageRequest = PageRequest.of(page - 1, 4);
        Page<HomeDto> homePage = homeService.getHomeByCategoryPage(pageRequest, categoryId);

        int totalPages = homePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("homes", homePage);
        return modelAndView;
    }

    @PostMapping("/search/{page}")
    public ModelAndView getHomeByName(@RequestParam("name-home") String name, @PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("user/userLoginSuccess");
        PageRequest pageRequest = PageRequest.of(page-1, 4);
        Page<HomeDto> homePage = homeService.getHomeByName(pageRequest, name);

        int totalPages = homePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("homes", homePage);
        return modelAndView;
    }

//    @GetMapping("/detail/{id}")
//    public String detailProduct(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("product", productService.getProductById(id));
//        return "user/detailProduct";
//    }
}
