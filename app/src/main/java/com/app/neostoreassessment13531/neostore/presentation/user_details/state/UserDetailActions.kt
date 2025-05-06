package com.app.neostoreassessment13531.neostore.presentation.user_details.state

sealed interface UiUserDetailActions {
    data object OnBackPressed: UiUserDetailActions
}