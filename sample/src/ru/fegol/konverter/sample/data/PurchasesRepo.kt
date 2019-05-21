package ru.fegol.konverter.sample.data

import ru.fegol.konverter.sample.data.dto.PersonDto
import ru.fegol.konverter.sample.data.dto.PurchaseDto

object PurchasesRepo {

    fun getPurchases(personId: Int): List<PurchaseDto> {
        return listOf(
            PurchaseDto(0, "test", 10, 0),
            PurchaseDto(1, "test1", 22, 0)
        ).filter { it.personId == personId }
    }

    fun getPersons(): List<PersonDto> = listOf(PersonDto(0, "Some person", 25))
}