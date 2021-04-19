package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONNumber(value: Number, parent: JSONComposedValue? = null) : JSONValue(value, parent) {


    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }

}