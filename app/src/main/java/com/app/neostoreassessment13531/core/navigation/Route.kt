package com.app.neostoreassessment13531.core.navigation
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object RegisterUser : Route

    @Serializable
    data object UserList : Route

    @Serializable
    data class UserDetails(val userId:String) : Route

}