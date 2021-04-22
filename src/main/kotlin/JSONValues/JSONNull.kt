package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONNull: JSONValue(null) {
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}