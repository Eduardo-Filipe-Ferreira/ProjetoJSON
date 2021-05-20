package Configs

import JSONValue
import JSONValues.*
import JSONVisualizer
import Visitors.SerializeVisitor
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*

class Edit: Action{
    override val name: String
        get() = "Edit"

    override fun execute(window: JSONVisualizer) {
        val tree = window.tree

        val selectedItem = tree.selection.first()
        if( (selectedItem.data as JSONValue) !is JSONObject &&
            (selectedItem.data as JSONValue) !is JSONArray &&
            !checkIfIsInArray((selectedItem.data as JSONValue)))
            createEditShell(tree , window).open()
    }

}

fun createEditShell(tree: Tree , window: JSONVisualizer) : Shell {
    val selectedItem = tree.selection.first()
    val selectedJSONValue = (selectedItem.data as JSONValue)
    
    val editShell = Shell(Display.getDefault())

    editShell.setSize(250, 200)
    editShell.text = "Edit JSONValue"
    editShell.layout = GridLayout(4,false)

    val propertyName = Label(editShell, SWT.NONE)
    val editField = Text(editShell, SWT.BORDER)
    val confirmButton = Button(editShell, SWT.BORDER)
    confirmButton.text = "OK"

    propertyName.text = (selectedJSONValue.parent as JSONObject).getKey((selectedItem.data as JSONValue))+": "

    editShell.pack()

    confirmButton.addSelectionListener(object : SelectionAdapter() {
        override fun widgetSelected(e: SelectionEvent) {
            println("Click ok!")

            println(editField.text)
            if(editField.text != "")
            {
                if(selectedJSONValue is JSONNumber && editField.text.all { Character.isDigit(it)}){
                    selectedJSONValue.value = editField.text.toFloat()
                    updateSelectedItemText(selectedItem,editField.text)
                    tree.requestLayout()
                    editShell.close()
                }
                if(selectedJSONValue is JSONString){
                    selectedJSONValue.value = editField.text
                    updateSelectedItemText(selectedItem,"\"" + editField.text + "\"")
                    tree.requestLayout()
                    editShell.close()
                }
                if(selectedJSONValue is JSONBoolean && (editField.text == "true" || editField.text == "false")){
                    selectedJSONValue.value = editField.text.toBoolean()
                    updateSelectedItemText(selectedItem,editField.text)
                    tree.requestLayout()
                    editShell.close()
                }

                window.setLabelTextOnSelection()
            }
        }
    })

    return editShell
}

fun updateSelectedItemText(selectedItem : TreeItem , JSONNewValue:String){
    val currentTexts = selectedItem.text.split(":")
    selectedItem.text = currentTexts[0] + ": " + JSONNewValue
}

//copy paste from serialize visitor
private fun checkIfIsInArray(value:JSONValue):Boolean{
    if (value.parent is JSONArray)
        return true
    else if(value.parent == null)
        return false
    return checkIfIsInArray(value.parent!!)
}
