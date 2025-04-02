package com.app.neostoreassessment13531.neostore.domain.model

data class UserDataModel(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val gender: String = "",
    val password: String = "",
    val addressModel: AddressModel ?= null,
    val educationModel: EducationModel ?= null,
    val professional: ProfessionalModel ?= null
)


val dummyUserDataModel = UserDataModel(
    firstName = "Omkar",
    lastName = "Sawant",
    phoneNumber = "8779876543",
    email = "okmbvfg@dfbnm.coc",
    gender = "Male",
)
