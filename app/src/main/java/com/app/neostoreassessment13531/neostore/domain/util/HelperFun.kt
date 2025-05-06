package com.app.neostoreassessment13531.neostore.domain.util

import com.app.neostoreassessment13531.neostore.domain.model.AddressModel
import com.app.neostoreassessment13531.neostore.domain.model.EducationModel
import com.app.neostoreassessment13531.neostore.domain.model.ProfessionalModel
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel

object HelperFun{
    fun getDummyRegisterUserModel(): RegisterUserModel {
        return RegisterUserModel(
            user = UserDataModel(
                firstName = "John",
                lastName = "Doe",
                phoneNumber = "1234567890",
                email = "john.doe@example.com",
                gender = "Male",
                password = "password123",
                imageUri = ""
            ),
            address = AddressModel(
                address = "123 Main St",
                landmark = "Near the park",
                city = "Anytown",
                state = "California"
            ),
            education = EducationModel(
                education = "Bachelor's Degree",
                yearOfPassing = "2020",
                grade = "3.5"
            ),
            professional = ProfessionalModel(
                experience = "3",
                designation = "Software Engineer",
                domain = "Technology"
            )
        )
    }
}