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
@Configuration(prefix = "demoiselle.billing")
public class DemoiselleGatewayConfig implements Serializable {

    @ConfigurationSuppressLogger
    private String hashcashKey;

    private Long timetoLiveMilliseconds;

    private Long challenge;

    public String getHashcashKey() {
        return hashcashKey;
    }

    public Long getTimetoLiveMilliseconds() {
        return timetoLiveMilliseconds;
    }

    public Long getChallenge() {
        return challenge;
    }

}
