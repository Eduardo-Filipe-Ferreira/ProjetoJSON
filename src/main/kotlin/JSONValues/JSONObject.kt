package JSONValues

import JSONValue
import Visitors.JSONVisitor

/*
*
* Equivalent to a File Directory
*
* */

class JSONObject(children: MutableMap<String, JSONValue>? = mutableMapOf(),
                 parent: JSONComposedValue? = null) : JSONComposedValue(children, parent) {

    init {
        if (children != null) {
            this.children = children
        }
    }


    override fun accept(visitor: JSONVisitor) {

        if(visitor.visit(this))
            if(children!!.isNotEmpty())
                children!!.forEach{ (key, value) ->
                    value.accept(visitor)
                }
        visitor.endVisit(this)
    }


    fun addProperty(name:String,value:JSONValue){
        value.parent = this
        children!![name] = value
    }

    fun getKey(target: JSONValue): String {
        return children!!.filter { target == it.value }.keys.first()
    }

    fun isLastJSONValue(value: JSONValue): Boolean{
        if(children!!.keys.indexOf(getKey(value)) == children!!.keys.size - 1)
            return true
        return false
    }
    
    
}