package com.semillero.ecosistema.security;

import com.semillero.ecosistema.repositories.IUserRepository;
import com.semillero.ecosistema.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {
     private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter; // Inyección de JwtTokenAuthenticationFilter

    @Autowired
    public SecurityConfig(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) { // Inyección por constructor
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }
    RequestMatcher publicUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/login/oauth2/**"),
            new AntPathRequestMatcher("/supplier", "GET"),
            new AntPathRequestMatcher("/publication", "GET"),
            new AntPathRequestMatcher("/category", "GET"),
            new AntPathRequestMatcher("/supplier/searchbyname"),
            new AntPathRequestMatcher("/supplier/searchbycategory"),
            new AntPathRequestMatcher("/supplier"),
            new AntPathRequestMatcher("/supplier/searchallacepted"),
            new AntPathRequestMatcher("/supplier/searchAllReviewAndChange"),
             new AntPathRequestMatcher("/supplier/image"),
            new AntPathRequestMatcher("/supplier/getById/{id}"),
            new AntPathRequestMatcher("/supplier/allNews"),
            new AntPathRequestMatcher("/category"),
            new AntPathRequestMatcher("/sendMail"),
            new AntPathRequestMatcher("/location/{lat}/{lng}/{accept}"),
            new AntPathRequestMatcher("/publication/increaseView/{idUser}/{idPublication}"),
            new AntPathRequestMatcher("/publication/getById/{id_publication}"),
            new AntPathRequestMatcher("/chat/initial"),
            new AntPathRequestMatcher("/chat/answer/{id}"),
            new AntPathRequestMatcher("/supplier/create/{id}")


    );

    RequestMatcher providerUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/publication/edit/**"),
            new AntPathRequestMatcher("/publication/create/**"),
            new AntPathRequestMatcher("/provider"),
            new AntPathRequestMatcher("/supplier/create"),
            new AntPathRequestMatcher("/supplier/update"),
            new AntPathRequestMatcher("/image/deleted/{id}"),
            new AntPathRequestMatcher("/paises"),
            new AntPathRequestMatcher("/provincias")

    );

    RequestMatcher adminUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/admin"),
            new AntPathRequestMatcher("/rubros/get-all"),
            new AntPathRequestMatcher("/auth/user/details"),
            new AntPathRequestMatcher("/publication/create/{id}"),
            new AntPathRequestMatcher("/publication/edit/{id_publication}/{id_user}"),
            new AntPathRequestMatcher("/publication/delete/{id}"),
            new AntPathRequestMatcher("/supplier/changeStatus/{id}"),
            new AntPathRequestMatcher("/supplier/allDeniedSupplier"),
            new AntPathRequestMatcher("/supplier/searchAllReviewAndChange"),
            new AntPathRequestMatcher("/supplier/changeStatus/{id}"),
            new AntPathRequestMatcher("/statistics/quantitySupplierByCategory"),
            new AntPathRequestMatcher("/statistics/quantitySupplierByStatus"),
            new AntPathRequestMatcher("/statistics/publicationByQuantityViews")



            );

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(publicUrls).permitAll();
                    auth.requestMatchers(adminUrls).hasAuthority("ADMINISTRADOR");
                    auth.requestMatchers(providerUrls).hasAuthority("PROVEEDOR");
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore( jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
