package com.example.signup.filter;

//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@AllArgsConstructor
//@Slf4j
//public class RedisSessionCheck extends OncePerRequestFilter {
//
//    private final StringRedisTemplate redisTemplate;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String sessionToken = request.getHeader("X-Session-Token");
//        log.debug("inside the dp filter internal {}",sessionToken);
//        if(null != sessionToken){
//            String key = "session:"+sessionToken;
//            String userDetails = redisTemplate.opsForValue().get(key);
//            if(null != userDetails){
//                String[] arraysOfTokenDetails = userDetails.split(":");
//                String username = arraysOfTokenDetails[0];
//                List<SimpleGrantedAuthority> authorityList = Arrays.stream(arraysOfTokenDetails[1].split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorityList);
//
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//                redisTemplate.expire(key,100, TimeUnit.SECONDS);
//            }
//        }
//        log.debug("final of the do filter");
//        filterChain.doFilter(request,response);
//    }
//}
