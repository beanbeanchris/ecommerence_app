package edu.gatech.seclass.ecommerence_app.util

// A sealed class named 'Resource' with a generic type 'T'. This class is used to represent
// the different states of data handling (like loading, success, or error) in a standardized way,
// particularly useful for network operations or database transactions.
// For UI state handling in MVVM architecture

sealed class Resource<T> (
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data:T): Resource<T>(data)
    class Error<T>(message: String): Resource<T>(message = message)
    class Loading<T>: Resource<T>()
    class Unspecified<T>: Resource<T>()
}
