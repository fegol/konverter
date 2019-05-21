package ru.fegol.konverter.sample.data.entities

data class Person(val name: String, val age: Int, val purchases: List<Purchase>)

data class Purchase(val name: String, val sum: Int)