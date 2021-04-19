package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONBoolean(value: Any,
                  parent: JSONComposedValue) : JSONValue(value,parent) {


    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }

}