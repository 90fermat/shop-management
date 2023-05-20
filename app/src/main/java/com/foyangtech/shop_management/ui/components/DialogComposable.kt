package com.foyangtech.shop_management.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.foyangtech.shop_management.R


@Composable
fun ConfirmDeleteDialog(
   showDialog: MutableState<Boolean>,
   text: String,
   action: () -> Unit
) {
   if (showDialog.value) {
      AlertDialog(
         title =  { Text(text = stringResource(id = R.string.confirm_delete_title, text)) },
         dismissButton = { DialogCancelButton(R.string.cancel) { showDialog.value = false } },
         confirmButton = {
            DialogConfirmButton(text = R.string.delete) {
               showDialog.value = false
               action()
            }
         },
         onDismissRequest = { showDialog.value = false },
         text = {
            Text(text = stringResource(id = R.string.confirm_delete_body, text))
         }
      )
   }
}