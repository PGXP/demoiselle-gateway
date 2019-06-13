/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.gateway;

import java.io.Serializable;
import org.demoiselle.jee.configuration.annotation.Configuration;
import org.demoiselle.jee.configuration.annotation.ConfigurationSuppressLogger;

/**
 *
 * @author SERPRO
 */
@Configuration(prefix = "demoiselle.gateway")
public class DemoiselleGatewayConfig implements Serializable {

    @ConfigurationSuppressLogger
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
