package Configs

import JSONValue
import JSONVisualizer
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.TreeItem

interface Plugin {

    fun pluginMain(window: JSONVisualizer)

    fun JSONVisualizer.getSelectedTreeItem() : TreeItem {
        return tree.selection.first()
    }

    fun JSONVisualizer.getSelectedJSONValue() : JSONValue{
        return tree.selection.first().data as JSONValue
    }

    fun JSONVisualizer.setSelectedJSONValue(newValue : JSONValue) {
        tree.selection.first().data = newValue
    }

    fun JSONVisualizer.getSelectedTreeITemText() : String{
        return tree.selection.first().text
    }

    fun JSONVisualizer.setSelectedTreeITemText(newText: String){
        tree.selection.first().text = newText
    }

    fun JSONVisualizer.setTreeItemImage(imagePath: String){
        tree.selection.first().image = Image(tree.selection.first().display,imagePath)
    }

    fun JSONVisualizer.setTreeItemImage(treeItem : TreeItem, imagePath: String){
        treeItem.image = Image(treeItem.display,imagePath)
    }

    fun JSONVisualizer.setTreeItemText(treeItem : TreeItem, newText: String){
        treeItem.text = newText
    }

    fun JSONVisualizer.setBackgroundColorSelectedItemTree(color: Color){
        tree.selection.first().background = color
    }

    fun JSONVisualizer.setBackgroundColorItemTree(treeItem: TreeItem, color: Color){
        treeItem.background = color
    }
}