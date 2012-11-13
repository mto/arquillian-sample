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
package com.mto.arquillian.demo;

import com.mto.arquillian.demo.security.ShiroServlet;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import java.net.URL;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 11/13/12
 */
@RunWith(Arquillian.class)
public class ShiroTest extends TestCase
{
   @Drone
   WebDriver driver;

   @Deployment
   public static WebArchive deploy()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "shiro.war");
      war.setWebXML("com/mto/arquillian/demo/shiro/web.xml");
      war.addAsWebInfResource("com/mto/arquillian/demo/shiro/shiro.ini");
      war.addClass(ShiroServlet.class);

      return war;
   }

   @Test
   @RunAsClient
   public void login(@ArquillianResource URL deploymentURL)
   {
      driver.get(deploymentURL.toString() + "secure");
      assertEquals("You have logged in as root with password secret", driver.getPageSource());
   }

   @Test
   @RunAsClient
   public void secondRequest(@ArquillianResource URL deploymentURL)
   {
      driver.get(deploymentURL.toString() + "secure");
      assertEquals("You have logged in", driver.getPageSource());
   }

}
