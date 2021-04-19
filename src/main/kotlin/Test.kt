import JSONValues.*
import Visitors.GetterVisitor
import Visitors.SerializeVisitor

fun main() {
    val obj = JSONObject()

    val obj2 = JSONObject()

    val str1 = JSONString("Str1")
    val str2 = JSONString("Str2")
    val bool2 = JSONBoolean(false)

    val arr1 = JSONArray()

    val str3 = JSONString("Str3")
    val str4 = JSONString("Str4")

    val obj3 = JSONObject()

    val num2 = JSONNumber(2)
    val obj4 = JSONObject()

    val arr2 = JSONArray()

    val num1 = JSONNumber(2)

    val arr3 = JSONArray()

    val bool1 = JSONBoolean(true,arr3)

    val visitor = SerializeVisitor(obj)

//    println(visitor.objectMap)

    val isString = {value: JSONValue -> value is JSONString }

//    val hasThisName = {value: JSONValue -> value.name == "Str3" }

    val visitorGetter: GetterVisitor = GetterVisitor(obj,isString)

//    println(visitorGetter.results)

    data class Point(val x:Int,
                     val y:Int)

    val parser = JSONGenerator()

    println(parser.getJSONValue(Point(1,2)))

    val ser1 = SerializeVisitor(parser.getJSONValue(Point(1,2)))

    println(ser1.objectMap)


}