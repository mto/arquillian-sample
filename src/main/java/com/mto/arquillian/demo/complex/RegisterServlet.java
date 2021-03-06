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
package com.mto.arquillian.demo.complex;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 10/23/12
 */
@WebServlet(name = "registerservlet", urlPatterns = {"/register/*"})
public class RegisterServlet extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      Writer writer = resp.getWriter();
      writer.write("<html><head></head><body>");
      writer.write("<form name='registerForm' action='" + req.getRequestURI() + "' method='POST' >");
      writer.write("<input name='username' type='text' />");
      writer.write("<input name='ok_button' type='submit' />");
      writer.write("</form>");
      writer.write("</body></html>");
      writer.flush();
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      String username = req.getParameter("username");
      if(username == null)
      {
         doGet(req, resp);
      }
      else
      {
         Writer writer = resp.getWriter();
         writer.write("You have registered succesfully under the name: " + username);
         writer.flush();
      }
   }
}
