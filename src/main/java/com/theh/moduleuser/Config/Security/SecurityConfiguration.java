package com.theh.moduleuser.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private ApplicationRequestFilter applicationRequestFilter;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //configuration de authentication manager
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
    // TODO bean pour la hierarchie des role et implementation

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ROOT > ROLE_ADMIN\n" +
                "ROLE_ADMIN > ROLE_STAFF\n" +
                "ROLE_STAFF > ROLE_USER"); // TODO implementé le role guest plutard
        return hierarchy;
    }

    // and, if using method security also add
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
    //End
    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
//        UserDetails admin = Utilisateur.withUsername("Basant")
//                .password(encoder.encode("Pwd1"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = Utilisateur.withUsername("John")
//                .password(encoder.encode("Pwd2"))
//                .roles("USER","ADMIN","HR")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
        return new MyUserDetailsService();
    }

    //TODO desactivation du cors avant spring security

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf-> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST,"/nouveau/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/muslimApi/v1/authentication/authenticate",
                                //--
                                "/muslimApi/v1/mosque/find/{idmosque}",
                                "/muslimApi/v1/mosque/find/localisation/{villeOrPays}",
                                "/muslimApi/v1/mosque/find/vendredi/{vendredi}",
                                "/muslimApi/v1/mosque/find/all/",
                                "/muslimApi/v1/mosque/find/",
                                "/muslimApi/v1/mosque/find/nom/{nom}",
                                "/muslimApi/v1/mosque/display/{id}",
                                "/muslimApi/v1/localisation/all",
                                "/muslimApi/v1/localisation/all/pages/",
                                "/muslimApi/v1/localisation/{idmosque}",
                                "/muslimApi/v1/predication/{idpredication}",
                                "/muslimApi/v1/predication/find/{nom}",
                                "/muslimApi/v1/predication/find/type/{type}",
                                "/muslimApi/v1/predication/find/theme/{theme}",
                                "/muslimApi/v1/predication/all",
                                "/muslimApi/v1/mosque/",
                                "/muslimApi/v1/mosque/",
                                //--
                                "/muslimApi/v1/authentication/token/verify/{jwtToken}",
                                "/muslimApi/v1/image/display/{id}/{context}",
                                "/muslimApi/v1/documents/downloads/{id}/",
                                "/v2/api-docs/**",
                                "/health",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )

                //TODO logout implementation fonctionne mais pas a 100%
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}
