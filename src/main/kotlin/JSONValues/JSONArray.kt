package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONArray(var children: MutableList<JSONValue>? = mutableListOf()) : JSONValue(children) {


    init {
        if (children!!.isNotEmpty())
            children!!.forEach{
                addProperty(it)
            }
    }


    override fun accept(visitor: JSONVisitor) {

        if(visitor.visit(this))
            children!!.forEach{
                it.accept(visitor)
            }
        visitor.endVisit(this)
    }

    fun isLastJSONValue(value: JSONValue): Boolean{
        if(children!!.indexOf(value) == children!!.size - 1)
            return true
        return false
    }

    fun addProperty(value:JSONValue){
        children!!.add(value)
        value.parent = this
    }
}