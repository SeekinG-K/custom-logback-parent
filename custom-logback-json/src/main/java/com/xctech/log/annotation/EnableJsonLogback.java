package com.xctech.log.annotation;

import com.xctech.log.config.CustomJsonLogbackConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CustomJsonLogbackConfig.class})
public @interface EnableJsonLogback {
}
