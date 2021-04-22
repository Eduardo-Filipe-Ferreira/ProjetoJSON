package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONString(value: String) : JSONValue(value) {

    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}