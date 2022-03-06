package com.mghostl.fox.rusgolf.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import javax.validation.constraints.NotNull

@ConfigurationProperties(prefix = "rusgolf")
data class RusGolfProperties (
    @NotNull
    var host: String? = null
    )