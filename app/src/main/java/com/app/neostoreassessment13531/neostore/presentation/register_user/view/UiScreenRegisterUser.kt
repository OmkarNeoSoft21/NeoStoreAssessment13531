package com.app.neostoreassessment13531.neostore.presentation.register_user.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Phone
import androidx.compose.material.icons.twotone.School
import androidx.compose.material.icons.twotone.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.app.neostoreassessment13531.core.LocalNavController
import com.app.neostoreassessment13531.core.ui.CustomDropDown
import com.app.neostoreassessment13531.core.ui.CustomToolbar
import com.app.neostoreassessment13531.core.ui.HeadlineTextField
import com.app.neostoreassessment13531.core.ui.VerticalSpacer
import com.app.neostoreassessment13531.core.ui.screenMargin
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.neostore.domain.enum.EducationType
import com.app.neostoreassessment13531.neostore.domain.model.AddressModel
import com.app.neostoreassessment13531.neostore.domain.model.EducationModel
import com.app.neostoreassessment13531.neostore.domain.model.ProfessionalModel
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import com.app.neostoreassessment13531.neostore.presentation.register_user.view_model.RegisterUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UiScreenRegisterUser(
    viewModel: RegisterUserViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(0) { 3 }
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current
    UiRegisterUser(
        state = uiState.state?.registerUser ?: RegisterUserModel(),
        pagerState = pagerState,
        onUpdateUserState = { viewModel.updateState(it) },
        onUpdateAddressState = { viewModel.updateState(it) },
        onUpdateProfessionalState = { viewModel.updateState(it) },
        onUpdateEducationModel = { viewModel.updateState(it) },
        onValidate = {
            when (it) {
                Form.User -> viewModel.validateUser {
                    onNextPage(scope = scope, pagerState)
                }

                Form.Address -> viewModel.validateAddress { onNextPage(scope = scope, pagerState) }

                Form.Professional -> viewModel.validateProfessional {
                    viewModel.registerUser { navController.navigateUp() }
                }
            }
        }
    )
}

enum class Gender {
    None, Male, Female
}

enum class Form {
    User, Address, Professional,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiRegisterUser(
    state: RegisterUserModel,
    pagerState: PagerState,
    onUpdateUserState: (UserDataModel) -> Unit,
    onUpdateAddressState: (AddressModel) -> Unit,
    onUpdateEducationModel: (EducationModel) -> Unit,
    onUpdateProfessionalState: (ProfessionalModel) -> Unit,
    onValidate: (Form) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current
    BackHandler(true) {
        onBackPressed(navController, pagerState, scope)
    }
    Scaffold(topBar = {
        CustomToolbar("Register User", isBackArrow = true) {
            onBackPressed(navController, pagerState, scope)
        }
    }) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .screenMargin()
        ) {
            HorizontalPager(state = pagerState) { index ->
                when (index) {
                    0 -> UiFormUser(
                        state = state.user,
                        onUpdateState = onUpdateUserState,
                        onValidate = onValidate
                    )

                    1 -> UiFormAddress(
                        state = state.address,
                        onUpdateAddress = onUpdateAddressState,
                        onValidate = onValidate
                    )

                    2 -> UiFormProfessional(
                        proState = state.professional,
                        eduState = state.education,
                        onUpdateEducationState = onUpdateEducationModel,
                        onUpdateProfessionalState = onUpdateProfessionalState,
                        onValidate = onValidate
                    )
                }
            }
        }

    }
}

fun onNextPage(scope: CoroutineScope, pagerState: PagerState) {
    scope.launch {
        if (pagerState.currentPage < 2) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }
}

fun onBackPressed(navController: NavHostController, pagerState: PagerState, scope: CoroutineScope) {
    if (pagerState.currentPage == 0) {
        navController.navigateUp()
    } else {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }
}

@Composable
fun UiFormProfessional(
    proState: ProfessionalModel,
    eduState: EducationModel,
    onUpdateProfessionalState: (ProfessionalModel) -> Unit,
    onUpdateEducationState: (EducationModel) -> Unit,
    onValidate: (Form) -> Unit
) {


    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Educational Info",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(5.dp)

        val educationList = EducationType.entries.map { it.typeName } as ArrayList<String>
        Text(
            text = "Education",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge
        )
        CustomDropDown(
            arrString = educationList,
            paddingValues = PaddingValues(bottom = 12.dp),
            value = eduState.education,
            placeHolderText = "Select",
            onValueChange = { value ->
                onUpdateEducationState(eduState.copy(education = value))
            }
        )

        HeadlineTextField(
            value = eduState.yearOfPassing,
            onValueChange = { value ->
                onUpdateEducationState(eduState.copy(yearOfPassing = value))
            },
            placeHolderText = "Year of passing",
            imageVector = Icons.TwoTone.School,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = eduState.grade,
            onValueChange = { value ->
                onUpdateEducationState(eduState.copy(grade = value))
            },
            placeHolderText = "Grade",
            imageVector = Icons.TwoTone.School,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        VerticalSpacer(10.dp)

        Text(
            text = "Professional Info",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(5.dp)

        HeadlineTextField(
            value = proState.experience,
            onValueChange = { value ->
                onUpdateProfessionalState(proState.copy(experience = value))
            },
            placeHolderText = "Experience",
            imageVector = Icons.TwoTone.Work,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = proState.designation,
            onValueChange = { value ->
                onUpdateProfessionalState(proState.copy(designation = value))
            },
            placeHolderText = "Designation",
            imageVector = Icons.TwoTone.Work,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = proState.domain,
            onValueChange = { value ->
                onUpdateProfessionalState(
                    proState.copy(
                        domain = value,
                    )
                )
            },
            placeHolderText = "Domain",
            imageVector = Icons.TwoTone.Work,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )


        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 15.dp),
            onClick = {
                onValidate(Form.Professional)
            }) {
            Text("Register")
        }

    }
}

@Composable
fun UiFormUser(
    state: UserDataModel,
    onUpdateState: (UserDataModel) -> Unit,
    onValidate: (Form) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        HeadlineTextField(
            value = state.firstName,
            onValueChange = { value ->
                onUpdateState(state.copy(firstName = value))
            },
            placeHolderText = "First Name",
            imageVector = Icons.TwoTone.AccountCircle,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = state.lastName,
            onValueChange = { value ->
                onUpdateState(state.copy(lastName = value))
            },
            placeHolderText = "Last Name",
            imageVector = Icons.TwoTone.AccountCircle,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = state.phoneNumber,
            onValueChange = { value ->
                onUpdateState(state.copy(phoneNumber = value))
            },
            placeHolderText = "Phone Number",
            imageVector = Icons.TwoTone.Phone,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = state.email,
            onValueChange = { value ->
                onUpdateState(state.copy(email = value))
            },
            placeHolderText = "Email",
            imageVector = Icons.TwoTone.Email,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )


        Text(
            text = "Gender", textAlign = TextAlign.Start, fontWeight = FontWeight.W500
        )
        var gender by remember { mutableStateOf(Gender.None) }
        Row(
            modifier = Modifier.padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender == Gender.Male,
                onClick = {
                    gender = Gender.Male
                    onUpdateState(state.copy(gender = gender.toString()))
                },
                modifier = Modifier.size(30.dp),
            )
            Text("Male", style = MaterialTheme.typography.labelSmall)

            RadioButton(
                selected = gender == Gender.Female,
                onClick = {
                    gender = Gender.Female
                    onUpdateState(state.copy(gender = gender.toString()))
                },
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(30.dp),
            )
            Text("Female", style = MaterialTheme.typography.labelSmall)
        }


        HeadlineTextField(
            value = state.password,
            onValueChange = { value ->
                onUpdateState(state.copy(password = value))
            },
            placeHolderText = "Password",
            imageVector = Icons.TwoTone.Lock,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp),
        )

        var confirmPassword by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        HeadlineTextField(
            value = confirmPassword,
            onValueChange = { value ->
                confirmPassword = value
                isError = confirmPassword != state.password
            },
            isError = isError,
            placeHolderText = "Confirm Password",
            imageVector = Icons.TwoTone.Lock,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp),
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 15.dp),
            onClick = {
                onValidate(Form.User)
            }) {
            Text("NEXT")
        }
    }
}

@Composable
fun UiFormAddress(
    state: AddressModel,
    onUpdateAddress: (AddressModel) -> Unit,
    onValidate: (Form) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        HeadlineTextField(
            value = state.address,
            onValueChange = { value ->
                onUpdateAddress(state.copy(address = value))
            },
            placeHolderText = "Address",
            imageVector = Icons.Rounded.LocationCity,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = state.landmark,
            onValueChange = { value ->
                onUpdateAddress(state.copy(landmark = value))
            },
            placeHolderText = "Landmark",
            imageVector = Icons.Rounded.LocationCity,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )


        Text(
            text = "State",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge
        )
        CustomDropDown(
            arrString = arrStatesList,
            paddingValues = PaddingValues(bottom = 12.dp),
            value = state.state,
            placeHolderText = "Select",
            onValueChange = { value ->
                onUpdateAddress(state.copy(state = value))
            }
        )

        HeadlineTextField(
            value = state.city,
            onValueChange = { value ->
                onUpdateAddress(state.copy(city = value))
            },
            placeHolderText = "City",
            imageVector = Icons.Rounded.LocationCity,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 15.dp),
            onClick = {
                onValidate(Form.Address)
            }
        ) {
            Text("NEXT")
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun UiRegisterUserPreview() {
    AppTheme {
        Surface(modifier = Modifier.padding(12.dp)) {
            UiFormAddress(
                state = AddressModel(),
                onUpdateAddress = {

                },
                onValidate = {

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UiRegisterProfessionalPreview() {
    AppTheme() {
        Surface(modifier = Modifier.padding(12.dp)) {
            UiFormProfessional(
                proState = ProfessionalModel(),
                eduState = EducationModel(),
                onUpdateProfessionalState = {},
                onUpdateEducationState = {},
                onValidate = {}
            )
        }
    }
}

var arrStatesList = arrayListOf(
    "Andhra Pradesh",
    "Arunachal Pradesh",
    "Assam",
    "Bihar",
    "Chhattisgarh",
    "Goa",
    "Gujarat",
    "Haryana",
    "Himachal Pradesh",
    "Jharkhand",
    "Karnataka",
    "Kerala",
    "Madhya Pradesh",
    "Maharashtra",
    "Manipur",
    "Meghalaya",
    "Mizoram",
    "Nagaland",
    "Odisha",
    "Punjab",
    "Rajasthan",
    "Sikkim",
    "Tamil Nadu",
    "Telangana",
    "Tripura",
    "Uttar Pradesh",
    "Uttarakhand",
    "West Bengal"
)
