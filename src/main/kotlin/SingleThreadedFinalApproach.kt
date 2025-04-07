package org.example


class SingleThreadedFinalApproach {

    fun run(lines: List<String>): String {
        val length = lines.size
        val countPerIp = mutableMapOf<String, Long>()

        for (i in 0 until length) {
            val typeIndex = lines[i].indexOf(ERROR, startIndex = 8)
            if (typeIndex != -1) {
                val ip = lines[i].substring(0, typeIndex - 1)
                countPerIp[ip] = countPerIp.getOrDefault(ip, 0) + 1
            }
        }

        return countPerIp.maxBy { it.value }.key
    }
}