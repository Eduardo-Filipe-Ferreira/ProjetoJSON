package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONNumber(value: Number) : JSONValue(value) {


    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }

}