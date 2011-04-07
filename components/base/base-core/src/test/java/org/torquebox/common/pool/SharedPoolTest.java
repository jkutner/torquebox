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

package org.torquebox.common.pool;

import static org.junit.Assert.*;

import org.junit.Test;
import org.torquebox.common.spi.InstanceFactory;

public class SharedPoolTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoInstanceOrFactory() throws Exception {
        SharedPool<String> pool = new SharedPool<String>();

        pool.create();
    }

    @Test
    public void testInitialInstanceViaCtor() throws Exception {
        String instance = "tacos";

        SharedPool<String> pool = new SharedPool<String>( instance );

        pool.create();

        for (int i = 0; i < 100; ++i) {
            assertEquals( instance, pool.borrowInstance() );
        }
    }

    @Test
    public void testInitialInstanceViaAccessor() throws Exception {
        String instance = "tacos";

        SharedPool<String> pool = new SharedPool<String>();
        pool.setInstance( instance );

        pool.create();

        for (int i = 0; i < 100; ++i) {
            assertEquals( instance, pool.borrowInstance() );
        }
    }

    @Test
    public void testInstanceFactoryViaCtor() throws Exception {
        String instance = "tacos";

        SharedPool<String> pool = new SharedPool<String>( new MockInstanceFactory( instance ) );

        pool.create();

        for (int i = 0; i < 100; ++i) {
            assertEquals( instance, pool.borrowInstance() );
        }
    }

    @Test
    public void testInstanceFactoryViaAccessor() throws Exception {
        String instance = "tacos";

        SharedPool<String> pool = new SharedPool<String>();

        pool.setInstanceFactory( new MockInstanceFactory( instance ) );

        pool.create();

        for (int i = 0; i < 100; ++i) {
            assertEquals( instance, pool.borrowInstance() );
        }
    }

    class MockInstanceFactory implements InstanceFactory<String> {

        private String instance;

        public MockInstanceFactory(String instance) {
            this.instance = instance;
        }

        public String createInstance(String contextInfo) throws Exception {
            return this.instance;
        }

        @Override
        public void destroyInstance(String instance) {
            // no-op
        }

    }

}
