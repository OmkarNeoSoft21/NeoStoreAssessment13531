package com.app.neostoreassessment13531.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun HeadlineTextField(
    modifier: Modifier = Modifier,
    value:String,
    onValueChange:(String)->Unit,
    placeHolderText:String,
    imageVector: ImageVector ?= null,
    paddingValues: PaddingValues= PaddingValues(),
    shape: Shape = RoundedCornerShape(topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
){
    val isKeyboardTypePassword by remember { mutableStateOf(keyboardOptions.keyboardType == KeyboardType.Password) }
    var showPassword by remember { mutableStateOf(false) }

    Text(
        text= placeHolderText,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.primary)
    )
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues),
        value = value ,
        onValueChange = onValueChange,
        visualTransformation = if (isKeyboardTypePassword) {
            if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
        }
        else VisualTransformation.None,
        supportingText = supportingText,
        isError = isError,
        placeholder = {
            CustomPlaceHolder(placeHolderText)
        },
        leadingIcon = {
            if (imageVector != null) {
                Image(
                    imageVector = imageVector,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
        },
        shape = shape,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        trailingIcon = {
            trailingIcon ?: run {
                if (isKeyboardTypePassword){
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun CustomPlaceHolder(placeHolderText:String){
    Text(
        text = placeHolderText,
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )
}