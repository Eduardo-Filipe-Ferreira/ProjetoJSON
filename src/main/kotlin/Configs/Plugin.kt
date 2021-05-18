package Configs

import JSONVisualizer

abstract class Plugin {
    abstract fun pluginMain(window: JSONVisualizer)
}