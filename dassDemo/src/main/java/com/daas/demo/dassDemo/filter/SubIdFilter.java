package com.daas.demo.dassDemo.filter;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SubIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String subId = servletRequest.getParameter("subId"); // or from header/request body
            if(subId!= null) {
                com.daas.demo.dassDemo.utility.RequestContextHolder.setSubId(Long.parseLong(subId));
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            com.daas.demo.dassDemo.utility.RequestContextHolder.clear(); // 🧹 Cleanup to prevent leakage
        }
    }
}

