package com.app.neostoreassessment13531.neostore.presentation.user_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.neostoreassessment13531.neostore.presentation.user_list.state.StateUserList
import com.app.neostoreassessment13531.core.util.UiState
import com.app.neostoreassessment13531.neostore.domain.repository.RepositoryUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repositoryUser: RepositoryUser
) : ViewModel(){

    private val _state : MutableStateFlow<UiState<StateUserList>> = MutableStateFlow(UiState(state = StateUserList()))
    val state = _state.asStateFlow()

    init {
        loadUserList()
    }

    private fun loadUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryUser
                .getAllUsers()
                .collectLatest { list  ->
                    _state.update { it.copy(state = StateUserList(usersList = list as ArrayList)) }
                }
        }
    }

}