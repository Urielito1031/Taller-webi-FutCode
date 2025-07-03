package com.tallerwebi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

   @Override
   public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {

      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");

      if (usuarioId == null) {
         response.sendRedirect(request.getContextPath() + "/login");
         return false;
      }

      return true;
   }
}
