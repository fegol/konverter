package ru.fegol.konverter.sample

import ru.fegol.konverter.converterContext
import ru.fegol.konverter.sample.data.PurchasesRepo
import ru.fegol.konverter.sample.data.dto.PersonDto
import ru.fegol.konverter.sample.data.dto.PurchaseDto
import ru.fegol.konverter.sample.data.entities.Person
import ru.fegol.konverter.sample.data.entities.Purchase

fun main() {
    val repo = PurchasesRepo
    val context = converterContext {
        register<PurchaseDto, Purchase> { Purchase(it.name, it.sum) }
        register<PersonDto, Person> {
            Person(it.name, it.age, repo.getPurchases(it.id).convertAll())
        }
    }
    val person = context.run { repo.getPersons().first().convert(Person::class) }
    println(person)
}