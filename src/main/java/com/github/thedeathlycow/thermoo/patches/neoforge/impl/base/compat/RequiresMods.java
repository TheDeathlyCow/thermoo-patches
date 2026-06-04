package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.compat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface RequiresMods {
    String[] value();
}