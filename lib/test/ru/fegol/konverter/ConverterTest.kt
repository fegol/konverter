package ru.fegol.konverter

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class ConverterTest : StringSpec() {

    init {
        "simple convert" {
            converterContext {
                register<String, String> { "some $it" }
            }.run {
                val convertedValue: String = "string".convert()
                convertedValue shouldBe "some string"
            }
        }
        "named converters" {
            converterContext {
                register<String, String> { "some $it" }
                register<String, String>("another") { "another $it" }
            }.run {
                val convertedValue: String = "string".convert()
                val anotherConvertedValue: String = "string".convert("another")
                convertedValue shouldBe "some string"
                anotherConvertedValue shouldBe "another string"
            }
        }
        "included context" {
            val baseContext = converterContext {
                register<String, String> { "base $it" }
            }
            converterContext {
                include(baseContext)
                register<Int, Int> { it + 1 }
            }.run {
                "string".convert<String, String>() shouldBe "base string"
                10.convert<Int, Int>() shouldBe 11
            }
        }
        "included context conflict" {
            val baseContext = converterContext {
                register<String, String> { "base $it" }
            }
            converterContext {
                include(baseContext)
                shouldThrow<ConvertConflictException> {
                    register<String, String> { "some $it" }
                }
            }
        }
    }
}