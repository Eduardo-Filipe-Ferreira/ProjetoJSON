import JSONValues.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

@Suppress("UNCHECKED_CAST")
class JSONGenerator {


    fun getJSONValue(value:Any?): JSONValue{

        when (value){
            is Number -> return JSONNumber(value)
            is String -> return JSONString(value)
            is Char -> return JSONString(value as String)
            is Boolean -> return JSONBoolean(value)
            is Collection<*> -> {
                val valueArray = JSONArray()
                value.forEach {
                    valueArray.addProperty(getJSONValue(it))
                }
                return valueArray
            }
            is Map<*,*> -> {
                val valueObject = JSONObject()
                value.forEach {(key,_value)->
                    valueObject.addProperty(key.toString(),getJSONValue(_value))
                }
                return valueObject
            }
            is Enum<*> -> return JSONString(value.name)
            else -> { //data class or null
                if(value != null) {
                    val valueObject = JSONObject()

                    val valueClass: KClass<Any> = value::class as KClass<Any>

                    if (valueClass.isData) {

                        valueClass.declaredMemberProperties.forEach {
                            if (!it.hasAnnotation<Skip>()) {
                                if (it.hasAnnotation<ID>())
                                    valueObject.addProperty(
                                        it.findAnnotation<ID>()!!.id,
                                        getJSONValue(it.call(value)!!)
                                    )
                                else
                                    valueObject.addProperty(
                                        it.name,
                                        getJSONValue(it.call(value)!!)
                                    )
                            }
                        }

                        return valueObject
                    }
                }
            }
        }

        return JSONString("null")
    }

}