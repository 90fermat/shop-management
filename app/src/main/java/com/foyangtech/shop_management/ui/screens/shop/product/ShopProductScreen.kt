package com.foyangtech.shop_management.ui.screens.shop.product

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import com.foyangtech.shop_management.ui.components.CardProduct
import com.foyangtech.shop_management.ui.components.DialogCancelButton
import com.foyangtech.shop_management.ui.components.DialogConfirmButton
import com.foyangtech.shop_management.ui.components.SearchTextField
import java.util.Locale
import com.foyangtech.shop_management.R.string as AppText

@Composable
fun ShopProductScreen(
    shopId: String,
    openScreen: (String) -> Unit,
    viewModel: ShopProductViewModel = hiltViewModel()
) {
    val shop by viewModel.shop

    LaunchedEffect(Unit) {
       viewModel.initialize(shopId)
    }

    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    val showAddProductDialog = remember { mutableStateOf(false) }
    val showConfirmDeleteDialog = remember { mutableStateOf(false) }

    val products = viewModel.getProducts(shopId).collectAsStateWithLifecycle(initialValue = emptyList())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddProductDialog.value = true}) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it),

            ) {
            SearchTextField(state = searchState, Modifier.fillMaxWidth())
            ProductList(searchState, products, shop, openScreen, showConfirmDeleteDialog, viewModel)
        }
    }

    if (showAddProductDialog.value) AddProductDialog(showDialog = showAddProductDialog, viewModel)

    if (showConfirmDeleteDialog.value) {
        val toDeleteProduct by viewModel.currentToDeleteProduct
        ConfirmDeleteDialog(
            showDialog = showConfirmDeleteDialog,
            text = toDeleteProduct.name
        ) {
            viewModel.confirmDelete(toDeleteProduct.id)
        }
    }
}

@Composable
private fun ProductList(
    searchedProductState: MutableState<TextFieldValue>,
    stateList: State<List<Product>>,
    shop: Shop,
    openScreen: (String) -> Unit,
    showDialog: MutableState<Boolean>,
    viewModel: ShopProductViewModel
) {
    val filteredListState = remember { mutableStateOf(listOf<Product>()) }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        if (searchedProductState.value.text != "") {
            filteredListState.value = stateList.value
                .filter { it.name.lowercase(Locale.getDefault())
                    .contains(searchedProductState.value.text) }
        } else  { filteredListState.value = stateList.value }

        if (stateList.value.isEmpty()) item {

        }
        else items(filteredListState.value) {
            CardProduct(
                product = it,
                onClickAction = { /*TODO*/ },
                onMenuClicks = listOf(
                    { viewModel.onUpdateMenuClick(it.id, openScreen) },
                    {
                        viewModel.onDeleteMenuClick(it)
                        showDialog.value = true
                    }
                ),
                currency = shop.currency,
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDialog(showDialog: MutableState<Boolean>,
                       title: Int,
                       name: MutableState<String>,
                       price: MutableState<Double>,
                       shopPrice: MutableState<Double>,
                       stockInShop: MutableState<Double>,
                       unit: MutableState<String>,
                       okAction: () -> Unit,
                       action: Int
) {

    if (showDialog.value) {
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
                    OutlinedTextField(value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text(text = stringResource(id = AppText.shop_name_dialog))}
                    )
                    OutlinedTextField(value = "${price.value}",
                        onValueChange = { price.value = it.toDouble() },
                        label = { Text(text = stringResource(id = AppText.price))}
                    )
                    OutlinedTextField(value = "${shopPrice.value}",
                        onValueChange = { shopPrice.value = it.toDouble() },
                        label = { Text(text = stringResource(id = AppText.shop_price))}
                    )
                    OutlinedTextField(value = "${stockInShop.value}",
                        onValueChange = { stockInShop.value = it.toDouble() },
                        label = { Text(text = stringResource(id =AppText.stock))}
                    ) 
                    OutlinedTextField(value = unit.value,
                        onValueChange = { unit.value = it },
                        label = { Text(text = stringResource(id = AppText.unit))}
                    )
                }
            }
        )
    }
}

@Composable
private fun ConfirmDeleteDialog(
    showDialog: MutableState<Boolean>,
    text: String,
    action: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            title =  { Text(text = stringResource(id = AppText.confirm_delete_title, text)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showDialog.value = false } },
            confirmButton = {
                DialogConfirmButton(text = AppText.delete) {
                    showDialog.value = false
                    action()
                }
            },
            onDismissRequest = { showDialog.value = false },
            text = {
                Text(text = stringResource(id = AppText.confirm_delete_body, text))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddProductDialog(showDialog: MutableState<Boolean>, viewModel: ShopProductViewModel) {
    val name = remember { mutableStateOf("") }
    val price = remember { mutableStateOf(0.0) }
    val shopPrice = remember { mutableStateOf(0.0) }
    val stock = remember { mutableStateOf(0.0) }
    val unit = remember { mutableStateOf("unit") }
    
    ShowDialog(
        showDialog = showDialog,
        title = AppText.add_product,
        name = name,
        price = price,
        shopPrice = shopPrice,
        stockInShop = stock,
        unit = unit,
        okAction = {
            viewModel.saveProduct(
                Product("", name.value, price.value,
                shopPrice.value, stock.value, unit.value)
            )
                   },
        action = AppText.add_confirm
    )
}


