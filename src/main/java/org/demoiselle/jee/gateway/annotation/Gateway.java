/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.gateway.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author PauloGladson
 */
@Inherited
@InterceptorBinding
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface Gateway {

    @Nonbinding
    boolean enable() default true;
}
