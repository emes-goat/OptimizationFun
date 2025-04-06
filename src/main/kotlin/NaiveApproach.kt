package org.example

class NaiveApproach {

    fun run(lines: List<String>) {
        lines.filter { it.contains("ERROR") }
            .map { it.split(",").first() }
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            .key
    }
}