# JSONVisualizer


## Modelos de dados

Para instanciar os dados suportados por ficheiros JSON são usados as seguintes classes de dados:

1. JSONObject
2. JSONArray
3. JSONNumber
4. JSONBoolean
5. JSONString
6. JSONNull

Sendo todos eles derivados duma classe abstrata comum : JSONValue 

Esta classe abstrata tem os seguintes atributos: 
1. depth - simboliza o nivel de profundidade do objeto em relação ao pai
2. parent - o JSONValue ao qual o JSONValue instanciado esta adjacente

Esta classe abstrata obriga a implementação do método `accept(visitor : JSONVisitor)` usado para aplicar o padrão de desenho chamado visitors.
Este padrão de desenho é responsável por permitir definir como percorrer todos os valores associados a um determinado JSONValue.
É possivel criar novos visitors utilizando a interface JSONVisitor


## Instanciação

### JSONObject

Um JSONObject pode ser instanciado de 2 formas:
1. Dada uma mutableList de pares de Strings e JSONValues, sendo que a string simboliza o nome da propriedade associada ao JSONValue passado
2. Sem nada, simbolizando um objeto vazio


### JSONArray

À semelhaça do JSONObject, um JSONArray pode ser instanciado de 2 formas:
1. Dada uma mutableList com JSONValues, sendo esses JSONValues os valores que estam dentro do array
2. Sem nada, simbolizando um array vazio


### Restantes classes

É passado o valor que o objeto deve de tomar como argumento

Exemplos: 
`JSONNumber(3)` ou `JSONNumber(0.3)`

`JSONBoolean(true)`

`JSONString("Hello World")`

`JSONNull()`


## Adição de propriedades em classes compostas

É possivel adicionar propriedades tanto a um objeto como a um array

No caso do objeto é usado `JSONObject.addProperty(name:String , value:JSONValue)` adicionando uma propriedade ao objeto com o nome dado em "name" e o valor dados em value

No caso do array é usado `JSONArray.addProperty(value:JSONValue)` adicionando a propriedade dada em value ao array


## Passagem para texto JSON

É possivel obter o texto em formato JSON de dado valor usando um atributo da classe SerializeVisitor chamado objectMap

Exemplo:
```
val visitor = SerializeVisitor(objeto)  //pode ser qualquer tipo de dado referido anteriormente
println(visitor.objectMap)              // mostra na consola o objeto dado em formato json
```


## Pesquisa por JSONValues

É possivel obter o JSONValue que assegure a expressão lambda dada como argumento à classe GetterVisitor

Exemplo:
```
val isString = {value: JSONValue -> value is JSONString }
val visitorGetter: GetterVisitor = GetterVisitor(object,isString)
prinln(visitorGetter.results) //imprime na consola todos os objetos associados a object que sejam JSONString
```

## Inferência de dados:

É possivel criar as estruturas de dados a partir dos seguintes dados:
1. Objetos de valor (data class)
2. Coleções (Collection)
3. Tabelas (Map)
4. Tipos primitivos
5. Strings
6. Enumerados

Para isto deve se usar o metodo getJSONValue da classe JSONGenerator

Exemplo:
```
val inteiro = 3
val jsonNumber = JSONGenerator().getJSONValue(inteiro) //devolve um JSONNumber com o valor 3
```
A inferência transpõem os dados da seguinte forma:
1. data class -> JSONObject
2. Coleções -> JSONArray
3. Tabelas -> JSONObject
4. Tipos primitivos -> JSONNumber/JSONString/JSONBoolean
5. Strings -> JSONString
6. Enumerados -> JSONString


## Anotações

Na criação de uma data classe é possivel ignorar um parametro na criação do JSONObject usando a anotação @Skip
Pode-se também alterar o nome da propriedade que será associada ao objecto usando @ID(novo ID)


## Janela de visualização 

A Janela de visualização é feita em conjunção com a biblioteca SWT e é composta pelos seguintes atributos:

1. uma Shell -> `JSONVisualizer.shell`
2. um Tree onde é disposto todos os componentes do objeto a visualizar -> `JSONVisualizer.tree`
3. uma Label onde é disposto o texto em JSON do item selecionado na tree -> `JSONVisualizer.text`
4. uma Text onde é possivel pesquisar pelos elementos da tree -> `JSONVisualizer.searchBox`
5. um Compose onde são adicionadas as ações de plugins -> `JSONVisualizer.configButtonsAdder`


### Instanciação da janela de visualição

Exemplo: `JSONVisualizer(Objeto_a_visualizar).open()`


## Plugin's

São permitidos 2 tipos de plugins:

1. Plugins de apresentação
2. Plugins de criações de ações

Para aplicar os plugins à janela é necessário usar o método `applyConfigs(visualizer : JSONVisualizer)` da classe ConfigReader
Exemplo:

```
val visualizer = JSONVisualizer(object)
ConfigReader.applyConfigs(visualizer)
visualizer.open()
```


### Plugins de apresentação

Estes plugins são instanciados usando a classe abstrata Plugin

Esta classe obriga à impletação do método pluginMain que recebe a window onde o plugin será aplicado


### Plugins de ações

Estes plugins são instanciados usando a interface Action
São criados butões que permitem executar a ação implementada e adicionados 

Classes que implementam esta interface necessitam de apresentar 2 componentes:
1. O nome da ação -> Será o nome usado no botão que permitirá correr a ação implementada
2. O método execute que recebe a window onde a ação vai ser efetuada e executa a ação 


### Introdução dos Plugins

Para introduzir os plugins na janela de visualização é necessário indicar os plugins no ficheiro de configuração "di.properties"

Apenas é suportado um plugin de apresentação

São suportados vários plugins de ações e estes devem de ser separados por ","

Exemplo:
```
JSONVisualizer.buttonActions=Configs.Edit,Configs.Write,Configs.Check,Configs.AddProperty,Configs.DeleteProperty
JSONVisualizer.plugin=Configs.IconPlugin
```
