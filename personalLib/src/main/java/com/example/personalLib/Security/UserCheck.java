package com.example.personalLib.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserCheck {

    public static boolean hasUserRole()
    {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
            return true;
        return false;
    }

    public static boolean hasAdminRole()
    {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            return true;
        return false;
    }

    public static String getCurrentUserLogin()
    {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        return authentication.getName();
    }

}
