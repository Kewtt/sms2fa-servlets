package com.twilio.sms2fa.application.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isAuthenticated((HttpServletRequest) request)) {
            ((HttpServletResponse) response).sendRedirect("/sessions/new/");
        }

        chain.doFilter(request, response);
    }

    public boolean isAuthenticated(HttpServletRequest request){
        return Boolean.TRUE.equals(request.getSession().getAttribute("authenticated"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
