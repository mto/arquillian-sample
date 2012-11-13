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
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 11/13/12
 */
public class ShiroBootstrap implements ServletContextListener
{
   @Override
   public void contextInitialized(ServletContextEvent servletContextEvent)
   {
      ServletContext ctx = servletContextEvent.getServletContext();
      String shiroConfig = ctx.getInitParameter("shiroConfigFile");

      InputStream in = ctx.getResourceAsStream(shiroConfig);
      Ini ini = new Ini();
      ini.load(in);

      Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
      SecurityManager sm = factory.getInstance();
      SecurityUtils.setSecurityManager(sm);
   }

   @Override
   public void contextDestroyed(ServletContextEvent servletContextEvent)
   {
   }
}
