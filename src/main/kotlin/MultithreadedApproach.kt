package org.example

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.min

class MultithreadedApproach() {

    fun run(lines: List<String>): String {
        val nCpu = Runtime.getRuntime().availableProcessors()
        val executorService = Executors.newFixedThreadPool(nCpu)
        val chunkRanges = chunkRanges(lines.size, nCpu)
        val results = ConcurrentHashMap<String, Long>()

        try {
            chunkRanges
                .map { (start, end) ->
                    executorService.submit(RunnerRunnable(start, end, lines, results))
                }
                .toList()
                .forEach { future ->
                    future.get(1, TimeUnit.SECONDS)
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

    private class RunnerRunnable(
        private val start: Int,
        private val end: Int,
        private val lines: List<String>,
        private val results: ConcurrentHashMap<String, Long>
    ) : Runnable {
        override fun run() {
            val localCounts = mutableMapOf<String, Long>()

            for (i in start..end) {
                val line = lines[i]
                val typeIndex = line.indexOf(ERROR, startIndex = 8)
                if (typeIndex != -1) {
                    val ip = line.substring(0, typeIndex - 1)
                    localCounts[ip] = localCounts.getOrDefault(ip, 0) + 1
                }
            }

            localCounts.forEach { (ip, count) ->
                results.merge(ip, count, Long::plus)
            }
        }
    }
}