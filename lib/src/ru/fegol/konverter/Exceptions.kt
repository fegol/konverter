package ru.fegol.konverter

/**
 * This exception catching when converter graph already have register function for this types
 */
class ConvertConflictException(from: String, to: String) :
    RuntimeException("Multiple converters for types: $from -> $to")