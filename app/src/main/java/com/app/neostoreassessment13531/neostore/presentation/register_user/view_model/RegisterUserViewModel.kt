package com.app.neostoreassessment13531.neostore.presentation.register_user.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.neostoreassessment13531.core.snackbar.SnackBarController
import com.app.neostoreassessment13531.core.snackbar.SnackBarData
import com.app.neostoreassessment13531.core.util.ScreenState
import com.app.neostoreassessment13531.core.util.UiState
import com.app.neostoreassessment13531.neostore.domain.enum.Form
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.repository.RepositoryUser
import com.app.neostoreassessment13531.neostore.presentation.register_user.state.StateRegisterUser
import com.app.neostoreassessment13531.neostore.presentation.register_user.state.UiRegisterUserActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val repositoryUser: RepositoryUser
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<StateRegisterUser>> =
        MutableStateFlow(UiState(state = StateRegisterUser(RegisterUserModel())))
    val state = _state.asStateFlow()

    fun validateUser(onValidated: () -> Unit) {
        _state.value.state?.registerUser?.run {
            listOf(
                Pair(user.imageUri.isEmpty(), "Please add image for your profile"),
                Pair(user.firstName.isEmpty(), "Please enter first name"),
                Pair(user.lastName.isEmpty(), "Please enter last name"),
                Pair(user.phoneNumber.isEmpty(), "Please enter your phone number"),
                Pair(user.phoneNumber.length == 9, "Please enter 10 digit phone number"),
                Pair(user.email.isEmpty(), "Please enter your email"),
                Pair(user.gender.isEmpty(), "Please select your gender"),
                Pair(user.password.isEmpty(), "Please enter password"),
                Pair(user.password.isEmpty(), "Please enter password"),
            ).firstOrNull {
                it.first
            }?.run {
                SnackBarController.showMessage(SnackBarData(second))
            } ?: onValidated.invoke()
        }
    }

    fun validateAddress(onValidated: () -> Unit) {
        _state.value.state?.registerUser?.run {
            listOf(
                Pair(address.address.isEmpty(), "Please enter address"),
                Pair(address.city.isEmpty(), "Please enter city"),
                Pair(address.state.isEmpty(), "Please select state"),
                Pair(address.landmark.isEmpty(), "Please enter landmark"),
            ).firstOrNull {
                it.first
            }?.run {
                SnackBarController.showMessage(SnackBarData(second))
            } ?: onValidated.invoke()
        }
    }

    fun validateProfessional(onValidated: () -> Unit) {
        _state.value.state?.registerUser?.run {
            listOf(
                Pair(education.education.isEmpty(), "Please select education"),
                Pair(education.yearOfPassing.isEmpty(), "Please enter year of passing"),
                Pair(education.grade.isEmpty(), "Please enter your grade"),
                Pair(professional.experience.isEmpty(), "Please enter no of years experience"),
                Pair(professional.designation.isEmpty(), "Please enter designation"),
                Pair(professional.domain.isEmpty(), "Please enter domain"),
            ).firstOrNull {
                it.first
            }?.run {
                SnackBarController.showMessage(SnackBarData(second))
            } ?: onValidated.invoke()
        }
    }

    fun onUserAction(action: UiRegisterUserActions ,onNextPage:()->Unit , onRegisterUserCompletion:()->Unit) {
        when (action) {
            is UiRegisterUserActions.OnUpdateAddressState -> {
                _state.update {
                    it.copy(
                        state = it.state?.copy(
                            registerUser = it.state.registerUser.copy(address = action.model)
                        )
                    )
                }
            }

            is UiRegisterUserActions.OnUpdateEducationModel -> {
                _state.update {
                    it.copy(
                        state = it.state?.copy(
                            registerUser = it.state.registerUser.copy(education = action.model)
                        )
                    )
                }
            }

            is UiRegisterUserActions.OnUpdateProfessionalState -> {
                _state.update {
                    it.copy(
                        state = it.state?.copy(
                            registerUser = it.state.registerUser.copy(professional = action.model)
                        )
                    )
                }
            }

            is UiRegisterUserActions.OnUpdateUserState -> {
                _state.update {
                    it.copy(
                        state = it.state?.copy(
                            registerUser = it.state.registerUser.copy(user = action.model)
                        )
                    )
                }
            }

            is UiRegisterUserActions.OnValidate -> {
                when (action.form) {
                    Form.User -> validateUser(onNextPage)

                    Form.Address -> validateAddress(onNextPage)

                    Form.Professional -> validateProfessional {
                        registerUser(onRegisterUserCompletion)
                    }
                }
            }

            UiRegisterUserActions.OnBackPressed -> {}
        }
    }

    fun registerUser(onRegisterUserCompletion: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(screenState = ScreenState.Loading) }
            _state.value.state?.registerUser?.run {
                val isSaved = repositoryUser.saveUser(this)
                withContext(Dispatchers.Main.immediate) {
                    if (isSaved) {
                        SnackBarController.showMessage(SnackBarData("Record Saved Successfully!"))
                        onRegisterUserCompletion.invoke()
                    } else {
                        SnackBarController.showMessage(SnackBarData("Something went wrong!"))
                    }
                }
            }
            _state.update { it.copy(screenState = ScreenState.None) }
        }
    }




}