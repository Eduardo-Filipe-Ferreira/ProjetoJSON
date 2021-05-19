package Configs

import JSONValue
import JSONValues.JSONArray
import JSONValues.JSONObject
import JSONVisualizer
import org.eclipse.swt.graphics.*
import traverse

class IconPlugin : Plugin {
    private val condition: (JSONValue) -> Boolean = {value:JSONValue -> value is JSONObject || value is JSONArray }

    override fun pluginMain(window: JSONVisualizer){
        window.tree.traverse {
            if(condition(it.data as JSONValue))
                it.image = Image(it.display,"src/main/kotlin/Images/folderIcon.png")
            else
                it.image = Image(it.display,"src/main/kotlin/Images/fileIcon.png")
        }

    }
}