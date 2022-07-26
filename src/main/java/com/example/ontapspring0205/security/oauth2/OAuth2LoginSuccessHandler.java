package com.example.ontapspring0205.security.oauth2;

import com.example.ontapspring0205.entity.login.RoleEntity;
import com.example.ontapspring0205.entity.login.UserEntity;
import com.example.ontapspring0205.repository.IRoleRepository;
import com.example.ontapspring0205.repository.IUserRepository;
import com.example.ontapspring0205.security.config.AuthProvider;
import com.example.ontapspring0205.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IRoleRepository iRoleRepository;
    @Autowired
    UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2UserImpl customOAuth2User = (CustomOAuth2UserImpl) authentication.getPrincipal();

        String email = customOAuth2User.getEmail();
        String username = customOAuth2User.getName();
        String clientName = customOAuth2User.getClientName();
        String idClient = customOAuth2User.getID();
        String accessToken = customOAuth2User.getAccessToken();

        String urlImageFacebookOrGG ="";
        String alphaAndDigits ="";
        String userUpLoadPath = "/Users/vudt/Desktop/final_test/src/main/resources/static/imageUser";
        List<UserEntity> userEntityList = iUserRepository.findAllByEmail(email);
        UserEntity userEntity = new UserEntity();
       if(userEntityList.size() > 1){
           for (UserEntity u: userEntityList) {
               if (u.getAuthProvider().name().equalsIgnoreCase(clientName)){
                   userEntity = u;
               }
           }
       }else {
            userEntity = iUserRepository.findByEmail(email);
       }

        AuthProvider authProvider = null;
        if (clientName.equals("Facebook")){
            urlImageFacebookOrGG = "https://graph.facebook.com/"+idClient+"/picture?type=large&access_token="+accessToken;
            alphaAndDigits = username.replaceAll("\\s+", "")+idClient;
            authProvider = AuthProvider.FACEBOOK;
        }else if (clientName.equals("Google")){
            urlImageFacebookOrGG = customOAuth2User.getImage();
            alphaAndDigits = username.replaceAll("\\s+", "")+customOAuth2User.getSubGG();
            authProvider = AuthProvider.GOOGLE;
        }else {
            authProvider = AuthProvider.LOCAL;
        }

        String imageName = alphaAndDigits.replaceAll("[^a-zA-Z0-9]+","")+".jpg";
        String pathImages = userUpLoadPath +"/"+imageName;

        RoleEntity roleEntity = iRoleRepository.findById(2L).get();
//        List<RoleEntity> listRole = new ArrayList<>();
//        listRole.add(roleEntity);

        if (userEntity == null || (userEntity != null && userEntity.getAuthProvider() != authProvider)){
            UserEntity newUser = UserEntity.builder()
                    .id(null)
                    .userName(username)
                    .email(email)
                    .password(null)
                    .phone(null)
                    .address(null)
                    .photo(imageName)
                    .dateCreate(null)
                    .status(true)
                    .authProvider(authProvider)
                    .role(roleEntity)
                    .build();

            userService.save(newUser);
        }else {
            userEntity.setAuthProvider(authProvider);
            userEntity.setUserName(username);
            userEntity.setEmail(email);
            userEntity.setPhoto(imageName);
            userEntity.setRole(roleEntity);
            userService.save(userEntity);
        }
        userService.downloadImage(urlImageFacebookOrGG,pathImages);

//        response.sendRedirect(  "/user/home/page/1");
        request.getRequestDispatcher("/user/home/page/1").forward(request,response);
    }
}
