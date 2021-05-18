package Configs

import JSONValue
import JSONValues.*
import JSONVisualizer
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import traverse
import javax.swing.JOptionPane

class Check: Action {
    override val name: String
        get() = "Check"

    override fun execute(window: JSONVisualizer) {
        val tree = window.tree
        createValidationShell(tree).open()
    }

    private fun createValidationShell(tree: Tree) : Shell{

        val validationShell = Shell(Display.getDefault())

        validationShell.setSize(350, 100)
        validationShell.text = "Search for a JSON Value"
        validationShell.layout = GridLayout(3,false)

        val searchSpan = Combo(validationShell, SWT.READ_ONLY)
        searchSpan.add("At least one")
        searchSpan.add("All")

        val valueType = Combo(validationShell, SWT.READ_ONLY)
        valueType.add("Object")
        valueType.add("Array")
        valueType.add("String")
        valueType.add("Number")
        valueType.add("Boolean")
        valueType.add("Null")


        val withOrWithout = Combo(validationShell, SWT.READ_ONLY)
        withOrWithout.add("With")
        withOrWithout.add("Without")

        val propertyLabel = Label(validationShell, SWT.NONE)
        propertyLabel.text = "Property Name/Value:"

        val propertyValueToSearch = Text(validationShell, SWT.BORDER)

        val confirmButton = Button(validationShell, SWT.BORDER)
        confirmButton.text = "Search"


        confirmButton.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {

                if( searchSpan.text.isNotEmpty()    &&
                    valueType.text.isNotEmpty()     &&
                    withOrWithout.text.isNotEmpty() &&
                    propertyValueToSearch.text.isNotEmpty()){

                    val with = withOrWithout.text == "With"
                    println(with)
                    var results = searchThroughTree(tree,
                                                    valueType.text,
                                                    with,
                                                    propertyValueToSearch.text)

                    println(results)
                    if (results.isEmpty()){
                        JOptionPane.showMessageDialog(null,"There's no ${valueType.text.toLowerCase()} ${withOrWithout.text.toLowerCase()} the a property with the name or value \"${propertyValueToSearch.text}\"")
                        return
                    }

                    if(searchSpan.text == "At least one")
                        results = mutableListOf(results[0])


                    createResultsTree(validationShell,results)
                    validationShell.pack()
                }

            }
        })

        return validationShell
    }

    fun searchThroughTree(  tree:Tree,
                            valueType:String,
                            withOrWithout : Boolean,
                            propertyName: String): MutableList<TreeItem>{

        val results : MutableList<TreeItem> = mutableListOf()
        tree.traverse{ item ->

            var typeMatch = false
            var propertyMatch = false
            val value = item.data as JSONValue
            val itemsPropertyName = item.text

            if (    (valueType == "Object"  && value is JSONObject)     ||
                (valueType == "Array"   && value is JSONArray)      ||
                (valueType == "String"  && value is JSONString)     ||
                (valueType == "Number"  && value is JSONNumber)     ||
                (valueType == "Boolean" && value is JSONBoolean)    ||
                (valueType == "Null"    && value is JSONNull))
                typeMatch = true

            if(withOrWithout){//searching for with

                if (value is JSONObject){ //value is an object -> check children's name and value
                    value.children.forEach {(key,_val) ->
                        if(key == propertyName || _val.toString() == propertyName)
                            propertyMatch = true
                    }
                }else if (value is JSONArray){ //value is an array -> check children's value
                    value.children.forEach{
                        if(it.value.toString() == propertyName)
                            propertyMatch = true
                    }
                }else{ //isn't an array or an object so check only the value
                    if(value.value.toString() == propertyName)
                        propertyMatch = true
                }

            }
            else{//searching for without

                if (value is JSONObject){ //value is an object -> check children's name and value
                    propertyMatch = true //starts true until proven false
                    value.children.forEach {(key,_val) ->
                        if(key == propertyName || _val.toString() == propertyName)
                            propertyMatch = false
                    }
                }else if (value is JSONArray){ //value is an array -> check children's value
                    propertyMatch = true
                    value.children.forEach{
                        if(it.value.toString() == propertyName)
                            propertyMatch = false
                    }
                }else{ //isn't an array or an object so check only the value
                    propertyMatch = true
                    if(value.value.toString() == propertyName)
                        propertyMatch = false
                }

            }

            if( typeMatch && propertyMatch)
                results.add(item)

        }

        return results

    }

    private fun checkIfShellHasResultsTree(window: Shell): Boolean{
        var result = false

        for (item in window.children){
            if( item is Tree) {
                result = true
                break
            }
        }

        return result
    }

    private fun getTreeOfShell(window: Shell): Tree?{

        for (item in window.children){
            if( item is Tree) {
                item.removeAll()
                return item
            }
        }

        return null
    }

    fun createResultsTree(window: Shell,results: MutableList<TreeItem>){

        val resultsTree = if(checkIfShellHasResultsTree(window)){
            getTreeOfShell(window)
        }
        else{
            Tree(window, SWT.BORDER)
        }

        for (item in results){
            println(item)
            val resultTreeItem = TreeItem(resultsTree, SWT.NONE)
            resultTreeItem.data = item.data
            resultTreeItem.text = item.text
        }
    }

}