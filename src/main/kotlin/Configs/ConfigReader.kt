package Configs

import Inject
import InjectAdd
import JSONVisualizer
import java.io.File
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

class ConfigReader {
    companion object{
        private val map: MutableMap<String, List<KClass<*>>> =
            mutableMapOf()
        init  {
            readConfigs()
        }

        private fun readConfigs(){
            val scanner = Scanner(File("di.properties"))
            while(scanner.hasNextLine()){
                val line = scanner.nextLine()
                val parts = line.split("=")

                val configs: MutableList<KClass<*>> = mutableListOf()

                parts[1].split(',').forEach{
                    configs.add(Class.forName(it).kotlin)
                }

                map[parts[0]] = configs
                println(parts[0])
                println(map[parts[0]])

            }
            scanner.close()
        }


        fun applyConfigs(window: JSONVisualizer) {
            window::class.declaredMemberProperties.forEach {
                if(it.hasAnnotation<Inject>()) {

                    it.isAccessible = true

                    val key = "JSONVisualizer." + it.name

                    println(key)
                    println(map[key])
                    if (map.keys.contains(key)) {
                        val obj = map[key]!![0].createInstance()

                        (it as KMutableProperty<*>).setter.call(window, obj)
                    }
                }

                if(it.hasAnnotation<InjectAdd>()){
                    it.isAccessible = true

                    val key = "JSONVisualizer." + it.name


                    if (map.keys.contains(key)) {
                        map[key]!!.forEach{ el ->
                            val obj = el.createInstance()

                            val actions = it.getter.call(window) as MutableList<Any>

                            actions.add(obj)

                            println(it.getter.call(window) as MutableList<Any>)
                            println(it.name)
                        }
                    }


                }
            }
        }
    }

}