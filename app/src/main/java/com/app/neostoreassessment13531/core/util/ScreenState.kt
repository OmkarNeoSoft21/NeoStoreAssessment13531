package com.app.neostoreassessment13531.core.util


data class UiState(
    val screenState : ScreenState = ScreenState.None,
    val error : UiText? = null,
    val title : String = ""
)

sealed interface ScreenState {
    data object Paginating : ScreenState
    data object Loading : ScreenState
    data object Refreshing : ScreenState
    data object Searching : ScreenState
    data object None : ScreenState
}
