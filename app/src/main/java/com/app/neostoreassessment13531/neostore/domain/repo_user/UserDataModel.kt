package com.app.neostoreassessment13531.neostore.domain.repo_user

data class UserDataModel(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val profession: String,
    val experience: String,
    val email: String,
    val gender: String,
)

val dummyUserDataModel = UserDataModel(
    firstName = "Omkar",
    lastName = "Sawant",
    profession = "Senior Software Engineer",
    phoneNumber = "8779876543",
    experience = "5.9",
    email = "okmbvfg@dfbnm.coc",
    gender = "Male",
)
