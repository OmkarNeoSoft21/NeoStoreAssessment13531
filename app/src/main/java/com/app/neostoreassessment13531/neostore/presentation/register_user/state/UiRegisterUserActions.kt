package com.app.neostoreassessment13531.neostore.presentation.register_user.state

import com.app.neostoreassessment13531.neostore.domain.enum.Form
import com.app.neostoreassessment13531.neostore.domain.model.AddressModel
import com.app.neostoreassessment13531.neostore.domain.model.EducationModel
import com.app.neostoreassessment13531.neostore.domain.model.ProfessionalModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel

sealed interface UiRegisterUserActions {
    data class OnUpdateUserState(val model: UserDataModel): UiRegisterUserActions
    data class OnUpdateAddressState(val model: AddressModel): UiRegisterUserActions
    data class OnUpdateProfessionalState(val model: ProfessionalModel): UiRegisterUserActions
    data class OnUpdateEducationModel(val model: EducationModel): UiRegisterUserActions
    data class OnValidate(val form: Form): UiRegisterUserActions
    data object OnBackPressed: UiRegisterUserActions
}