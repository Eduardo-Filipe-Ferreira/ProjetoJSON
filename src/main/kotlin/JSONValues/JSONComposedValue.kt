package JSONValues

import JSONValue

abstract class JSONComposedValue(
    var children: MutableMap<String, JSONValue>? = null,
    parent: JSONComposedValue? = null) : JSONValue(children,parent) {

//    var children : MutableList<JSONValue> = mutableListOf()

}