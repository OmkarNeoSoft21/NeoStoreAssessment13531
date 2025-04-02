package com.app.neostoreassessment13531.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


@Composable
fun VerticalSpacer(dp: Dp){
    Spacer(modifier = Modifier.padding(vertical= dp))
}

@Composable
fun Spacer(pd: PaddingValues){
    Spacer(Modifier.padding(pd))
}

@Composable
fun TopSpacer(dp: Dp){
    Spacer(modifier = Modifier.padding(top = dp))
}

@Composable
fun BottomSpacer(dp: Dp){
    Spacer(modifier = Modifier.padding(bottom = dp))
}

@Composable
fun StartSpacer(dp: Dp){
    Spacer(modifier = Modifier.padding(start = dp))
}

@Composable
fun EndSpacer(dp: Dp){
    Spacer(modifier = Modifier.padding(end = dp))
}

