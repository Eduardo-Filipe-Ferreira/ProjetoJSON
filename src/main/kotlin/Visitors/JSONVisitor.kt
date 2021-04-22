package Visitors

import JSONValue
import JSONValues.*

interface JSONVisitor {

    /*
    *
    * Represents the way an object should be looped around
    * For this project, if the object is a leaf then should add it's toString to the output, else should enter it's values
    *
    * */

    fun visit(_object: JSONObject): Boolean = true

    fun endVisit(_object: JSONObject)

    fun visit(array: JSONArray): Boolean = true

    fun endVisit(array: JSONArray)

    fun visit(number: JSONNumber)

    fun visit(boolean: JSONBoolean)

    fun visit(string: JSONString)

    fun visit(_null: JSONNull)


}