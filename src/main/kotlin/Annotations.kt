
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY
//        , AnnotationTarget.VALUE_PARAMETER
//        , AnnotationTarget.EXPRESSION
)
annotation class Skip

@Target(AnnotationTarget.PROPERTY)
annotation class ID(val id:String)