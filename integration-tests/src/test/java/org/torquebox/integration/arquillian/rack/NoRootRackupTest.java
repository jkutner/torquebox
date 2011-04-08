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

package org.torquebox.integration.arquillian.rack;

import static org.junit.Assert.assertEquals;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.RunAsClient;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.torquebox.integration.arquillian.AbstractIntegrationTestCase;

@RunAsClient
public class NoRootRackupTest extends AbstractIntegrationTestCase {

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return createDeployment( "rack/norootrackup-knob.yml" );
    }

    @Test
    public void testHappiness() {
        driver.get( "http://localhost:8080/norootrackup" );
        WebElement body = driver.findElementByTagName( "body" );
        String expectedRoot = System.getProperty( "basedir" );
        expectedRoot = expectedRoot.replaceAll( "\\\\", "/" );
        expectedRoot = toVfsPath( expectedRoot + "/apps/rack/norootrackup" );

        assertEquals( "RACK_ROOT=" + expectedRoot, body.getText().trim() );
    }

}
