package io.kotest.provided

import io.kotest.core.config.AbstractProjectConfig
import kotlin.time.Duration.Companion.seconds

object ProjectConfig : AbstractProjectConfig() {
    override val timeout = 60.seconds
}
