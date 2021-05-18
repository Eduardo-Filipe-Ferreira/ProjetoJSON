package Visitors

import JSONValues.*
import org.eclipse.swt.widgets.*
import org.eclipse.swt.SWT

class createTreeVisualizorVisitor(private var treeRoot : Tree): JSONVisitor {

    var treeItemRoot : TreeItem? = null

    override fun visit(number: JSONNumber) {

        val dirRoot : TreeItem = getTreeItem()

        dirRoot.text = if (number.parent is JSONObject)
            "${(number.parent as JSONObject).getKey(number)} : ${number.value}"
        else
            "${number.value}"
        dirRoot.data = number
    }

    override fun visit(boolean: JSONBoolean) {

        val dirRoot : TreeItem = getTreeItem()

        dirRoot.text = if (boolean.parent is JSONObject)
            "${(boolean.parent as JSONObject).getKey(boolean)} : ${boolean.value}"
        else
            "${boolean.value}"
        dirRoot.data = boolean
        //println(treeItemRoot!!.text)
    }

    override fun visit(string: JSONString) {

        val dirRoot : TreeItem = getTreeItem()

        dirRoot.text = if (string.parent is JSONObject)
            "${(string.parent as JSONObject).getKey(string)} : \"${string.value}\""
        else
            "\"${string.value}\""

        dirRoot.data = string
    }

    override fun visit(_null: JSONNull) {

        val dirRoot : TreeItem = getTreeItem()

        dirRoot.text = if (_null.parent is JSONObject)
            "${(_null.parent as JSONObject).getKey(_null)} : null"
        else
            "null"
        dirRoot.data = _null
        //println(treeItemRoot!!.text)
    }

    override fun visit(_object: JSONObject): Boolean {

        val dirRoot : TreeItem = getTreeItem()

        if (_object.parent is JSONObject)
            dirRoot.text = (_object.parent as JSONObject).getKey(_object)
        if(_object.parent == null){
            dirRoot.text = "ValueRoot"
        }

        dirRoot.data = _object

        treeItemRoot = dirRoot
        //println("Definido ${treeItemRoot!!.text} como Root \n")

        if(_object.children.isEmpty())
            return false
        return true
    }

    override fun endVisit(_object: JSONObject) {
        treeItemRoot =
            if(treeItemRoot!!.parentItem != null) {
                //println("Saiu ${treeItemRoot!!.text} de Root e entrou o Pai -> ${treeItemRoot!!.parentItem.text} \n")
                treeItemRoot!!.parentItem
            }else {
                //println("Saiu ${treeItemRoot!!.text} de Root e ja nao tinha pai \n")
                null
            }
        //println(treeItemRoot == null)
    }

    override fun visit(array: JSONArray): Boolean {
        val dirRoot = getTreeItem()

        if (array.parent is JSONObject)
            dirRoot.text = (array.parent as JSONObject).getKey(array)

        dirRoot.data = array

        treeItemRoot = dirRoot

        //println("Definido ${treeItemRoot!!.text} como Root\n")

        if(array.children.isEmpty())
            return false
        return true
    }

    override fun endVisit(array: JSONArray) {

        treeItemRoot =
            if(treeItemRoot!!.parentItem != null) {
                //println("Saiu ${treeItemRoot!!.text} de Root e entrou o Pai -> ${treeItemRoot!!.parentItem.text} \n")
                treeItemRoot!!.parentItem
            }
            else {
                //println("Saiu ${treeItemRoot!!.text} de Root e ja nao tinha pai \n")
                null
            }

        //println(treeItemRoot == null)
    }

    private fun getTreeItem():TreeItem{
        val dirRoot : TreeItem

        if (treeItemRoot == null){

            dirRoot = TreeItem(treeRoot, SWT.NONE)
            treeItemRoot = dirRoot

        }
        else
            dirRoot = TreeItem(treeItemRoot, SWT.NONE)


        return dirRoot
    }

}