/*
 * Copyright 2008-2011 Red Hat, Inc, and individual contributors.
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

package org.torquebox.integration.arquillian.rails2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.RunAsClient;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.torquebox.integration.arquillian.AbstractIntegrationTestCase;

@RunAsClient
public class BasicBackwardsCompatTest extends AbstractIntegrationTestCase {

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return createDeployment( "rails2/basic-rails.yml" );
    }

    @Test
    public void testHighLevel() {
        driver.get( "http://localhost:8080/basic-rails" );
        WebElement element = driver.findElementById( "success" );
        assertNotNull( element );
        assertEquals( "basic-rails", element.getAttribute( "class" ) );
    }

    @Test
    public void testSendData() {
        driver.get( "http://localhost:8080/basic-rails/senddata" );
        String content = driver.getPageSource();
        assertNotNull( content );
        assertEquals( "this is the content", content );

    }

    @Test
    public void testSendFile() {
        driver.get( "http://localhost:8080/basic-rails/sendfile" );
        String content = driver.getPageSource();
        assertNotNull( content );
        assertEquals( "this is the contents of the file", content.trim() );

    }

}
