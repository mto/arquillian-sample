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

import com.mto.arquillian.demo.moderate.GreetingServlet;
import com.mto.arquillian.demo.moderate.WelcomeServlet;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 10/22/12
 */
@RunWith(Arquillian.class)
public class ModerateTest extends TestCase
{
   @ArquillianResource
   Deployer deployer;

   @Deployment()
   public static WebArchive firstDeploy()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "first.war");
      //war.setWebXML("com/mto/arquillian/demo/moderate/web.xml");
      war.addClass(GreetingServlet.class);
      war.addClass(WelcomeServlet.class);

      return war;
   }

   //managed = false means this deployment is not deployed by Arquillian
   @Deployment(name = "secondDeploy", managed = false)
   public static WebArchive secondDeploy()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "second.war");
      war.addClass(GreetingServlet.class);
      return war;
   }

   @Test
   @InSequence(0)
   @RunAsClient
   public void testFirstDeploy(@ArquillianResource URL url) throws Exception
   {
      assertEquals("http://localhost:8080/first/", url.toString());

      URL welcomeURL = new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getPath() + "welcome/");
      HttpURLConnection conn = (HttpURLConnection)welcomeURL.openConnection();
      conn.setInstanceFollowRedirects(false);
      conn.connect();
      assertEquals(302, conn.getResponseCode());
      String location = conn.getHeaderField("Location");
      assertEquals("http://localhost:8080/first/greeting", location);

      HttpURLConnection nextConn = (HttpURLConnection)new URI(location).toURL().openConnection();
      nextConn.connect();
      assertEquals(200, nextConn.getResponseCode());
      InputStream in = nextConn.getInputStream();
      assertEquals("Hello!", new BufferedReader(new InputStreamReader(in)).readLine());
   }

   @Test
   @InSequence(1)
   @RunAsClient
   public void testSecondDeploy() throws Exception
   {
      deployer.deploy("secondDeploy");
      URL greetingURL = new URI("http://localhost:8080/second/greeting/").toURL();
      HttpURLConnection conn = (HttpURLConnection)greetingURL.openConnection();
      conn.connect();
      assertEquals(200, conn.getResponseCode());
      assertEquals("Hello!", new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine());
   }

   @Test
   @InSequence(2)
   public void testUndeploy() throws Exception
   {
      deployer.undeploy("secondDeploy");
      URL greetingURL = new URI("http://localhost:8080/second/greeting/").toURL();
      HttpURLConnection conn = (HttpURLConnection)greetingURL.openConnection();
      conn.connect();
      assertEquals(404, conn.getResponseCode());
   }
}
