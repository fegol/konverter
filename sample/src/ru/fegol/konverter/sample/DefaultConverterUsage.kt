package ru.fegol.konverter.sample

import ru.fegol.konverter.Converter
import ru.fegol.konverter.ConverterContext
import ru.fegol.konverter.converterContext

fun main() {
    ConverterContext.defaultContext = converterContext {
        register<String, String> { "|| $it ||" }
    }
    println(defaultConverterUsage())
}

fun defaultConverterUsage(): String = Converter.run {
    "some string".convert()
}