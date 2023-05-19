package com.foyangtech.shop_management.ui.components


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.common.extensions.dropdownSelector
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.util.Currencies


@Composable
@ExperimentalMaterial3Api
fun DropdownContextMenu(
  options: List<String>,
  modifier: Modifier,
  onActionClick: (String) -> Unit
) {
  var isExpanded by remember { mutableStateOf(false) }

  ExposedDropdownMenuBox(
    expanded = isExpanded,
    modifier = modifier,
    onExpandedChange = { isExpanded = !isExpanded }
  ) {
    Icon(
      modifier = Modifier.padding(8.dp, 0.dp),
      imageVector = Icons.Default.MoreVert,
      contentDescription = "More"
    )

    ExposedDropdownMenu(
      modifier = Modifier.width(180.dp),
      expanded = isExpanded,
      onDismissRequest = { isExpanded = false }
    ) {
      options.forEach { selectionOption ->
        DropdownMenuItem(
          onClick = {
            onActionClick(selectionOption)
            isExpanded = false
          },
          text =  {
            Text(text = selectionOption)
          }
        )
      }
    }
  }
}

@Composable
@ExperimentalMaterial3Api
fun DropdownSelector(
  @StringRes label: Int,
  options: List<String>,
  selection: String,
  modifier: Modifier,
  onNewValue: (String) -> Unit
) {
  var isExpanded by remember { mutableStateOf(false) }

  ExposedDropdownMenuBox(
    expanded = isExpanded,
    modifier = modifier,
    onExpandedChange = { isExpanded = !isExpanded }
  ) {
    OutlinedTextField(
      modifier = Modifier.menuAnchor(),
      readOnly = true,
      value = selection,
      onValueChange = {},
      label = { Text(stringResource(label)) },
      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
      colors = ExposedDropdownMenuDefaults.textFieldColors()
    )

    ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
      options.forEach { selectionOption ->
        DropdownMenuItem(
          onClick = {
            onNewValue(selectionOption)
            isExpanded = false
          },
          text =  {
            Text(text = selectionOption)
          }
        )
      }
    }
  }
}

/*@Composable
@ExperimentalMaterial3Api
private fun dropdownColors(): TextFieldColors {
  return ExposedDropdownMenuDefaults.textFieldColors(
    containerColor =  MaterialTheme.colorScheme.surface,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.primary
  )
}*/

@Composable
fun ContextMenuCard(
  showMenu: Boolean,
  menuItems: List<Int>,
  onClickCallbacks: List<() -> Unit>,
  onDismiss: () -> Unit
) {

  DropdownMenu(expanded = showMenu, onDismissRequest = onDismiss) {
    menuItems.forEachIndexed { index, item ->
      DropdownMenuItem(
        text = {  Text(stringResource(id = item)) },
        onClick = {
          onClickCallbacks[index]()
          onDismiss()
        }
      )
      if (index < menuItems.size - 1) Divider()
    }
  }
}

/*@Composable
@Preview(showBackground = true,)
fun ContextMenuCardPreview() {
  var showMenu by remember { mutableStateOf(false) }
  val onDismiss = { showMenu = !showMenu }
  Box(modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.TopStart)){
    IconButton(onClick = { showMenu = true }) {
      Icon(
        Icons.Default.MoreVert,
        contentDescription = "Localized description"
      )
    }
    ContextMenuCard(
      showMenu, listOf(, "Delete"),
      listOf({}), onDismiss
    )
  }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DropDownSeletorPreview() {
  DropdownSelector(label = R.string.shop_currency_dialog,
    options = listOf("EUR", "CFA", "DOLLAR"),
    selection = "", modifier = Modifier.dropdownSelector(),
    onNewValue = {  })
}
