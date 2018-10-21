package space.efremov.otusspringlibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.security.remember-me-key}")
    private String rememberMeKey;

    private final JpaAuthenticationManager jpaAuthenticationManager;

    public SecurityConfig(JpaAuthenticationManager jpaAuthenticationManager) {
        this.jpaAuthenticationManager = jpaAuthenticationManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/login").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/**").authenticated()
                .and()
                .formLogin().usernameParameter("nickname").passwordParameter("password")
                .loginPage("/login")
                .defaultSuccessUrl("/login/success")
                .failureUrl("/login?failure")
                .and()
                .rememberMe().key(rememberMeKey);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jpaAuthenticationManager);
    }
}
