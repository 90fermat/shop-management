package com.foyangtech.shop_management.ui.components


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.foyangtech.shop_management.R.drawable as AppIcon
import com.foyangtech.shop_management.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicField(
  @StringRes text: Int,
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    placeholder = { Text(stringResource(text)) }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
  OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    placeholder = { Text(stringResource(AppText.email)) },
    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
  )
}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
  PasswordField(value, AppText.password, onNewValue, modifier)
}

@Composable
fun RepeatPasswordField(
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  PasswordField(value, AppText.repeat_password, onNewValue, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordField(
  value: String,
  @StringRes placeholder: Int,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var isVisible by remember { mutableStateOf(false) }

  val icon =
    if (isVisible) painterResource(AppIcon.ic_visibility_on)
    else painterResource(AppIcon.ic_visibility_off)

  val visualTransformation =
    if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

  OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    placeholder = { Text(text = stringResource(placeholder)) },
    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
    trailingIcon = {
      IconButton(onClick = { isVisible = !isVisible }) {
        Icon(painter = icon, contentDescription = "Visibility")
      }
    },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    visualTransformation = visualTransformation
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(state: MutableState<TextFieldValue>, modifier: Modifier) {
  OutlinedTextField(
    modifier = modifier,
    value = state.value,
    onValueChange = {value -> state.value = value },
    leadingIcon = {
      Icon(
        Icons.Default.Search,
        contentDescription = null,
        modifier = Modifier
          .padding(15.dp)
          .size(24.dp)
      )
    },
    trailingIcon = {
      if (state.value != TextFieldValue("")) {
        IconButton(onClick = { state.value = TextFieldValue("") }) {
          Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
              .padding(15.dp)
              .size(24.dp)
          )
        }
      }
    },
    placeholder = {
      Text(
        text = stringResource(id = AppText.search_placeholder),
      fontStyle = FontStyle.Italic)
    }
  )
}

@Preview
@Composable
fun SearchTextPreview() {
  var state = remember { mutableStateOf(TextFieldValue("")) }
  SearchTextField(state = state, Modifier.fillMaxWidth())
}
