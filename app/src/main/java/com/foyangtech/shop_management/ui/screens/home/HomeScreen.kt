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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.common.extensions.dropdownSelector
import com.foyangtech.shop_management.common.extensions.smallSpacer
import com.foyangtech.shop_management.common.extensions.toolbarActions
import com.foyangtech.shop_management.R.string as AppText
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.ui.components.ActionToolbar
import com.foyangtech.shop_management.ui.components.CardShop
import com.foyangtech.shop_management.ui.components.CardShop2
import com.foyangtech.shop_management.ui.components.DialogCancelButton
import com.foyangtech.shop_management.ui.components.DialogConfirmButton
import com.foyangtech.shop_management.ui.components.DropdownSelector
import com.foyangtech.shop_management.util.Currencies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by remember  { viewModel.uiState }
    var showAddShopDialog by remember { mutableStateOf(false) }
    val shops = viewModel.shops.collectAsStateWithLifecycle(initialValue = emptyList())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddShopDialog = true },
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

        ShopList(list = shops, Modifier.padding(it), viewModel, openScreen)

        /*Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        }*/
    }

    if (showAddShopDialog) {
        AlertDialog(
            title =  { Text(text = stringResource(id = AppText.add_shop_dialog_title)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showAddShopDialog = false } },
            confirmButton = {
                DialogConfirmButton(text = AppText.add_shop_confirm) {
                    showAddShopDialog = false
                    viewModel.addShop()
                }
            },
            onDismissRequest = { showAddShopDialog = false },
            text = {
                Column {
                    OutlinedTextField(value = uiState.showDialogShopName,
                        onValueChange = { viewModel.onShopNameChange(it)},
                        label = { Text(text = stringResource(id = AppText.shop_name_dialog))}
                    )
                    OutlinedTextField(value = uiState.showDialogShopDescription,
                        onValueChange = { viewModel.onShopDescriptionChange(it)},
                        label = { Text(text = stringResource(id = AppText.shop_description_dialog))}
                    )
                    DropdownSelector(label = R.string.shop_currency_dialog,
                        options = Currencies.values().map { it.name },
                        selection = "", modifier = Modifier.dropdownSelector(),
                        onNewValue = { viewModel.onShopCurrencyChange(it) })
                }
            }
        )
    }
}

@Composable
private fun ShopList(list: State<List<Shop>>, modifier: Modifier,
                     viewModel: HomeViewModel?, openScreen: (String) -> Unit) {

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
            CardShop(it) {
               viewModel?.onShopCardClick(it.id, openScreen)
            }
        }
    }

}

@Composable
@Preview
fun HomePreview() {
    ShopList( remember {
        mutableStateOf(listOf(
            Shop(name = "MaMi", description = "MaMi shop"),
            Shop(name = "Boutique Archives", description = "Buy Archives")))
    },
        Modifier.padding(2.dp), null
    ) { actionPreview() }
}
fun actionPreview() {
}

@Composable
@Preview
fun CardShopPreview() {
    CardShop2(Shop("Test", "My Test Shop, show like a Angel")) { actionPreview() }
}