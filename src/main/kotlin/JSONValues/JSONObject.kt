package JSONValues

import JSONValue
import Visitors.JSONVisitor

/*
*
* Equivalent to a File Directory
*
* */

class JSONObject(val children: MutableMap<String, JSONValue> = mutableMapOf()) : JSONValue(children) {

    init {
        children.forEach{(key,value) ->
            addProperty(key,value)
        }
    }

    override fun accept(visitor: JSONVisitor) {

        if(visitor.visit(this))
            if(children.isNotEmpty())
                children.forEach{ (_, value) ->
                    value.accept(visitor)
                }
        visitor.endVisit(this)
    }

    fun addProperty(name:String,value:JSONValue){
        value.parent = this
        value.depth = this.depth + 1
        children[name] = value
    }

    fun getKey(target: JSONValue): String {
        return children.filter { target == it.value }.keys.first()
    }

    fun isLastJSONValue(value: JSONValue): Boolean{
        if(children.keys.indexOf(getKey(value)) == children.keys.size - 1)
            return true
        return false
    }
    
    
}