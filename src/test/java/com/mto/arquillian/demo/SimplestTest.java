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

import com.mto.arquillian.demo.simple.Greeting;
import com.mto.arquillian.demo.simple.GreetingImpl;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ServiceLoader;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 10/22/12
 */
@RunWith(Arquillian.class)
public class SimplestTest extends TestCase
{

   @Deployment
   public static JavaArchive deployJar()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "simple.jar");
      jar.addAsServiceProvider(Greeting.class, GreetingImpl.class);

      return jar;
   }

   //Possible to annotate this test case with RunAsClient
   @Test
   @InSequence(value = 1)
   public void firstExecution()
   {
      System.out.println("ARQUILLIAN TEST RUNNER WORKS!!!!!");
      System.out.println("Executing test case firstExecution");
   }

   //Impossible to annotate this test case with RunAsClient
   @Test
   @InSequence(value =2)
   public void checkDeployJar()
   {
      System.out.println("Executing test case checkDeployJar");
      Greeting g = ServiceLoader.load(Greeting.class).iterator().next();
      assertEquals("Hello the world by GreetingImpl", g.sayHello());
   }
}
