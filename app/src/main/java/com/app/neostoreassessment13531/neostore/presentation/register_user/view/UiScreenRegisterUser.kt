package com.app.neostoreassessment13531.neostore.presentation.register_user.view

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Phone
import androidx.compose.material.icons.twotone.School
import androidx.compose.material.icons.twotone.Work
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.app.neostoreassessment13531.core.LocalNavController
import com.app.neostoreassessment13531.core.ui.CustomDropDown
import com.app.neostoreassessment13531.core.ui.CustomToolbar
import com.app.neostoreassessment13531.core.ui.HeadlineTextField
import com.app.neostoreassessment13531.core.ui.VerticalSpacer
import com.app.neostoreassessment13531.core.ui.screenMargin
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.core.util.FileHelper
import com.app.neostoreassessment13531.neostore.domain.enum.EducationType
import com.app.neostoreassessment13531.neostore.domain.enum.Form
import com.app.neostoreassessment13531.neostore.domain.enum.Gender
import com.app.neostoreassessment13531.neostore.domain.model.AddressModel
import com.app.neostoreassessment13531.neostore.domain.model.EducationModel
import com.app.neostoreassessment13531.neostore.domain.model.ProfessionalModel
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import com.app.neostoreassessment13531.neostore.presentation.register_user.state.UiRegisterUserActions
import com.app.neostoreassessment13531.neostore.presentation.register_user.view_model.RegisterUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import java.io.File


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
        onUiRegisterUserActions = {
            when (it) {
                is UiRegisterUserActions.OnBackPressed -> onBackPressed(
                    navController = navController, pagerState = pagerState, scope = scope
                )

                else -> viewModel.onUserAction(
                    action = it,
                    onNextPage = { onNextPage(scope = scope, pagerState = pagerState) },
                    onRegisterUserCompletion = { navController.navigateUp() })
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiRegisterUser(
    state: RegisterUserModel,
    pagerState: PagerState,
    onUiRegisterUserActions: (UiRegisterUserActions) -> Unit
) {
    val context = LocalContext.current
    var isOpenCameraDialog by remember { mutableStateOf(false) }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                val uri = FileHelper.saveBitmapToCacheAndGetUri(context, it)
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateUserState(
                        state.user.copy(
                            imageUri = uri.toString()
                        )
                    )
                )
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver = context.contentResolver
                try {
                    contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    val uriString = it.toString()
                    onUiRegisterUserActions(UiRegisterUserActions.OnUpdateUserState(state.user.copy(imageUri = uriString)))
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    BackHandler(true) {
        onUiRegisterUserActions.invoke(UiRegisterUserActions.OnBackPressed)
    }


    Scaffold(
        topBar = {
            CustomToolbar("Register User", isBackArrow = true) {
                onUiRegisterUserActions.invoke(UiRegisterUserActions.OnBackPressed)
            }
        }) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .screenMargin()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                pageSpacing = 12.dp,
                userScrollEnabled = false
            ) { index ->
                when (index) {
                    0 -> UiFormUser(
                        state = state.user,
                        onUiRegisterUserActions = onUiRegisterUserActions,
                        imageModifier = Modifier.clickable {
                            isOpenCameraDialog = true
                        })

                    1 -> UiFormAddress(
                        state = state.address, onUiRegisterUserActions = onUiRegisterUserActions
                    )

                    2 -> UiFormProfessional(
                        proState = state.professional,
                        eduState = state.education,
                        onUiRegisterUserActions
                    )
                }
            }


            if (isOpenCameraDialog) {
                DialogImagePicker(
                    modifier = Modifier.align(Alignment.Center),
                    onDismissRequest = { isOpenCameraDialog = false },
                    onCameraClick = {
                        cameraLauncher.launch(null)
                        isOpenCameraDialog = false
                    },
                    onGalleryClick = {
                        galleryLauncher.launch("image/*")
                        isOpenCameraDialog = false
                    })

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogImagePicker(
    modifier: Modifier,
    onDismissRequest: () -> Unit = {},
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {

    BasicAlertDialog(
        modifier = modifier
            .safeContentPadding()
            .fillMaxWidth()
            .screenMargin(),
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            colors = CardDefaults.elevatedCardColors(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceAround
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable { onGalleryClick() }) {
                    Image(
                        modifier = Modifier
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface), CircleShape
                            )
                            .size(100.dp)
                            .padding(20.dp),
                        painter = rememberVectorPainter(Icons.Outlined.PhotoLibrary),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Gallery",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable { onCameraClick() }
                ) {
                    Image(
                        modifier = Modifier
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface), CircleShape
                            )
                            .size(100.dp)
                            .padding(20.dp),
                        painter = rememberVectorPainter(Icons.Outlined.Camera),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Camera",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
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
    onUiRegisterUserActions: (UiRegisterUserActions) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Educational Info", textAlign = TextAlign.Start, fontWeight = FontWeight.Bold
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
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateEducationModel(
                        eduState.copy(
                            education = value
                        )
                    )
                )
            })

        HeadlineTextField(
            value = eduState.yearOfPassing,
            onValueChange = { value ->
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateEducationModel(
                        eduState.copy(
                            yearOfPassing = value
                        )
                    )
                )
            },
            placeHolderText = "Year of passing",
            imageVector = Icons.TwoTone.School,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = eduState.grade,
            onValueChange = { value ->
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateEducationModel(
                        eduState.copy(
                            grade = value
                        )
                    )
                )
            },
            placeHolderText = "Grade",
            imageVector = Icons.TwoTone.School,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        VerticalSpacer(10.dp)

        Text(
            text = "Professional Info", textAlign = TextAlign.Start, fontWeight = FontWeight.Bold
        )

        VerticalSpacer(5.dp)

        HeadlineTextField(
            value = proState.experience,
            onValueChange = { value ->
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateProfessionalState(
                        proState.copy(
                            experience = value
                        )
                    )
                )
            },
            placeHolderText = "Experience",
            imageVector = Icons.TwoTone.Work,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = proState.designation,
            onValueChange = { value ->
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateProfessionalState(
                        proState.copy(
                            designation = value
                        )
                    )
                )
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
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateProfessionalState(
                        proState.copy(
                            domain = value,
                        )
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
                .padding(top = 15.dp), onClick = {
                onUiRegisterUserActions(UiRegisterUserActions.OnValidate(Form.Professional))
            }) {
            Text("Register")
        }

    }
}

@Composable
fun UiFormUser(
    state: UserDataModel,
    onUiRegisterUserActions: (UiRegisterUserActions) -> Unit,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(Modifier.align(Alignment.CenterHorizontally)) {
            Image(
                modifier = imageModifier
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
                        RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
                    )
                    .size(100.dp)
                    .padding(10.dp),
                painter = if (state.imageUri.isNotEmpty())
                    rememberAsyncImagePainter(state.imageUri.toUri())
                else
                    rememberVectorPainter(Icons.Outlined.AddAPhoto),
                contentDescription = ""
            )
        }

        HeadlineTextField(
            value = state.firstName,
            onValueChange = { value ->
                onUiRegisterUserActions(UiRegisterUserActions.OnUpdateUserState(state.copy(firstName = value)))
            },
            placeHolderText = "First Name",
            imageVector = Icons.TwoTone.AccountCircle,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = state.lastName,
            onValueChange = { value ->
                onUiRegisterUserActions(UiRegisterUserActions.OnUpdateUserState(state.copy(lastName = value)))
            },
            placeHolderText = "Last Name",
            imageVector = Icons.TwoTone.AccountCircle,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        HeadlineTextField(
            value = state.phoneNumber,
            onValueChange = { value ->
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateUserState(
                        state.copy(
                            phoneNumber = value
                        )
                    )
                )
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
                onUiRegisterUserActions(UiRegisterUserActions.OnUpdateUserState(state.copy(email = value)))
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
                    onUiRegisterUserActions(
                        UiRegisterUserActions.OnUpdateUserState(
                            state.copy(
                                gender = gender.toString()
                            )
                        )
                    )
                },
                modifier = Modifier.size(30.dp),
            )
            Text("Male", style = MaterialTheme.typography.labelSmall)

            RadioButton(
                selected = gender == Gender.Female,
                onClick = {
                    gender = Gender.Female
                    onUiRegisterUserActions(
                        UiRegisterUserActions.OnUpdateUserState(
                            state.copy(
                                gender = gender.toString()
                            )
                        )
                    )
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
                onUiRegisterUserActions(UiRegisterUserActions.OnUpdateUserState(state.copy(password = value)))
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
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp),
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 15.dp), onClick = {
                onUiRegisterUserActions(UiRegisterUserActions.OnValidate(Form.User))
            }) {
            Text("NEXT")
        }
    }
}

@Composable
fun UiFormAddress(
    state: AddressModel, onUiRegisterUserActions: (UiRegisterUserActions) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeadlineTextField(
            value = state.address,
            onValueChange = { value ->
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateAddressState(
                        state.copy(
                            address = value
                        )
                    )
                )
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
                onUiRegisterUserActions(
                    UiRegisterUserActions.OnUpdateAddressState(
                        (state.copy(
                            landmark = value
                        ))
                    )
                )
            },
            placeHolderText = "Landmark",
            imageVector = Icons.Rounded.LocationCity,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            paddingValues = PaddingValues(bottom = 12.dp)
        )


        Text(
            text = "State", textAlign = TextAlign.Start, style = MaterialTheme.typography.bodyLarge
        )
        CustomDropDown(
            arrString = arrStatesList,
            paddingValues = PaddingValues(bottom = 12.dp),
            value = state.state,
            placeHolderText = "Select",
            onValueChange = { value ->
                onUiRegisterUserActions(UiRegisterUserActions.OnUpdateAddressState((state.copy(state = value))))
            })

        HeadlineTextField(
            value = state.city,
            onValueChange = { value ->
                onUiRegisterUserActions(UiRegisterUserActions.OnUpdateAddressState((state.copy(city = value))))
            },
            placeHolderText = "City",
            imageVector = Icons.Rounded.LocationCity,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            paddingValues = PaddingValues(bottom = 12.dp)
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 15.dp), onClick = {
                onUiRegisterUserActions(UiRegisterUserActions.OnValidate(Form.Address))
            }) {
            Text("NEXT")
        }

    }
}


@Preview(
    showBackground = true
)
@Composable
private fun UiRegisterUserPreview() {
    AppTheme {
        Surface(modifier = Modifier.padding(12.dp)) {
            UiFormUser(
                state = UserDataModel(),
                onUiRegisterUserActions = {

                })
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
