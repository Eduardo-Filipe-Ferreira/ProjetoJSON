package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONBoolean(value: Boolean) : JSONValue(value) {


    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }

}