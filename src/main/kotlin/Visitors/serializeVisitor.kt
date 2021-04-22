package Visitors

import JSONValue
import JSONValues.*

class SerializeVisitor(_object : JSONValue):JSONVisitor {

    var objectMap = ""

    init {
        _object.accept(this)
    }


    override fun visit(value: JSONObject): Boolean {

        objectMap += value.getTabs() +

                if (value.parent != null) {
                    if (value.parent is JSONObject) {
                        "\"" + (value.parent as JSONObject).getKey(value) + "\":{" +
                        if (value.children!!.isNotEmpty())
                            "\n"
                        else
                            ""
                    }
                    else
                        "{" //parent is JSONArray
                } else
                    "{\n"

        if (value.children!!.isEmpty())
            return false
        return true
    }


    override fun endVisit(_object: JSONObject){

        objectMap += (
                if(_object.children!!.isNotEmpty())
                    _object.getTabs()
                else
                    ""
                )+
                if (_object.parent != null)
                    if(_object.parent is JSONObject)
                        if (!(_object.parent as JSONObject).isLastJSONValue(_object))
                            "},\n"
                        else
                            "}\n"
                    else //parent is JSONArray
                        if(!(_object.parent as JSONArray).isLastJSONValue(_object))
                            "},"
                        else
                            "}"
                else
                    "}"

    }

    override fun visit(array: JSONArray): Boolean{

        objectMap += array.getTabs() +
                     getNameIfParentIsObject(array) + "["

        if (array.children!!.isEmpty())
            return false
        return true

    }

    override fun endVisit(array: JSONArray){
        objectMap += "]${addComaIfLast(array)}"
    }

    override fun visit(number: JSONNumber) {
        objectMap += number.getTabs() +
                     getNameIfParentIsObject(number) +
                     number.value.toString()+
                     addComaIfLast(number)
    }

    override fun visit(boolean: JSONBoolean) {
        objectMap += boolean.getTabs() +
                     getNameIfParentIsObject(boolean) +
                     boolean.value.toString()+
                     addComaIfLast(boolean)
    }

    override fun visit(string: JSONString) {
        objectMap += string.getTabs() +
                     getNameIfParentIsObject(string) +
                     "\"${string.value}\"${addComaIfLast(string)}"
    }


    private fun addComaIfLast(value:JSONValue): String{

        var adder = ""

        if (value.parent is JSONObject)
            adder += if (!(value.parent as JSONObject).isLastJSONValue(value))
                ",\n"
            else
                "\n"

        if(value.parent is JSONArray)
            if (!(value.parent as JSONArray).isLastJSONValue(value))
                adder += ","

        return adder
    }

    private fun checkIfIsInArray(value:JSONValue):Boolean{
        if (value.parent is JSONArray)
            return true
        else if(value.parent == null)
            return false
        return checkIfIsInArray(value.parent!!)
    }

    private fun getNameIfParentIsObject(value : JSONValue): String{
        if (value.parent != null)
            if(value.parent is JSONObject)
                return "\"" + (value.parent as JSONObject).getKey(value) + "\":"
        return ""
    }



}