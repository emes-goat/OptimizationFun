package com.abc.def

import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.util.concurrent.ThreadLocalRandom


@State(Scope.Benchmark)
open class GeneratedData {

    companion object {
        private const val NUMBER_OF_IP_ADDRESSES = 100
        private const val NUMBER_OF_LINES = 50_000_000
    }

    val lines = ArrayList<String>(NUMBER_OF_LINES)

    @Setup(Level.Trial)
    fun setUp() {
        val ipPool = List(NUMBER_OF_IP_ADDRESSES) { i ->
            val thirdOctet = i / 10
            val fourthOctet = i % 10
            "192.168.$thirdOctet.$fourthOctet"
        }

        val eventWeights = listOf(
            "LOGIN" to 30,
            "LOGOUT" to 30,
            "ACCESS" to 25,
            "UPDATE" to 10,
            "ERROR" to 5
        )
        val totalWeight = eventWeights.sumOf { it.second }
        val weightedEventList = eventWeights.flatMap { (event, weight) ->
            List(weight) { event }
        }

        val random = ThreadLocalRandom.current()

        repeat(NUMBER_OF_LINES) { i ->
            val ip = ipPool[random.nextInt(ipPool.size)]
            val event = weightedEventList[random.nextInt(totalWeight)]

            lines.add("$ip,$event\n")
        }
    }
}
