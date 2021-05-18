import Configs.Action
import Configs.Plugin
import JSONValues.JSONArray
import JSONValues.JSONObject
import Visitors.SerializeVisitor
import Visitors.createTreeVisualizorVisitor
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.*
import org.eclipse.swt.widgets.*


class JSONVisualizer(private val rootJSONValue: JSONValue) {

    val shell: Shell = Shell(Display.getDefault())
    val tree: Tree
    val text: Label
    val searchBox: Text
    val configButtonsAdder : Composite

    @Inject
    lateinit var plugin: Plugin

    @InjectAdd
    private val buttonActions = mutableListOf<Action>()

    init {
        shell.setSize(600, 450)
        shell.text = "JSON Value Visualizer"
        shell.layout = GridLayout(2,true)

        val compose = Composite(shell,SWT.BORDER)
        compose.layout = GridLayout(1,false)
        compose.layoutData = GridData(SWT.LEFT, SWT.TOP, true, true)

        tree = Tree(compose, SWT.BORDER)
        tree.layoutData = GridData(SWT.LEFT, SWT.TOP, true, true)

        searchBox = Text(compose, SWT.BORDER)
        searchBox.layoutData = GridData(SWT.LEFT, SWT.TOP, true, false)

        text = Label(shell, SWT.NONE)
        text.layoutData = GridData(SWT.LEFT, SWT.TOP, true, true)

        configButtonsAdder = Composite(compose,SWT.BORDER)
        configButtonsAdder.layout = GridLayout(1,false)
        configButtonsAdder.layoutData = GridData(SWT.LEFT, SWT.BOTTOM, true, true)

        createTreeItem()
        addListeners()
    }

    private fun createTreeItem(){
        val visitor = createTreeVisualizorVisitor(tree)
        rootJSONValue.accept(visitor)
    }

    fun open() {
        tree.expandAll()

        if (::plugin.isInitialized) {
            println("is initialized")
            plugin.pluginMain(this)
        }

        val window = this
        buttonActions.forEach { action ->

            val button = Button(configButtonsAdder,SWT.PUSH)
            button.text = action.name

            button.addSelectionListener(object : SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    if (tree.selection.isNotEmpty()) //only when there's something selected
                        action.execute(window)
                }
            })
        }
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()

    }

    fun rePaintTree(){
        if (::plugin.isInitialized) {
            println("is initialized")
            plugin.pluginMain(this)
        }
    }



    private fun highlightItem(objName:String, tree: Tree){

        tree.traverse {
            if (it.text.toLowerCase().contains(objName.toLowerCase()) && objName.isNotEmpty())
                it.background = Color(240, 240, 255)//,1)
            else
                it.background = Color(255,255,255)
        }

    }


    private fun addListeners() {
        searchBox.addModifyListener {

            println(searchBox.text)
            println(searchBox.text.isEmpty())

            highlightItem(searchBox.text,tree)

        }

        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val serializeObject = tree.selection.first().data as JSONValue
                if(serializeObject is JSONObject || serializeObject is JSONArray ) {
                    text.text = removeExcessTabs(SerializeVisitor(serializeObject).objectMap, serializeObject)
                }
                else {
                    if (serializeObject.parent is JSONArray)
                        text.text = SerializeVisitor(serializeObject).objectMap
                    else
                        text.text = SerializeVisitor(serializeObject).objectMap.split(":")[1]
                }

                if(text.text.last() == ',')
                    text.text = text.text.dropLast(1)

                if (text.text.substringAfterLast(',') == "\n")
                    text.text = text.text.dropLast(2)
                text.requestLayout()
            }
        })
    }



}


private fun removeExcessTabs(Serialize:String,obj:JSONValue):String{
    var result = ""
    val splits = Serialize.split("\n")

    splits.forEach {

        result += if (splits.indexOf(it) != splits.size - 1)
            it.replaceRange(0, obj.depth, "") + "\n"
        else
            it
    }

    return result

}

// auxiliar para profundidade do nó
fun TreeItem.depth(): Int =
    if(parentItem == null) 0
    else 1 + parentItem.depth()

// auxiliares para varrer a árvore

fun Tree.expandAll() = traverse { it.expanded = true }

fun Tree.traverse(visitor: (TreeItem) -> Unit) {
    fun TreeItem.traverse() {
        visitor(this)
        items.forEach {
            it.traverse()
        }
    }
    items.forEach { it.traverse() }
}

fun Tree.createTextToFile() : String{
    var textToAdd = ""
    val serializeObject = this.selection.first().data as JSONValue

    textToAdd = if(serializeObject is JSONObject || serializeObject is JSONArray) {
        removeExcessTabs(SerializeVisitor(serializeObject).objectMap, serializeObject)
    }
    else {
        if (serializeObject.parent is JSONArray)
            SerializeVisitor(serializeObject).objectMap
        else
            SerializeVisitor(serializeObject).objectMap.split(":")[1]
    }

    if(textToAdd.last() == ',')
        textToAdd = textToAdd.dropLast(1)

    if (textToAdd.substringAfterLast(',') == "\n")
        textToAdd = textToAdd.dropLast(2)

    return textToAdd
}


