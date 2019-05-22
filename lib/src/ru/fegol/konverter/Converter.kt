package ru.fegol.konverter

import kotlin.reflect.KClass

object Converter {
    fun <T : Any, R : Any> T.convert(tclazz: String, rclazz: String): R {
        return ConverterContext.defaultContext.convert(this, tclazz, rclazz)
    }

    fun <T : Any, R : Any> T.convert(name: String, tclazz: String, rclazz: String): R {
        return ConverterContext.defaultContext.convert(this, tclazz, rclazz, name)
    }

    fun <T : Any, R : Any> Iterable<T>.convertAll(tclazz: String, rclazz: String): List<R> {
        return ConverterContext.defaultContext.convertAll(this, tclazz, rclazz)
    }

    inline fun <reified T : Any, reified R : Any> T.convert(): R {
        return convert(T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    inline fun <reified T : Any, R : Any> T.convert(clazz: KClass<R>): R {
        return convert(T::class.qualifiedName!!, clazz.qualifiedName!!)
    }

    inline fun <reified T : Any, reified R : Any> T.convert(name: String): R {
        return convert(name, T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    inline fun <reified T : Any, reified R : Any> Iterable<T>.convertAll(): List<R> {
        return convertAll(T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    inline fun <reified T : Any, R : Any> Iterable<T>.convertAll(clazz: KClass<R>): List<R> {
        return convertAll(T::class.qualifiedName!!, clazz.qualifiedName!!)
    }
}

fun defaultConverter(block: Converter.() -> Unit) {
    Converter.apply(block)
}