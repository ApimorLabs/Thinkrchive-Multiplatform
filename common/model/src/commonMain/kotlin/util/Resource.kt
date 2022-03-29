package util

// T is for data to be held and V is for error codes (it can be an enum or class)
sealed class Resource<T, V>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: V? = null
) {
    class Success<T, V>(data: T) : Resource<T, V>(data = data)

    class Error<T, V>(
        message: String,
        errorCode: V? = null
    ) : Resource<T, V>(message = message, errorCode = errorCode)

    class Loading<T, V>(data: T? = null) : Resource<T, V>(data)
}