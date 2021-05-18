package Configs

import JSONValue
import JSONValues.JSONArray
import JSONValues.JSONObject
import JSONVisualizer
import traverse

class ChangeText: Plugin() {
    val conditionToChangeText: (JSONValue) -> Boolean = {value -> value is JSONObject || value is JSONArray }

    override fun pluginMain(window: JSONVisualizer) {
        window.tree.traverse {
            if(conditionToChangeText(it.data as JSONValue)){

                if(it.data is JSONObject)
                    it.text += " - number of children: " + (it.data as JSONObject).children.size

                if(it.data is JSONArray)
                    it.text += " - number of children: " + (it.data as JSONArray).children.size

            }
        }
    }
}