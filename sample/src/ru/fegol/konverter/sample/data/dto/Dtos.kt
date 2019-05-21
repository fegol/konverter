package ru.fegol.konverter.sample.data.dto

data class PersonDto(val id: Int, val name: String, val age: Int)

data class PurchaseDto(val id: Int, val name: String, val sum: Int, val personId: Int)