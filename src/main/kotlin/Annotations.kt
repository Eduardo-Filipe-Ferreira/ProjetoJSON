//first and second phase
@Target(AnnotationTarget.PROPERTY)
annotation class Skip

@Target(AnnotationTarget.PROPERTY)
annotation class ID(val id:String)

//third and fourth phase
@Target(AnnotationTarget.PROPERTY)
annotation class Inject

@Target(AnnotationTarget.PROPERTY)
annotation class InjectAdd