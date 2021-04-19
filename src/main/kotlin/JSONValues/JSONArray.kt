package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONArray(children: MutableList<JSONValue>? = mutableListOf(),
                parent: JSONComposedValue? = null) : JSONComposedValue(children, parent) {

//    var children: MutableList<JSONValue> = mutableListOf()
//
//    init {
//        if (children != null) {
//            this.children = children
//        }
//    }


    override fun accept(visitor: JSONVisitor) {

        if(visitor.visit(this))
            children!!.forEach{
                it.accept(visitor)
            }
        visitor.endVisit(this)
    }

    fun isLastJSONValue(value: JSONValue): Boolean{
        if(children.indexOf(value) == children.size - 1)
            return true
        return false
    }
}