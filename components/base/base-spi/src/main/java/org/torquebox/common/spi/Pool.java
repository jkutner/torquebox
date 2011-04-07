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

package org.torquebox.common.spi;

/**
 * A simple instance pool.
 * 
 * @author Bob McWhirter <bmcwhirt@redhat.com>
 * 
 * @param <T>
 *            The instance type.
 */
public interface Pool<T> {

    /**
     * Borrow an instance from the pool.
     * 
     * @return The borrowed instance.
     * @throws Exception
     *             if an error occurs.
     */
    T borrowInstance() throws Exception;

    /**
     * Borrow an instance from the pool.
     * 
     * @param timeout
     *            Wait time to acquire instance.
     * @return The borrowed instance.
     * @throws Exception
     *             if an error occurs.
     */
    T borrowInstance(long timeout) throws Exception;

    /**
     * Release an instance back into the pool.
     * 
     * @param instance
     *            The instance to release.
     */
    void releaseInstance(T instance);
    
    String getName();

}
