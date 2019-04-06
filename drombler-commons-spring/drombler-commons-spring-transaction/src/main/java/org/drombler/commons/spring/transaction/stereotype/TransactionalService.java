package org.drombler.commons.spring.transaction.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.softsmithy.lib.util.BusinessException;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author puce
 */
@Inherited
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Service
@Transactional(rollbackFor = BusinessException.class)
@Target(value = {ElementType.TYPE})
public @interface TransactionalService {

    /**
     * The suggested component name or else empty String.
     *
     * @return the suggested component name or else empty String
     */
    @AliasFor(annotation = Service.class)
    String value() default "";
}
