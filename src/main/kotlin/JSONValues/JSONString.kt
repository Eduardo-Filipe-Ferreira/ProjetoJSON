package JSONValues

import JSONValue
import Visitors.JSONVisitor

class JSONString : JSONValue {

    constructor(value: String,parent: JSONArray) : super(value,parent)

    constructor(value: String,parent: JSONObject? = null): super(value,parent)

    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}