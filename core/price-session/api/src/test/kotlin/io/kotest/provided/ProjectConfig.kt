package io.kotest.provided

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.SpecExecutionOrder
import kotlin.time.Duration.Companion.seconds

object ProjectConfig : AbstractProjectConfig() {
    override val timeout = 30.seconds
    override val specExecutionOrder = SpecExecutionOrder.Random
}
