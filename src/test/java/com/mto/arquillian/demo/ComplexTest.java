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
import com.thoughtworks.selenium.DefaultSelenium;
import junit.framework.TestCase;
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
import java.net.URL;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 10/22/12
 */
@RunWith(Arquillian.class)
public class ComplexTest extends TestCase
{
   @Drone
   DefaultSelenium browser;

   @Deployment
   public static WebArchive deployWar()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "register.war");
      war.addClass(RegisterServlet.class);
      return war;
   }

   @Test
   @InSequence(0)
   @RunAsClient
   public void testAccessRegisterForm(@ArquillianResource URL deploymentURL)
   {
      browser.open(deploymentURL.toString() + "register/");
      browser.waitForPageToLoad("50000");
      String expectedHtml = "<head></head><body><form name=\"registerForm\" action=\"/register/register/\" method=\"POST\"><input name=\"username\" type=\"text\"><input name=\"ok_button\" type=\"submit\"></form></body>";
      assertEquals(expectedHtml, browser.getHtmlSource());
   }

   @Test
   @InSequence(1)
   @RunAsClient
   public void testRegister()
   {
      browser.type("name=username", "gatein");
      browser.click("name=ok_button");
      browser.waitForPageToLoad("50000");
      assertEquals("You have registered succesfully under the name: gatein", browser.getBodyText());
   }
}
