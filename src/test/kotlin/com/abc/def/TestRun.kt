package com.abc.def

import org.example.MultithreadedApproach
import kotlin.test.Test

class TestRun {

    @Test
    fun checkImplementation() {
        val generatedData = listOf<String>("192.168.1.1,ERROR\n")
        val runner = MultithreadedApproach()
        runner.run(generatedData)
    }
}