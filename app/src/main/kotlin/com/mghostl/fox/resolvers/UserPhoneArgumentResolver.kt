package com.mghostl.fox.resolvers

import com.mghostl.fox.model.FoxUserDetails
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class UserPhoneArgumentResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) = parameter.getParameterAnnotation(UserPhone::class.java) != null

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ) = SecurityContextHolder.getContext().authentication.principal
        .let { if(it !is FoxUserDetails) null else it}
        ?.username ?: "Unrecognized phone"
}