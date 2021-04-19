import JSONValues.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties

@Suppress("UNCHECKED_CAST")
class JSONGenerator {

    fun getJSONValue(value:Any , valueParent: JSONComposedValue? = null): JSONValue{

        val valueClass :KClass<Any> = value::class as KClass<Any>

        println("$valueClass\n")

        if (valueClass.isData) {

            val valueObject = JSONObject("", parent = valueParent)

            valueClass.declaredMemberProperties.forEach {
//                println("$it" + "\n")
                getJSONValue(value = it, valueParent = valueObject)
            }

            return valueObject

        //repartir funcao para ter so as classes em si

        }

        if (value is KProperty<*>) {
            println(value.returnType.classifier as KClass<*>)

            when(value.returnType.classifier as KClass<*>){
                Int::class -> {
                    return if (valueParent != null)
                        JSONNumber(3, valueParent)
                    else
                        JSONNumber(2, JSONObject("null"))
                }

                Double::class -> {
                    return if (valueParent != null)
                        JSONNumber(3, valueParent)
                    else
                        JSONNumber(2, JSONObject("null"))
                }

                Float::class -> {
                    return if (valueParent != null)
                        JSONNumber(3, valueParent)
                    else
                        JSONNumber(2, JSONObject("null"))
                }

                String::class -> {
                    return if (valueParent != null)
                        JSONString("Value", valueParent)
                    else
                        JSONString("Value", JSONObject("null"))
                }

                Char::class -> {
                    return if (valueParent != null)
                        JSONString("Value", valueParent)
                    else
                        JSONString("Value", JSONObject("null"))
                }

                Boolean::class -> {
                    return if (valueParent != null)
                        JSONBoolean(true, valueParent)
                    else
                        JSONBoolean(false, JSONObject("null"))

                }

                Collection::class ->{
                    val list: MutableList<JSONValue> = mutableListOf()

                    return if (valueParent != null)

                        JSONArray(list, valueParent)
                    else
                        JSONString("Value", JSONObject("null"))

                }

                Map::class -> {

                }

                Enum::class -> {

                }



            }
        }
        return JSONObject("null")
    }

}