package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONArray(val children: MutableList<JSONValue> = mutableListOf()) : JSONValue(children) {


    init {
        children.forEach{
            addProperty(it)
        }
    }


    override fun accept(visitor: JSONVisitor) {

        if(visitor.visit(this))
            children.forEach{
                it.accept(visitor)
            }
        visitor.endVisit(this)
    }

    fun isLastJSONValue(value: JSONValue): Boolean{
        if(children.indexOf(value) == children.size - 1)
            return true
        return false
    }

    fun addProperty(value:JSONValue){
        children.add(value)
        value.parent = this
    }

    fun removeProperty(value:JSONValue){
        children.remove(value)
    }
}