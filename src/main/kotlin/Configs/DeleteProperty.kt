package Configs

import JSONValue
import JSONValues.JSONArray
import JSONValues.JSONObject
import JSONVisualizer

class DeleteProperty : Action{
    override val name: String
        get() = "Delete Property"

    override fun execute(window: JSONVisualizer) {
        val tree = window.tree
        val treeItemSelected = tree.selection.first()
        val valueSelected = treeItemSelected.data as JSONValue

        if(valueSelected.parent != null)
            if(valueSelected.parent is JSONObject)
                (valueSelected.parent as JSONObject).removeProperty(valueSelected)
            else
                (valueSelected.parent as JSONArray).removeProperty(valueSelected)

        treeItemSelected.dispose()
        window.setLabelTextOnSelection()
    }
}