package com.app.neostoreassessment13531.neostore.presentation.user_list.view

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.neostoreassessment13531.core.LocalNavController
import com.app.neostoreassessment13531.core.navigation.Route
import com.app.neostoreassessment13531.core.ui.CustomToolbar
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.core.util.ScreenState
import com.app.neostoreassessment13531.core.util.UiState
import com.app.neostoreassessment13531.neostore.domain.repo_user.dummyUserDataModel
import com.app.neostoreassessment13531.neostore.presentation.user_list.component.UserListItem
import com.app.neostoreassessment13531.neostore.presentation.user_list.state.StateUserList
import com.app.neostoreassessment13531.neostore.presentation.user_list.viewmodel.UserListViewModel

@Composable
fun UiScreenUsersList(
    viewModel: UserListViewModel = hiltViewModel()
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    UiUserList(uiState){
        navController.navigate(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiUserList(
    uiState : StateUserList,
    toNavigate:(Route) -> Unit
) {
    Scaffold(topBar = {
        CustomToolbar("User List"){}
    }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.state.screenState == ScreenState.None){
                LazyColumn{
                    items(items = uiState.usersList?.toList() ?: listOf()){
                        UserListItem(it)
                    }
                }
            }else{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).size(50.dp))
            }
            
            FloatingActionButton(
                modifier = Modifier.padding(30.dp).align(Alignment.BottomEnd),
                onClick = {},
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    contentDescription = "" ,  imageVector = Icons.Filled.AddCircle
                )
            }
        }

    }
}

@Preview
@Composable
private fun UiUserListPreview() {
    AppTheme(dynamicColor = false) {
        UiUserList(
            uiState = StateUserList(
                usersList = arrayListOf(dummyUserDataModel , dummyUserDataModel),
                state = UiState(screenState = ScreenState.None)
            ),
            toNavigate = { _-> },
        )
    }
}
