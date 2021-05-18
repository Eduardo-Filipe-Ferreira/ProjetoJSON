package Configs

import JSONValues.JSONArray
import JSONVisualizer
import org.eclipse.swt.widgets.TreeItem
import traverse

class ExcludeArrays: Plugin() {
    override fun pluginMain(window: JSONVisualizer) {
        val itemTreesToDispose: MutableList<TreeItem> = mutableListOf()

        window.tree.traverse {
            if(it.data is JSONArray)
                itemTreesToDispose.add(it) //can't delete here because of the visitor in traverse
        }

        for (treeItem in itemTreesToDispose){
            treeItem.dispose()
        }
    }
}