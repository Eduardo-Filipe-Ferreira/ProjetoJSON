package Configs

import JSONValue
import JSONValues.*
import JSONVisualizer
import org.eclipse.swt.SWT.*
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import javax.swing.JOptionPane

class AddProperty : Action{
    override val name: String
        get() = "Add Property"

    override fun execute(window: JSONVisualizer) {
        val tree = window.tree
        if( tree.selection.first().data is JSONObject ||
            tree.selection.first().data is JSONArray)

                createAddPropertyWindow(window).open()
    }

    private fun createAddPropertyWindow(window: JSONVisualizer) : Shell{
        val tree = window.tree

        val selectedItem = tree.selection.first()

        val addPropShell = Shell(Display.getDefault())

        addPropShell.setSize(350, 200)
        addPropShell.text = "Add Property to ${selectedItem.text}"
        addPropShell.layout = GridLayout(1,false)

        val valueType = Combo(addPropShell, READ_ONLY)
        valueType.add("Object")
        valueType.add("Array")
        valueType.add("String")
        valueType.add("Number")
        valueType.add("Boolean")
        valueType.add("Null")



        val propertyNamePromp   = Label(addPropShell, NONE)
        propertyNamePromp.text = "Property Name:"

        val propertyName        = Text(addPropShell, BORDER)
        if (selectedItem.data is JSONArray)
            propertyName.isEnabled = false

        val valuePromp          = Label(addPropShell, NONE)
        valuePromp.text = "Property Value:"

        val propertyValue       = Text(addPropShell, BORDER)
        checkValueTypeSelected(valueType,propertyValue)

        val confirmButton = Button(addPropShell, BORDER)
        confirmButton.text = "OK"


        confirmButton.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {

                createAndAddProperty(   selectedItem,
                                        valueType.text,
                                        propertyName,
                                        propertyValue,
                                        (selectedItem.data as JSONValue),window)
                addPropShell.close()
            }
        })
        return addPropShell
    }

    private fun checkValueTypeSelected(valueType: Combo, propertyValue:Text){

        valueType.addModifyListener {
            propertyValue.isEnabled = !(valueType.text == "Object" || valueType.text == "Array")
        }
    }

    private fun createAndAddProperty(   treeItemParent:TreeItem,
                                        valueType: String,
                                        propertyName: Text,
                                        propertyValue: Text,
                                        parentValue: JSONValue,
                                        window: JSONVisualizer){

        val value = if (valueType == "Object"){
            JSONObject()
        }
        else if (valueType == "Array"){
            JSONArray()
        }
        else if (valueType == "String"){
            JSONString(propertyValue.text)
        }
        else if (valueType == "Number" && propertyValue.text.all { Character.isDigit(it)}){
            JSONNumber(propertyValue.text.toFloat())
        }
        else if (valueType == "Boolean" && (propertyValue.text == "true" || propertyValue.text == "false")){
            JSONBoolean(propertyValue.text == "true")
        }
        else{ //Null
            JSONNull()
        }

        if(valueType == "Boolean" && !(propertyValue.text == "true" || propertyValue.text == "false")){
            JOptionPane.showMessageDialog(null,"Bad input on creating a boolean so a null value was created instead")
        }
        if(valueType == "Number" && !propertyValue.text.all { Character.isDigit(it)}){
            JOptionPane.showMessageDialog(null,"Bad input on creating a number so a null value was created instead")
        }

        val textToUse = when(value){
            is JSONObject -> propertyName.text

            is JSONArray -> propertyName.text

            is JSONString -> if(parentValue is JSONArray){
                "\"${propertyValue.text}\""
            }
            else{
                "${propertyName.text} : \"${propertyValue.text}\""
            }

            is JSONNumber -> if(parentValue is JSONArray){
                "\"${propertyValue.text}\""
            }
            else{
                "${propertyName.text} : \"${propertyValue.text}\""
            }

            is JSONBoolean -> if(parentValue is JSONArray){
                propertyValue.text
            }
            else{
                "${propertyName.text} : \"${propertyValue.text}\""
            }

            is JSONNull -> if(parentValue is JSONArray){
                "null"
            }
            else{
                "${propertyName.text} : null"
            }

            else -> ""
        }

        if(parentValue is JSONObject){
            parentValue.addProperty(propertyName.text,value)
        }
        if(parentValue is JSONArray){
            parentValue.addProperty(value)
        }

        val newTreeItem = TreeItem(treeItemParent, NONE)
        newTreeItem.data = value
        newTreeItem.text = textToUse

        window.rePaintWindow()
        
    }



}