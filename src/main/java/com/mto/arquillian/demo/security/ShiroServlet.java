/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.mto.arquillian.demo.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 11/13/12
 */

@WebServlet(name = "secureservlet", urlPatterns = {"/secure"})
public class ShiroServlet extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      Subject s = SecurityUtils.getSubject();
      String message = "";
      if (s.getPrincipal() == null)
      {
         try
         {
            s.login(new UsernamePasswordToken("root", "wrongpass"));
            message = "You have logged in as root with password wrongpass";
         }
         catch (Exception ex)
         {
            s.login(new UsernamePasswordToken("root", "secret"));
            message = "You have logged in as root with password secret";
         }
      }
      else
      {
         message = "You have logged in";
      }
      resp.getWriter().write(message);
      resp.getWriter().flush();
   }
}
