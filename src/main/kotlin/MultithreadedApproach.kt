package org.example

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.min

class MultithreadedApproach() {

    fun run(lines: List<String>): String {
        val nCpu = Runtime.getRuntime().availableProcessors()
        val executorService = Executors.newFixedThreadPool(nCpu)
        val chunkRanges = chunkRanges(lines.size, nCpu)

        try {
            val futures = chunkRanges.map { (start, end) ->
                executorService.submit(RunnerCallable(start, end, lines))
            }

            val results = HashMap<String, Long>()
            futures.forEach { future ->
                val localCounts = future.get(1, TimeUnit.SECONDS)
                localCounts.forEach { (ip, count) ->
                    results.merge(ip, count) { a, b -> a + b }
                }
            }

            return results.maxBy { it.value }.key
        } finally {
            executorService.shutdown()
            try {
                if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                    executorService.shutdownNow()
                }
            } catch (_: InterruptedException) {
                executorService.shutdownNow()
                Thread.currentThread().interrupt()
            }
        }
    }

    fun chunkRanges(linesSize: Int, numberOfChunks: Int): List<Pair<Int, Int>> {
        val chunkSize = linesSize / numberOfChunks
        val remainder = linesSize % numberOfChunks

        return List(numberOfChunks) { chunk ->
            val start = chunk * chunkSize + min(chunk, remainder)
            val end = start + chunkSize - 1 + if (chunk < remainder) 1 else 0
            start to end.coerceAtMost(linesSize - 1)
        }
    }

    private class RunnerCallable(
        private val start: Int,
        private val end: Int,
        private val lines: List<String>
    ) : Callable<Map<String, Long>> {

        override fun call(): Map<String, Long> {
            val localCounts = HashMap<String, Long>()

            for (i in start..end) {
                val line = lines[i]
                val typeIndex = line.indexOf(ERROR, startIndex = 8)
                if (typeIndex != -1) {
                    val ip = line.substring(0, typeIndex - 1)
                    localCounts[ip] = localCounts.getOrDefault(ip, 0) + 1
                }
            }

            return localCounts
        }
    }
}