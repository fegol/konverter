package ru.fegol.konverter

@Suppress("UNCHECKED_CAST")
class ConverterContextBuilder {

    private val maps: MutableMap<String, ConverterContext.(Any) -> Any> = mutableMapOf()

    @ConverterMarker
    inline fun <reified T : Any, reified R : Any> register(noinline block: ConverterContext.(T) -> R) {
        register(block, T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    @ConverterMarker
    inline fun <reified T : Any, reified R : Any> register(name: String, noinline block: ConverterContext.(T) -> R) {
        register(name, block, T::class.qualifiedName!!, R::class.qualifiedName!!)
    }

    @ConverterMarker
    fun includeDefaultContext() {
        maps.putAll(ConverterContext.defaultContext.maps)
    }

    @ConverterMarker
    fun include(context: ConverterContext) {
        maps.putAll(context.maps)
    }

    fun <T : Any, R : Any> register(block: ConverterContext.(T) -> R, tclazz: String, rclazz: String) {
        if (maps[tclazz + rclazz] != null) {
            throw ConvertConflictException(tclazz, rclazz)
        }
        maps[tclazz + rclazz] = {
            block.invoke(this, it as T)
        }
    }

    fun <T : Any, R : Any> register(name: String, block: ConverterContext.(T) -> R, tclazz: String, rclazz: String) {
        if (maps[name + tclazz + rclazz] != null) {
            throw ConvertConflictException(tclazz, rclazz)
        }
        maps[name + tclazz + rclazz] = {
            block.invoke(this, it as T)
        }
    }

    internal fun build(): Map<String, ConverterContext.(Any) -> Any> {
        return maps
    }
}