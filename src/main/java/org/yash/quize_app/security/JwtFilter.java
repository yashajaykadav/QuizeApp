package org.yash.quize_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yash.quize_app.entity.User;
import org.yash.quize_app.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtutil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
    throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        String email;

        try{
            email = jwtutil.extractEmail(token);

        }catch (Exception e){
            filterChain.doFilter(request,response);
            return;
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if(user!=null && jwtutil.isTokenValid(token,email)){

           var authorities = List.of(
                   new SimpleGrantedAuthority(user.getRole().name())
           );

           var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                   email,null,authorities
           );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }

}
