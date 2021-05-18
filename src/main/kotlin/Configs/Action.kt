package Configs

import JSONVisualizer

interface Action {
    val name: String
    fun execute(window: JSONVisualizer)
}



