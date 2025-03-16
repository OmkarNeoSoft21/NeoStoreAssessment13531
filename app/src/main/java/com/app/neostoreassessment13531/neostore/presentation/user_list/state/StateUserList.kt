package com.app.neostoreassessment13531.neostore.presentation.user_list.state

import com.app.neostoreassessment13531.neostore.domain.repo_user.UserDataModel
import com.app.neostoreassessment13531.core.util.UiState

data class StateUserList(
    val usersList : ArrayList<UserDataModel> ?= null,
    val state: UiState
)