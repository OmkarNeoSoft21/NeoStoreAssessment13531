package com.app.neostoreassessment13531.neostore.presentation.user_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.neostoreassessment13531.core.util.ScreenState
import com.app.neostoreassessment13531.neostore.presentation.user_list.state.StateUserList
import com.app.neostoreassessment13531.core.util.UiState
import com.app.neostoreassessment13531.neostore.domain.repo_user.UserDataModel
import com.app.neostoreassessment13531.neostore.domain.repo_user.dummyUserDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor() : ViewModel(){

    private val _state : MutableStateFlow<StateUserList> = MutableStateFlow(StateUserList(
        usersList = arrayListOf(),
        state = UiState()
    ))
    val state = _state.asStateFlow()

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        viewModelScope.launch {
            _state.update { it.copy(state = UiState(screenState = ScreenState.Loading)) }
            delay(1000)
            _state.update {
                val list : ArrayList<UserDataModel> = ArrayList()
                for (index in 0..10){
                    list.add(dummyUserDataModel)
                }
                it.copy(usersList = list , state = UiState(screenState = ScreenState.None))
            }
        }
    }

}