package com.app.neostoreassessment13531.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties


@Composable
fun CustomDropDown(
    arrString: ArrayList<String>,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText:String,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(topEnd = 20.dp, bottomStart = 20.dp)
                )
                .padding(horizontal = 10.dp)
                .clickable {
                    isExpanded = true
                }
        ) {
            if (value.isEmpty())
                CustomPlaceHolder(placeHolderText)
            else
                Text(text = value , modifier = Modifier.fillMaxWidth(0.7f))
            Image(
                painter = rememberVectorPainter(Icons.Rounded.ArrowDropDown),
                contentDescription = "DropDown Icon"
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            properties = PopupProperties(
                clippingEnabled = true
            )
        ) {
            arrString.forEach {
                DropdownMenuItem(text = {
                    Text(text = it)
                }, onClick = {
                    isExpanded = false
                    onValueChange(it)
                })
            }
        }
    }

}