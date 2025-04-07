package org.example

class MultithreadedApproach {

    fun run(lines: List<String>): String {
        val length = lines.size
        val countPerIp = mutableMapOf<String, Long>()

        val nCpu = Runtime.getRuntime().availableProcessors()

        //Work in progress
//        val chunkRanges = chunkRanges(length, nCpu)


        for (i in 0 until length) {
            val typeIndex = lines[i].indexOf(ERROR, startIndex = 8)
            if (typeIndex != -1) {
                val ip = lines[i].substring(0, typeIndex - 1)
                countPerIp[ip] = countPerIp.getOrDefault(ip, 0) + 1
            }
        }

        return countPerIp.maxBy { it.value }.key
    }

    fun chunkRanges(length: Long, chunks: Int): List<Pair<Long, Long>> {
        val baseChunkSize = length / chunks
        val remainder = length % chunks

        val result = mutableListOf<Pair<Long, Long>>()
        var start = 0L

        for (i in 0 until chunks) {
            val chunkSize = if (i < remainder) baseChunkSize + 1 else baseChunkSize
            val end = start + chunkSize - 1
            result.add(start to end)
            start = end + 1
        }

        return result
    }
}