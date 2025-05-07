package com.app.neostoreassessment13531.neostore.presentation.user_details.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.app.neostoreassessment13531.core.LocalNavController
import com.app.neostoreassessment13531.core.animation.customBoundsTransform
import com.app.neostoreassessment13531.core.ui.CustomToolbar
import com.app.neostoreassessment13531.core.ui.screenMargin
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.core.util.ScreenState
import com.app.neostoreassessment13531.neostore.domain.model.AddressModel
import com.app.neostoreassessment13531.neostore.domain.model.EducationModel
import com.app.neostoreassessment13531.neostore.domain.model.ProfessionalModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import com.app.neostoreassessment13531.neostore.domain.util.HelperFun
import com.app.neostoreassessment13531.neostore.presentation.user_details.state.StateUserDetails
import com.app.neostoreassessment13531.neostore.presentation.user_details.state.UiUserDetailActions
import com.app.neostoreassessment13531.neostore.presentation.user_details.view_model.UserDetailViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UiScreenUserDetail(
    userId: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: UserDetailViewModel = hiltViewModel<UserDetailViewModel, UserDetailViewModel.UserDetailViewModelFactory>(
        creationCallback = { factory: UserDetailViewModel.UserDetailViewModelFactory ->
            factory.create(userId)
        }
    )
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    UiUserDetails(
        sharedTransitionScope,
        animatedContentScope,
        screenState = uiState.screenState,
        state = uiState.state,
        onUserAction = {
            navController.navigateUp()
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun UiUserDetails(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    screenState: ScreenState,
    state: StateUserDetails?,
    onUserAction: (UiUserDetailActions) -> Unit
) {
    Scaffold(topBar = {
        CustomToolbar("User Detail", isBackArrow = true) {
            onUserAction(UiUserDetailActions.OnBackPressed)
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .screenMargin()
                .verticalScroll(rememberScrollState())
        ) {
            // Profile header with image
            ProfileHeader(state?.user , sharedTransitionScope, animatedContentScope)

            Spacer(modifier = Modifier.height(24.dp))

            // Personal Information
            SectionTitle("Personal Information")
            Spacer(modifier = Modifier.height(8.dp))
            PersonalInfoSection(state?.user ?: UserDataModel())

            Spacer(modifier = Modifier.height(24.dp))

            // Address Information
            SectionTitle("Address Information")
            Spacer(modifier = Modifier.height(8.dp))
            AddressSection(state?.user?.addressModel ?: AddressModel())

            Spacer(modifier = Modifier.height(24.dp))

            // Education Information
            SectionTitle("Education")
            Spacer(modifier = Modifier.height(8.dp))
            EducationSection(state?.user?.educationModel ?: EducationModel())

            Spacer(modifier = Modifier.height(24.dp))

            // Professional Information
            SectionTitle("Professional Experience")
            Spacer(modifier = Modifier.height(8.dp))
            ProfessionalSection(state?.user?.professional ?: ProfessionalModel())

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Preview
@Composable
private fun UiUserListPreview() {
    AppTheme(dynamicColor = false) {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavController provides navController) {
            /*UiUserDetails(
                screenState = ScreenState.None,
                state = StateUserDetails(
                    user = HelperFun.getDummyRegisterUserModel().user
                )
            ) {

            }*/
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ProfileHeader(
    user: UserDataModel?,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    with(sharedTransitionScope){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "image-${user?.id}"),
                        animatedVisibilityScope = animatedContentScope,
                        boundsTransform = customBoundsTransform
                    )
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)

            ) {
                Image(
                    painter = if (!user?.imageUri.isNullOrEmpty())
                        rememberAsyncImagePainter(user.imageUri.toUri())
                    else
                        rememberVectorPainter(Icons.Default.Person4),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.sharedBounds(
                    sharedTransitionScope.rememberSharedContentState(key = "name-${user?.id}"),
                    animatedVisibilityScope = animatedContentScope,
                    boundsTransform = customBoundsTransform
                ),
                text = "${user?.firstName ?: ""} ${user?.lastName ?: ""}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier.sharedBounds(
                    sharedTransitionScope.rememberSharedContentState(key = "designation-${user?.id}"),
                    animatedVisibilityScope = animatedContentScope,
                    boundsTransform = customBoundsTransform
                ),
                text = user?.professional?.designation ?: "",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun PersonalInfoSection(user: UserDataModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow(
                icon = Icons.Default.Person,
                label = "Full Name",
                value = "${user.firstName} ${user.lastName}"
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = user.email
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = user.phoneNumber
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.Face,
                label = "Gender",
                value = user.gender
            )
        }
    }
}

@Composable
private fun AddressSection(address: AddressModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow(
                icon = Icons.Default.Home,
                label = "Address",
                value = address.address
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.LocationOn,
                label = "Landmark",
                value = address.landmark
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.LocationCity,
                label = "City",
                value = address.city
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.Map,
                label = "State",
                value = address.state
            )
        }
    }
}

@Composable
private fun EducationSection(education: EducationModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow(
                icon = Icons.Default.School,
                label = "Degree",
                value = education.education
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.CalendarToday,
                label = "Year of Passing",
                value = education.yearOfPassing
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.Star,
                label = "Grade/GPA",
                value = education.grade
            )
        }
    }
}

@Composable
private fun ProfessionalSection(professional: ProfessionalModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow(
                icon = Icons.Default.Work,
                label = "Designation",
                value = professional.designation
            )

            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )

            InfoRow(
                icon = Icons.Default.Business,
                label = "Domain",
                value = professional.domain
            )
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                1.dp,
                MaterialTheme.colorScheme.outline
            )


            InfoRow(
                icon = Icons.Default.Timeline,
                label = "Experience",
                value = "${professional.experience} years"
            )
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}