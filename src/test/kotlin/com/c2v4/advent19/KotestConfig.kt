package com.c2v4.advent19

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.test.TestCaseOrder

object ProjectConfig : AbstractProjectConfig() {
  override val parallelism = 8
  override val testCaseOrder = TestCaseOrder.Random
}