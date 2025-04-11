package com.amasmobile.jet_a_reader.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EmailTextField(focusManager: FocusManager,
                   emailState: MutableState<String>
){


    OutlinedTextField(

        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(16.dp),

        value =  emailState.value,
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        placeholder = {
            Text("Email", style = TextStyle(color = Color.LightGray))
        },
        onValueChange = {
                enteredValue ->
            emailState.value = enteredValue
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down) // Or Right, Left, Up
            }
        )

    )

}


@Composable
fun PasswordTextField(
    passwordState: MutableState<String>,
    focusManager: FocusManager,
    imeAction: ImeAction = ImeAction.Next
){

    var isHidden by remember { mutableStateOf(true) }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(16.dp),

        value = passwordState.value,
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        onValueChange = {
                enteredValue ->
            passwordState.value = enteredValue
        },
        placeholder = {
            Text("Password",
                style = TextStyle(
                    color = Color.LightGray
                )
            )
        },

        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                //TODO as login pressed

                keyboardController?.hide()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        visualTransformation = if(isHidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if(isHidden)
                IconButton(
                    onClick = {
                        isHidden = false
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = "Visible")
                }
            else {
                IconButton(
                    onClick = {
                        isHidden = true
                    }
                ) {
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "Invisible")
                }
            }
        }

    )
}