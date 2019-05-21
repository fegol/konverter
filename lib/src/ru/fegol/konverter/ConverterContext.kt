package ru.fegol.konverter

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ConverterContext(converterContextBuilder: ConverterContextBuilder) {

    internal val maps = converterContextBuilder.maps

    fun <T : Any, R : Any> convert(
        from: T, tclazz: String,
        rclazz: String
    ): R {
        val mapper = maps[tclazz + rclazz]
            ?: throw IllegalArgumentException("Cannot convert $tclazz to $rclazz")
        return mapper.invoke(this, from) as R
    }

    fun <T : Any, R : Any> convertAll(
        from: Iterable<T>,
        tclazz: String,
        rclazz: String
    ): List<R> {
        val mapper = maps[tclazz + rclazz]
            ?: throw IllegalArgumentException("Cannot convert $tclazz to $rclazz")
        return from.map { mapper.invoke(this, it) as R }
    }

    @ConverterMarker
    inline fun <reified T : Any, reified R : Any> T.convert(): R {
        return convert(this, T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    @ConverterMarker
    inline fun <reified T : Any, R : Any> T.convert(clazz: KClass<R>): R {
        return convert(this, T::class.qualifiedName!!, clazz.qualifiedName!!)
    }

    @ConverterMarker
    inline fun <reified T : Any, reified R : Any> Iterable<T>.convertAll(): List<R> {
        return convertAll(this, T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    companion object {
        var defaultContext: ConverterContext = converterContext { }
    }
}

fun converterContext(block: ConverterContextBuilder.() -> Unit): ConverterContext {
    val builder = ConverterContextBuilder().apply(block)
    return ConverterContext(builder)
}