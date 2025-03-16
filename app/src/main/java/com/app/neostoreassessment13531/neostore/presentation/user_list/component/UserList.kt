package com.app.neostoreassessment13531.neostore.presentation.user_list.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.neostore.domain.repo_user.UserDataModel

@Composable
fun UserListItem(
    model : UserDataModel
) {
    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "UserImage"
            )
            Column(Modifier.fillMaxWidth(0.85f)) {
                Text(text = model.profession,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "${model.firstName} ${model.lastName}" ,
                    style = MaterialTheme.typography.labelLarge.copy(MaterialTheme.colorScheme.secondary)
                )
            }
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "")

        }
    }

}

@Preview(
    widthDp = 320,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun UserListPreview() {
    AppTheme(dynamicColor = false) {
        UserListItem(
            model = UserDataModel(
                firstName = "Omkar",
                lastName = "Sawant",
                profession = "Senior Software Engineer",
                phoneNumber = "8779876543",
                experience = "5.9",
                email = "okmbvfg@dfbnm.coc",
                gender = "Male",
            )
        )
    }
}
