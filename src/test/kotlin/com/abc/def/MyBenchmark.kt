package com.abc.def

import org.example.NaiveApproach
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.random.Random

open class MyBenchmark {

    @Fork(value = 1)
    @Warmup(iterations = 20, time = 2)
    @Measurement(iterations = 20, time = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    fun test1(blackhole: Blackhole, generatedData: GeneratedData) {
        val items = mutableListOf<Int>()

        NaiveApproach().run(generatedData.lines)

        blackhole.consume(items)
        blackhole.consume(generatedData)
    }
}