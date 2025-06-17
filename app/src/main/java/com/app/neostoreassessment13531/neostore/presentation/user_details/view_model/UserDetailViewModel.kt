package com.app.neostoreassessment13531.neostore.presentation.user_details.view_model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.neostoreassessment13531.core.util.UiState
import com.app.neostoreassessment13531.neostore.domain.repository.RepositoryUser
import com.app.neostoreassessment13531.neostore.presentation.user_details.state.StateUserDetails
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = UserDetailViewModel.UserDetailViewModelFactory::class)
class UserDetailViewModel @AssistedInject constructor(
    @Assisted private val userId: String,
    private val repositoryUser: RepositoryUser
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<StateUserDetails>> =
        MutableStateFlow(UiState(state = StateUserDetails()))
    val state = _state.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryUser
                .getUserInfo(userId)
                .collectLatest { model ->
                    _state.update { it.copy(state = StateUserDetails(user = model)) }
                }
        }
    }

    @AssistedFactory
    interface UserDetailViewModelFactory {
        fun create(userId: String): UserDetailViewModel
    }

}