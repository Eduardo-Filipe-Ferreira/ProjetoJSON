package Visitors

import JSONValue
import JSONValues.*

class GetterVisitor(_object: JSONObject,
                    val accept: (JSONValue) -> Boolean): JSONVisitor {

    var results:MutableList<JSONValue> = mutableListOf()

    init {
        _object.accept(this)
    }

    override fun visit(_object: JSONObject): Boolean{
        if (accept(_object))
            results.add(_object)

        if(_object.children!!.isEmpty())
            return false
        return true
    }

    override fun visit(array: JSONArray): Boolean {
        if (accept(array))
            results.add(array)

        if(array.children!!.isEmpty())
            return false
        return true
    }

    override fun visit(number: JSONNumber) {
        if (accept(number))
            results.add(number)
    }

    override fun visit(boolean: JSONBoolean) {
        if (accept(boolean))
            results.add(boolean)
    }

    override fun visit(string: JSONString) {
        if (accept(string))
            results.add(string)
    }

    override fun endVisit(_object: JSONObject) {}

    override fun endVisit(array: JSONArray) {}

}