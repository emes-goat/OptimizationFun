package org.example

class NaiveApproach {

    fun run(lines: List<String>): String {
        return lines.filter { it.contains("ERROR") }
            .map { it.split(",").first() }
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            .key
    }
}