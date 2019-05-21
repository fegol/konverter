package ru.fegol.konverter

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ConverterContextBuilder {

    internal val maps: MutableMap<String, ConverterContext.(Any) -> Any> = mutableMapOf()

    @ConverterMarker
    inline fun <reified T : Any, reified R : Any> register(noinline block: ConverterContext.(T) -> R) {
        register(block, T::class, R::class)
    }

    @ConverterMarker
    fun includeDefaultContext() {
        maps.putAll(ConverterContext.defaultContext.maps)
    }

    @ConverterMarker
    fun include(context: ConverterContext) {
        maps.putAll(context.maps)
    }

    fun <T : Any, R : Any> register(block: ConverterContext.(T) -> R, tclazz: KClass<T>, rclazz: KClass<R>) {
        maps[tclazz.qualifiedName + rclazz.qualifiedName] = {
            block.invoke(this, it as T)
        }
    }
}