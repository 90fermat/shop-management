package com.foyangtech.shop_management.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.common.extensions.smallSpacer
import com.foyangtech.shop_management.common.extensions.toolbarActions
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.ui.components.ActionToolbar
import com.foyangtech.shop_management.ui.components.CardShop
import com.foyangtech.shop_management.ui.components.ConfirmDeleteDialog
import com.foyangtech.shop_management.ui.components.ContextMenuItem
import com.foyangtech.shop_management.ui.components.DialogCancelButton
import com.foyangtech.shop_management.ui.components.DialogConfirmButton
import com.foyangtech.shop_management.ui.components.DropdownSelector
import com.foyangtech.shop_management.util.Currencies
import com.foyangtech.shop_management.R.string as AppText

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by remember  { viewModel.uiState }
    val showAddShopDialog = remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }
    val showConfirmDeleteDialog = remember { mutableStateOf(false) }

    val shops = viewModel.shops.collectAsStateWithLifecycle(emptyList())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddShopDialog.value = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Add, null) }
        },
        topBar = {
            ActionToolbar(
                title = AppText.shops_title,
                modifier = Modifier.toolbarActions(),
                endActionIcon = R.drawable.ic_settings,
                endAction = { viewModel.onSettingsClick(openScreen) }
            )
        }
    ) {

        Spacer(modifier = Modifier.smallSpacer())

        ShopList(list = shops, Modifier.padding(it), viewModel,
            showUpdateDialog, showConfirmDeleteDialog, openScreen)

    }

    if (showAddShopDialog.value) {
        ShopDialog(
            showDialog = showAddShopDialog,
            title = AppText.add_shop_dialog_title,
            name = uiState.showDialogShopName ,
            description = uiState.showDialogShopDescription,
            currency = uiState.showDialogShopCurrency,
            onNameChange = { name -> viewModel.onShopNameChange(name) },
            onDescriptionChange = { value -> viewModel.onShopDescriptionChange(value) },
            onCurrencyChange = { value -> viewModel.onShopCurrencyChange(value) },
            okAction = { viewModel.addShop()},
            action = AppText.add_confirm
        )
    }

    if (showUpdateDialog.value) {

        val toUpdateShop by viewModel.currentSelectedShop

        ShopDialog(
            showDialog = showUpdateDialog,
            title = AppText.update_shop_dialog_title,
            name = uiState.updatedShopName ,
            description = uiState.updatedShopDescription,
            currency = uiState.updatedShopCurrency,
            onNameChange = { name -> viewModel.onUpdateShopNameChange(name) },
            onDescriptionChange = { value -> viewModel.onUpdateShopDescriptionChange(value) },
            onCurrencyChange = { value -> viewModel.onUpdateShopCurrencyChange(value) },
            okAction = { viewModel.updateShop(toUpdateShop) },
            action = AppText.update
        )
    }

    if (showConfirmDeleteDialog.value) {

        val toDeleteShop by viewModel.currentSelectedShop

        ConfirmDeleteDialog(
            showDialog = showConfirmDeleteDialog,
            text = toDeleteShop.name
        ) {
            viewModel.deleteShop(toDeleteShop.id)
        }
    }
}

@Composable
private fun ShopList(
    list: State<List<Shop>>,
    modifier: Modifier,
    viewModel: HomeViewModel,
    showUpdateDialog: MutableState<Boolean>,
    showDeleteDialog: MutableState<Boolean>,
    openScreen: (String) -> Unit
) {

    if (list.value.isEmpty())    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = stringResource(id = AppText.no_shop_text),
                style = MaterialTheme.typography.bodyLarge)
        }
    }
    else LazyColumn(modifier = modifier) {
        items(list.value, { it.id }) {
            CardShop(
                shop = it,
                onClickAction = { viewModel.onShopCardClick(it.id, openScreen) },
                menuItems = listOf(
                    ContextMenuItem.UpdateMenuItem(
                        action = {
                            viewModel.onUpdateMenuClick(it)
                            showUpdateDialog.value = true
                        }
                    ),
                    ContextMenuItem.DeleteMenuItem(
                        action = {
                            viewModel.onDeleteMenuClick(it)
                            showDeleteDialog.value = true
                        }
                    )
                )

            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShopDialog(showDialog: MutableState<Boolean>,
                       title: Int,
                       name: String,
                       description: String,
                       currency: String,
                       onNameChange: (String) -> Unit,
                       onDescriptionChange: (String) -> Unit,
                       onCurrencyChange: (String) -> Unit,
                       okAction: () -> Unit,
                       action: Int
) {

    AlertDialog(
        title =  { Text(text = stringResource(id = title)) },
        dismissButton = { DialogCancelButton(AppText.cancel) { showDialog.value = false } },
        confirmButton = {
            DialogConfirmButton(text = action) {
                showDialog.value = false
                okAction()
            }
        },
        onDismissRequest = { showDialog.value = false },
        text = {
            Column {
                OutlinedTextField(value = name,
                    onValueChange = onNameChange ,
                    label = { Text(text = stringResource(id = AppText.shop_name_dialog))}
                )
                OutlinedTextField(value = description,
                    onValueChange = onDescriptionChange,
                    label = { Text(text = stringResource(id = AppText.shop_description_dialog))}
                )
                DropdownSelector(label = R.string.shop_currency_dialog,
                    options = Currencies.values().map { it.name },
                    selection = currency, modifier = Modifier,
                    onNewValue = onCurrencyChange)
            }
        }
    )
}

/*@Composable
@Preview
fun HomePreview() {
    ShopList( remember {
        mutableStateOf(listOf(
            Shop(name = "MaMi", description = "MaMi shop"),
            Shop(name = "Boutique Archives", description = "Buy Archives")))
    },
        Modifier.padding(2.dp), null
    ) { actionPreview() }
}*/
fun actionPreview() {
}

/*
@Composable
@Preview
fun CardShopPreview() {
    CardShop(Shop("Test", "My Test Shop, show like a Angel")) { actionPreview() }
}*/
