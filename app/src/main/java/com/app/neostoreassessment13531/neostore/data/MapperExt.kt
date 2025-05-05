package com.app.neostoreassessment13531.neostore.data

import com.app.neostoreassessment13531.neostore.data.local.entities.AddressTable
import com.app.neostoreassessment13531.neostore.data.local.entities.EducationInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.ProfessionalInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.domain.model.AddressModel
import com.app.neostoreassessment13531.neostore.domain.model.EducationModel
import com.app.neostoreassessment13531.neostore.domain.model.ProfessionalModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel

fun AddressTable.toAddressModel(): AddressModel {
    return AddressModel(
        userId = userId.toString(),
        addressId = addressId,
        address = address,
        landmark = landmark,
        city = city,
        state = state
    )
}

fun AddressModel.toAddressTable(userId: Int): AddressTable {
    return AddressTable(
        userId = userId,
        address = address,
        landmark = landmark,
        city = city,
        state = state
    )
}

fun EducationInfoTable.toEducationModel(): EducationModel {
    return EducationModel(
        education = education,
        yearOfPassing = yearOfPassing,
        grade = grade
    )
}

fun EducationModel.toEducationInfoTable(userId: Int): EducationInfoTable {
    return EducationInfoTable(
        userId = userId,
        education = education,
        yearOfPassing = yearOfPassing,
        grade = grade
    )
}

fun UserTable.toUserDataModel(): UserDataModel {
    return UserDataModel(
        id = userId.toString(),
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        email = email,
        gender = gender
    )
}

fun UserDataModel.toUserTable(): UserTable {
    return UserTable(
        userId = id.toIntOrNull() ?: 0, // Convert to Int safely
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        imageUri = imageUri,
        email = email,
        gender = gender
    )
}

fun ProfessionalModel.toProfessionalTable(userId: Int): ProfessionalInfoTable {
    return ProfessionalInfoTable(
        userId = userId,
        experience = experience,
        designation = designation ,
        domain = domain,
    )
}

fun ProfessionalInfoTable.toProfessionalModel(): ProfessionalModel {
    return ProfessionalModel(
        experience = experience,
        designation = designation,
        domain = domain
    )
}