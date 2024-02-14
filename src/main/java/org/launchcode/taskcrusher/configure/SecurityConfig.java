package org.launchcode.taskcrusher.configure;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
//                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests((requests) -> requests
//                      //  .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,  "/api/parentLogin", "/api/register",
//                                "/api/kidLogin").permitAll()//<-ONLY endpoints where auth is not required
//                        .requestMatchers(HttpMethod.GET,"/api/chores/list","/api/parent-dashboard/statistics", "/api/chores/edit/{choreId}","/api/assignments/kids").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/chores/add","/api/assignments/assign-chore" ).permitAll()
//                        .requestMatchers(HttpMethod.PUT,"/api/chores/edit/{choreId}" ).permitAll()
//                        .requestMatchers(HttpMethod.DELETE,"/api/chores/{choreId}" ).permitAll()
//                        .anyRequest().authenticated()
//
//                );
//
//        return http.build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests

                        .requestMatchers(HttpMethod.POST, "/api/parentLogin", "/api/register", "/api/kidLogin","/api/kidRegister").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}