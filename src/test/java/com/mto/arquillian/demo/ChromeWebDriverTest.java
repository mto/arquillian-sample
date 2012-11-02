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

import com.mto.arquillian.demo.complex.RegisterServlet;
import junit.framework.TestCase;
import qualifier.Chrome;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.net.URL;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 11/2/12
 */
@RunWith(Arquillian.class)
public class ChromeWebDriverTest extends TestCase
{
   @Drone
   @Chrome
   WebDriver driver;

   @Deployment
   public static WebArchive deployWar()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "webdriver.war");
      war.addClass(RegisterServlet.class);
      return war;
   }

   @Test
   @RunAsClient
   @InSequence(0)
   public void testAccessRegisterForm(@ArquillianResource URL url) throws Exception
   {
      driver.navigate().to(url.toString() + "register/");
      WebElement form = driver.findElement(By.tagName("form"));
      assertNotNull(form);
   }

   @Test
   @RunAsClient
   @InSequence(1)
   public void testRegister(@ArquillianResource URL url) throws Exception
   {
      driver.navigate().to(url.toString() + "register/");
      WebElement form = driver.findElement(By.tagName("form"));
      form.findElement(By.name("username")).sendKeys("gatein");
      form.submit();
      assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body><pre>You have registered succesfully under the name: gatein</pre></body></html>", driver.getPageSource());
   }
}
