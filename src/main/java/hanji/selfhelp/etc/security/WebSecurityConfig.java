package hanji.selfhelp.etc.security;

import hanji.selfhelp.etc.security.jwt.AuthEntryPointJwt;
import hanji.selfhelp.etc.security.jwt.AuthTokenFilter;
import hanji.selfhelp.etc.security.services.CustomOauth2Provider;
import hanji.selfhelp.etc.security.services.CustomOauth2UserService;
import hanji.selfhelp.etc.security.services.UserDetailsServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    CustomOauth2UserService customOauth2UserService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties){

        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull) .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    public ClientRegistration getRegistration(OAuth2ClientProperties oAuth2ClientProperties, String client) {

        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get(client);
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();

        if (clientId == null) {
            return null;
        }

        switch (client){//구글, 페이스북은 제공, 네이버 카카오는 따로 Provider 선언해줘야함
            case "google":
                return CustomOauth2Provider.GOOGLE.getBuilder(client)
                        .clientId(clientId).clientSecret(clientSecret).build();
            case "facebook":
                return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                        .clientId(clientId).clientSecret(clientSecret).build();
            case "kakao":
                return CustomOauth2Provider.KAKAO.getBuilder(client)
                        .clientId(clientId)
                        .clientSecret(clientSecret).build();
            case "naver":
                return CustomOauth2Provider.NAVER.getBuilder(client)
                        .clientId(clientId)
                        .clientSecret(clientSecret).build();
        }
        return null;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //jwt
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
//                .antMatchers("/api/diaries/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //oauth
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(customOauth2UserService);
    }
}























