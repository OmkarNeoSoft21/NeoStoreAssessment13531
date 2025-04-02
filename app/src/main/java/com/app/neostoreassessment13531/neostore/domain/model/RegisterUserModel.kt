package com.app.neostoreassessment13531.neostore.domain.model

data class RegisterUserModel(
    val user: UserDataModel = UserDataModel(),
    val address: AddressModel = AddressModel(),
    val education: EducationModel = EducationModel(),
    val professional: ProfessionalModel = ProfessionalModel()
)

data class AddressModel(
    val userId: String = "",
    val addressId: Int = 0,
    val address: String = "",
    val landmark: String = "",
    val city: String = "",
    val state: String = "",
)

data class EducationModel(
    val education: String = "",
    val yearOfPassing: String = "",
    val grade: String = ""
)

data class ProfessionalModel(
    val experience: String = "",
    val designation: String = "",
    val domain: String = ""
)