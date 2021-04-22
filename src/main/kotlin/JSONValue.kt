import JSONValues.JSONArray
import JSONValues.JSONComposedValue
import JSONValues.JSONObject
import Visitors.JSONVisitor
import kotlin.properties.Delegates

abstract class JSONValue(var value: Any? = null
//                         , var parent: JSONComposedValue? = null
) {


    /*
    *
    * abstract function to map a specific JSON value
    * returns the toString of said value
    *
    *
    * Classes can vary between:
    * -String
    * -Number
    * -Object
    * -Array
    * -Boolean (True or False)
    * -Null
    *
    *
    * Equivalent to a File
    *
    *
    * */

//    abstract fun mapJSONValue(): String

    /*
    *
    * All JSON values have a depth
    * This depth represents the level with the object and it's original father
    * If an value has no parent, then it's the initial branch of the object or the object itself
    * All the Values below have one more level of depth than it's father
    *
    * */

    var depth :Int = 0

    var parent : JSONValue? = null

    /*
    *
    * abstract function that determines of a visitor (given as argument) should loop through this specific value
    *
    * */

    abstract fun accept(visitor: JSONVisitor)

    /*
    *
    * Function to get the number of tabs for each particular value based on it's depth
    * Returns a String with the number of tabs
    *
    * */

    fun getTabs(): String{
        var tabs = ""

        for (i in 0 until depth)
            tabs += "\t"
        return tabs
    }


}