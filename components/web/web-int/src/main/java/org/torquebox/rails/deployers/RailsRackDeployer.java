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

package org.torquebox.rails.deployers;

import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.spi.deployer.DeploymentStages;
import org.jboss.deployers.spi.deployer.helpers.AbstractDeployer;
import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.torquebox.base.metadata.RubyApplicationMetaData;
import org.torquebox.rack.metadata.RackApplicationMetaData;
import org.torquebox.rails.metadata.RailsApplicationMetaData;

/**
 * <pre>
 * Stage: POST_PARSE
 *    In: RailsApplicationMetaData, RackApplicationMetaData
 *   Out: RackApplicationMetaData
 * </pre>
 * 
 * All Rails apps are essentially Rack apps, so from a Rails app we construct
 * Rack metadata to hand off to the Rack deployers. We assume that any deployer
 * that attached a RailsApplicationMetaData also attached a corresponding
 * RackApplicationMetaData.
 */
public class RailsRackDeployer extends AbstractDeployer {

    public static final String STATIC_PATH_PREFIX = "public";

    public RailsRackDeployer() {
        setInput( RailsApplicationMetaData.class );
        addRequiredInput( RubyApplicationMetaData.class );
        addRequiredInput( RackApplicationMetaData.class );

        addOutput( RackApplicationMetaData.class );
        setStage( DeploymentStages.POST_PARSE );
    }

    @Override
    public void deploy(DeploymentUnit unit) throws DeploymentException {
        RailsApplicationMetaData railsAppMetaData = unit.getAttachment( RailsApplicationMetaData.class );

        try {
            RackApplicationMetaData rackMetaData = unit.getAttachment( RackApplicationMetaData.class );

            if (railsAppMetaData.isRails3()) {
                rackMetaData.setRackUpScriptLocation( "config.ru" );
            } else {
                rackMetaData.setRackUpScript( getRackUpScript( rackMetaData.getContextPath() ) );
                rackMetaData.setRackUpScriptLocation( null );
            }

            rackMetaData.setStaticPathPrefix( STATIC_PATH_PREFIX );
        } catch (Exception e) {
            throw new DeploymentException( e );
        }
    }

    protected String getRackUpScript(String context) {
        if (context.endsWith( "/" )) {
            context = context.substring( 0, context.length() - 1 );
        }
        return "require %q(org/torquebox/rails/deployers/rackup)\n" + "run TorqueBox::Rails.app\n";

    }

}
