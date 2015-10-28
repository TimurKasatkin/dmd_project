package ru.innopolis.dmd.project.web.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
public class AuthenticationUtil {

    private static boolean isNotAuthenticated(Authentication authentication) {
        return authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken);
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !isNotAuthenticated(authentication);
    }

}
