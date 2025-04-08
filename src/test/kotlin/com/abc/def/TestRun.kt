package com.abc.def

import org.example.MultithreadedApproach
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.Test

class TestRun {

    @Test
    fun checkImplementation() {
        val generatedData = generateData(20)
        val runner = MultithreadedApproach()
        runner.run(generatedData)
    }

    private fun generateData(numberOfLines: Int): List<String> {
        val ipPool = List(numberOfLines) { i ->
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

        val results = mutableListOf<String>()
        repeat(numberOfLines) { i ->
            val ip = ipPool[random.nextInt(ipPool.size)]
            val event = weightedEventList[random.nextInt(totalWeight)]

            results.add("$ip,$event\n")
        }
        return results
    }
}