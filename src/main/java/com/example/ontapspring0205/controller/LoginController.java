package com.example.ontapspring0205.controller;

import com.example.ontapspring0205.dto.RoleDto;
import com.example.ontapspring0205.entity.login.UserEntity;
import com.example.ontapspring0205.model.UserModel;
import com.example.ontapspring0205.repository.IRoleRepository;
import com.example.ontapspring0205.repository.IUserRepository;
import com.example.ontapspring0205.security.config.AuthenSuccessHandlerLogin;
import com.example.ontapspring0205.security.config.UserDetailsImpl;
import com.example.ontapspring0205.service.UserService;
import com.example.ontapspring0205.service.emailService.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class LoginController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    AuthenSuccessHandlerLogin successHandlerLogin;
    @Autowired
    ResetPasswordService resetPasswordService;

    @GetMapping(value = {"/login", "", "/"})
    public String loginPage(Model model) {
        List<RoleDto> roleDTOList = userService.getAllRole();
        model.addAttribute("listRole", roleDTOList);
        model.addAttribute("userModel", new UserModel());
        return "login/loginForm";
    }

//    @GetMapping("/user")
//    public String getListUser() {
//        return "user/userLoginSuccess";
//    }

    @GetMapping("/admin")
    public String getListAdmin() {
        return "admin/adminLoginSuccess";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("userModel") UserModel userModel, Model model, @RequestParam("roleId") Long roleId) {
        userModel.setRoleModel(userService.getRoleModelById(roleId));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        UserEntity userEntityNew = userModel.ModelToEntity(userModel);
        // check email da ton tai ? tao ra thong bao email da dung de dang ky : save vao DB va thong bao dang ky thanh cong
        List<UserEntity> userEntityList = userRepository.findAll();
        Optional<UserEntity> emailCheck = userEntityList.stream().filter(u -> u.getEmail().equalsIgnoreCase(userModel.getEmail())).findFirst();
        if (!emailCheck.isPresent()) {
            userService.save(userEntityNew);
            model.addAttribute("message", "dang ky thanh cong");
            return "redirect:/login";
        } else {
            model.addAttribute("message", "tai khoan email da dang ky, hay dung email khac");
            return "redirect:/login";
        }
    }

    @PostMapping("/signIn")
    public void LoginAction(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpServletResponse res, HttpServletRequest req) throws ServletException, IOException {
        UserEntity findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            model.addAttribute("message", "user khong ton tai");
            req.getRequestDispatcher("/login").forward(req, res);
        }

        if (BCrypt.checkpw(password, findUser.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(new UserDetailsImpl(findUser),
                    null, Collections.singleton(new SimpleGrantedAuthority(findUser.getRole().getName())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            try {
                successHandlerLogin.onAuthenticationSuccess(req, res, authentication);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    // Reset password not done

//    @PostMapping("/forgot-password")
//    public String forgotPassword(@RequestParam("email") String email, Model model) {
//        resetPasswordService.sendMail(email);
//        return "successResetPass";
//    }
//
//    @GetMapping("/reset-password")
//    public String resetPassword(@RequestParam("token") String token, Model model) {
//        if (resetPasswordService.checkToken(token)) {
//            model.addAttribute("token", token);
//            return "resetPassword";
//        } else {
//            model.addAttribute("message", "token khong hop le");
//            return "loginForm";
//        }
//    }
//
//    @PostMapping("/reset-password")
//    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password, Model model) {
//        if (resetPasswordService.checkToken(token)) {
//            resetPasswordService.resetPassword(token, password);
//            model.addAttribute("message", "reset password thanh cong");
//            return "loginForm";
//        } else {
//            model.addAttribute("message", "token khong hop le");
//            return "loginForm";
//        }
//    }
//}

//    @GetMapping("/reset-password")
//    public String resetPassword(@RequestParam("token") String token, Model model) {
//        model.addAttribute("token", token);
//        return "formResetPassword";
//    }
//
//    @PostMapping("/reset-password")
//    public String resetPassword( Model model, HttpServletResponse res, HttpServletRequest req) throws ServletException, IOException {
//        String email = (String) req.getAttribute("email");
//        UserEntity userEntity = userRepository.findByEmail(email);
//        if (userEntity != null) {
//            model.addAttribute("user", userEntity);
//            model.addAttribute("message", " reset password success");
//            return "loginForm";
//        } else {
//            model.addAttribute("message", "email not found");
//            return "loginForm";
//        }
//    }
}
