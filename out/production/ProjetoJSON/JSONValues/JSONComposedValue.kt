
class JSONComposedValue:JSONValue{

    //removes ambiguidy between JSONArray and JSONObject

    //To create a JSONObject
    constructor(value: MutableMap<String,JSONValue>,parent:JSONComposedValue? = null): super(value,parent)

    //To create a JSONArray
    constructor(value: MutableList<JSONValue>,parent:JSONComposedValue? = null): super(value,parent)


}