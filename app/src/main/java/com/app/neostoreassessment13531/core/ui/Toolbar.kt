
package com.app.neostoreassessment13531.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.neostoreassessment13531.core.LocalNavController
import com.app.neostoreassessment13531.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    title: String,
    isBackArrow:Boolean = false,
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    ),
    onBackClick:(()->Unit) ?= null
) {
    val navController = LocalNavController.current
    TopAppBar(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .shadow(elevation = 5.dp),
        colors = topAppBarColors,
        navigationIcon = {
            AnimatedVisibility(isBackArrow){
                Icon(
                    modifier = Modifier.clickable {
                        onBackClick?.invoke() ?: run {
                            navController.navigateUp()
                        }
                    },
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = topAppBarColors.titleContentColor
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CustomToolbarPreview() {
    AppTheme {
        CustomToolbar(
            title = "Toolbar",
        )
    }
}
