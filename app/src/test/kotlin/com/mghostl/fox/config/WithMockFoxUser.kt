package com.mghostl.fox.config

import org.springframework.security.test.context.support.WithSecurityContext
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@WithSecurityContext(factory = WithMockFoxUserSecurityContextFactory::class)
annotation class WithMockFoxUser(
    val roles: Array<String> = [],
    val phone: String = ""
)
