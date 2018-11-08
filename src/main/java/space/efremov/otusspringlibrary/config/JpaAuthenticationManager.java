package space.efremov.otusspringlibrary.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.repository.UserRepository;

import java.util.Collections;

@Component
public class JpaAuthenticationManager implements AuthenticationProvider {

    private final UserRepository userRepository;

    public JpaAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return userRepository.findByUsername(authentication.getPrincipal().toString())
                .map(user -> {
                    if (isPasswordCorrect(authentication.getCredentials().toString(), user)) {
                        return new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword(),
                                Collections.singletonList((GrantedAuthority) () -> user.getRole().toString())
                        );
                    } else {
                        return null;
                    }
                })
                .orElse(null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


    private boolean isPasswordCorrect(String testPassword, User user) {
        return SecurityConfig.passwordEncoder.matches(testPassword, user.getPassword());
    }
}
