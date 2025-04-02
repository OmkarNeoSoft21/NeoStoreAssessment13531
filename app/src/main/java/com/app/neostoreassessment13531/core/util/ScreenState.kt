package com.app.neostoreassessment13531.core.util


data class UiState<T>(
    val screenState : ScreenState = ScreenState.None,
    val error : UiText? = null,
    val title : String = "",
    val state : T ?= null
)

sealed interface ScreenState {
    data object Paginating : ScreenState
    data object Loading : ScreenState
    data object Refreshing : ScreenState
    data object Searching : ScreenState
    data object None : ScreenState
}
