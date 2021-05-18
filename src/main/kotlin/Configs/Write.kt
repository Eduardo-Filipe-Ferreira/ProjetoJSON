package Configs

import JSONVisualizer
import createTextToFile
import org.eclipse.swt.widgets.Tree
import java.io.File
import javax.swing.JOptionPane

class Write: Action{
    override val name: String
        get() = "Write"

    override fun execute(window: JSONVisualizer) {
        val tree = window.tree

        val textToFile = tree.createTextToFile()

        val fileName = tree.selection.first().text + ".txt"

        File(fileName).writeBytes(textToFile.toByteArray())

        JOptionPane.showMessageDialog(null,"Wrote selected object's JSON on file $fileName successfully")

    }

}