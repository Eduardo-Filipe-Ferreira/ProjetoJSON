import Configs.ConfigReader
import JSONValues.*
import Visitors.GetterVisitor
import Visitors.SerializeVisitor

enum class EnumClass {
    ENUM1,ENUM2,ENUM3
}

fun main() {

    /*
    *
    * Init for test values
    *
    * */

    val obj1 = JSONObject()
    val obj2 = JSONObject()
    val obj3 = JSONObject()
    val obj4 = JSONObject()

    val str1 = JSONString("Str1")
    val str2 = JSONString("Str2")
    val str3 = JSONString("Str3")
    val str4 = JSONString("Str4")

    val bool1 = JSONBoolean(true)
    val bool2 = JSONBoolean(false)

    val num1 = JSONNumber(1)
    val num2 = JSONNumber(2)

    val arr1 = JSONArray()
    val arr2 = JSONArray()
    val arr3 = JSONArray()
    
    val null1 = JSONNull()


    obj1.addProperty("Object2",obj2)
    obj1.addProperty("Object3",obj3)
    obj3.addProperty("Object4",obj4)

    obj2.addProperty("String1",str1)
    obj2.addProperty("Boolean1",bool1)

    obj1.addProperty("Boolean2",bool2)

    arr1.addProperty(str2)
    arr1.addProperty(str3)
    arr1.addProperty(str4)
    arr1.addProperty(num1)

    obj1.addProperty("Array1",arr1)
    
    obj1.addProperty("Null1",null1)

    /*
    *
    * test for serialize visitors of the object created
    *
    * */

    val visitor = SerializeVisitor(obj1)

    println("Test: Serialize visitors of the object created")
    println(visitor.objectMap)

    val isString = {value: JSONValue -> value is JSONString }

    val visitorGetter: GetterVisitor = GetterVisitor(obj1,isString)

    /*
    *
    * test for getter visitors of the object created
    * searches for strings
    *
    * */

    println("Test: getter visitors of the object created -> search for strings")
    println(visitorGetter.results)



    /*      SECOND PhaSE        */
    println("")


    data class Point(
        @Skip
        val x:Int,
        @ID("Id Test")
        val y:Int
        )

    val string = "String"

    val number = 3

    val boolean = true

    val nullValue = null

    val list = setOf<Any>(3,"String",3,true,3,Point(3,2),4,1)

    val enum = EnumClass.ENUM1

    val map = mapOf(
//        @Skip
        "x" to list,

        "y" to 2,

        "z" to 3)


    val gen = JSONGenerator()

    val ser1 = SerializeVisitor(gen.getJSONValue(Point(1,2)))
    val ser2 = SerializeVisitor(gen.getJSONValue(string))
    val ser3 = SerializeVisitor(gen.getJSONValue(number))
    val ser4 = SerializeVisitor(gen.getJSONValue(boolean))
    val ser5 = SerializeVisitor(gen.getJSONValue(nullValue))
    val ser6 = SerializeVisitor(gen.getJSONValue(list))
    val ser7 = SerializeVisitor(gen.getJSONValue(enum))
    val ser8 = SerializeVisitor(gen.getJSONValue(map))

    println("Test: serialize of data class")
    println(ser1.objectMap)
    println("")

    println("Test: serialize of String")
    println(ser2.objectMap)
    println("")

    println("Test: serialize of Number")
    println(ser3.objectMap)
    println("")

    println("Test: serialize of Boolean")
    println(ser4.objectMap)
    println("")

    println("Test: serialize of null value")
    println(ser5.objectMap)
    println("")

    println("Test: serialize of List")
    println(ser6.objectMap)
    println("")

    println("Test: serialize of Enum")
    println(ser7.objectMap)
    println("")

    println("Test: serialize of Map")
    println(ser8.objectMap)
    println("")

    val visualizer = JSONVisualizer(obj1)

    ConfigReader.applyConfigs(visualizer)

    visualizer.open()
}