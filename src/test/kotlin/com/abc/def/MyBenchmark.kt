package com.abc.def

import org.example.SingleThreadedFinalApproach
import org.example.MultithreadedApproach
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

open class MyBenchmark {

    @Fork(value = 1)
    @Warmup(iterations = 15, time = 2)
    @Measurement(iterations = 15, time = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    fun benchmarkNaive(blackhole: Blackhole, generatedData: GeneratedData) {
        blackhole.consume(NaiveApproach().run(generatedData.lines))
    }

    @Fork(value = 2)
    @Warmup(iterations = 15, time = 1)
    @Measurement(iterations = 15, time = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    fun benchmarkSingleThreadFinal(blackhole: Blackhole, generatedData: GeneratedData) {
        blackhole.consume(SingleThreadedFinalApproach().run(generatedData.lines))
    }

    @Fork(value = 2)
    @Warmup(iterations = 15, time = 1)
    @Measurement(iterations = 15, time = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    fun benchmarkMultithreaded(blackhole: Blackhole, generatedData: GeneratedData) {
        blackhole.consume(MultithreadedApproach().run(generatedData.lines))
    }
}