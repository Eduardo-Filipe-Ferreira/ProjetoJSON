package JSONValues

import JSONValue

abstract class JSONComposedValue(
    var children: MutableMap<String, JSONValue>? = null) : JSONValue(children) {

//    var children : MutableList<JSONValue> = mutableListOf()

}