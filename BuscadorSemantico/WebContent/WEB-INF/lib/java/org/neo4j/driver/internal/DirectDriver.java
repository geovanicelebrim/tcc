/**
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.neo4j.driver.internal;

import org.neo4j.driver.internal.net.BoltServerAddress;
import org.neo4j.driver.internal.net.pooling.PoolSettings;
import org.neo4j.driver.internal.net.pooling.SocketConnectionPool;
import org.neo4j.driver.internal.security.SecurityPlan;
import org.neo4j.driver.internal.spi.ConnectionPool;
import org.neo4j.driver.v1.Logging;
import org.neo4j.driver.v1.Session;

import static java.lang.String.format;

public class DirectDriver extends BaseDriver
{
    private final BoltServerAddress address;
    private final ConnectionPool connections;

    public DirectDriver( BoltServerAddress address, ConnectionSettings connectionSettings, SecurityPlan securityPlan,
                         PoolSettings poolSettings, Logging logging )
    {
        super( securityPlan, logging );
        this.address = address;
        this.connections = new SocketConnectionPool( connectionSettings, securityPlan, poolSettings, logging );
    }

    @Override
    public Session session()
    {
        return new InternalSession( connections.acquire( address ), log );
    }

    @Override
    public void close()
    {
        try
        {
            connections.close();
        }
        catch ( Exception ex )
        {
            log.error( format( "~~ [ERROR] %s", ex.getMessage() ), ex );
        }
    }

}
