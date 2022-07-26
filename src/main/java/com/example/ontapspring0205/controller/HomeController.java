package com.example.ontapspring0205.controller;

import com.example.ontapspring0205.dto.HomeDto;
import com.example.ontapspring0205.model.HomeModel;
import com.example.ontapspring0205.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping("/tableList")
    public String getHomes(Model model, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of( 0, 4);
        model.addAttribute("homes", homeService.getHomes(pageRequest));
        return "admin/tables";
    }

    @GetMapping("/tableList/page/{page}")
    public ModelAndView ListHomePages( @PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/tables");
        PageRequest pageRequest = PageRequest.of(page - 1, 4);
        Page<HomeDto> homePage = homeService.getHomesPage(pageRequest);

        int totalPages = homePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("homes", homePage);
        return modelAndView;
    }

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("homeModel", new HomeModel());
        model.addAttribute("categories", homeService.getCategories());
        return "admin/formAdd";
    }

    @PostMapping("/save")
    public String save(Model model,@ModelAttribute("homeModel") HomeModel homeModel, @RequestParam("home_image")MultipartFile file, @RequestParam("categoryId") Long categoryId) {
        if (homeModel.getId()  == null) {
            if (homeService.saveHome(homeModel, file, categoryId)) {
                return "redirect:/home/tableList/page/1";
            }
            model.addAttribute("message", "Add New Home Error");
            return "redirect:/home/add";
        }else{
            if (homeService.updateHome(homeModel, file, categoryId)) {
                return "redirect:/home/tableList/page/1";
            }
            model.addAttribute("message", "Update Home Error");
            return "redirect:/home/edit/" + homeModel.getId();
        }
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("homeModel", homeService.getHomeById(id));
        model.addAttribute("categories", homeService.getCategories());
        return "admin/formEdit";
    }

    @GetMapping("/delete/{id}")
    public String deleteHome(Model model, @PathVariable("id") Long id) {
        if (homeService.deleteHome(id)) {
            return "redirect:/home/tableList";
        }
        model.addAttribute("message", "Delete Home Error");
        return "redirect:/home/tableList";
    }

    @PostMapping("/search")
    public ModelAndView getHomeByName(@RequestParam("name-home") String name) {
        ModelAndView modelAndView = new ModelAndView("admin/tables");
        PageRequest pageRequest = PageRequest.of(0, 4);
        Page<HomeDto> homePage = homeService.getHomeByName(pageRequest, name);

        int totalPages = homePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("homes", homePage);
        return modelAndView;
    }

    @GetMapping("/filter")
    public ModelAndView getHomeByCategory(@RequestParam("nameCategory") Long categoryId) {
        ModelAndView modelAndView = new ModelAndView("admin/tables");
        PageRequest pageRequest = PageRequest.of(0, 4);
        Page<HomeDto> homePage = homeService.getHomeByCategoryPage(pageRequest, categoryId);

        int totalPages = homePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("homes", homePage);
        return modelAndView;
    }

    @GetMapping("/charts")
    public String getCharts(Model model) {
        model.addAttribute("1room", homeService.buiderChart(1L));
        model.addAttribute("2room", homeService.buiderChart(2L));
        return "admin/charts";
    }
}
