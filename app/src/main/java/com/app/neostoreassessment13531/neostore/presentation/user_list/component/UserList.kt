package com.app.neostoreassessment13531.neostore.presentation.user_list.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import androidx.core.net.toUri
import com.app.neostoreassessment13531.core.animation.customBoundsTransform

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserListItem(
    model: UserDataModel,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    with(sharedTransitionScope) {
        ElevatedCard(
            modifier = modifier
                .padding(10.dp)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.surfaceContainer)
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
                    modifier = Modifier
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(key = "image-${model.id}"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = customBoundsTransform
                        )
                        .size(100.dp)
                        .clip(RoundedCornerShape(10))
                    ,
                    painter = if (!model.imageUri.isEmpty())
                        rememberAsyncImagePainter(model.imageUri.toUri())
                    else
                        rememberVectorPainter(Icons.Filled.AccountCircle),
                    contentScale = ContentScale.Crop,
                    contentDescription = "UserImage"
                )
                Column(Modifier.fillMaxWidth(0.85f)) {
                    Text(
                        modifier = Modifier.sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(key = "name-${model.id}"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = customBoundsTransform
                        ),
                        text = "${model.firstName} ${model.lastName}",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.secondary)
                    )
                    Text(
                        modifier = Modifier.sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(key = "designation-${model.id}"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = customBoundsTransform
                        ),
                        text = model.professional?.designation ?: "",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = ""
                )

            }
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

    }
}
