package com.foyangtech.shop_management.ui.screens.shop.product.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.common.CustomValidable
import tech.devscast.validable.NotEmptyValidable
import tech.devscast.validable.withValidable


@Composable
fun EditProductScreen(
   shopId: String,
   productId: String,
   popScreen: () -> Unit,
   viewModel: EditProductViewModel = hiltViewModel()
) {
   LaunchedEffect(Unit) { viewModel.initialize(shopId, productId)}
   Form(shopId = shopId, popScreen, viewModel = viewModel)
}

@Composable
private fun Form(
   shopId: String,
   popScreen: () -> Unit,
   viewModel: EditProductViewModel
) {

   val uiState by viewModel.uiState


   val name = remember { NotEmptyValidable() }
   val price = remember { CustomValidable(validDoubleField()) }
   val shopPrice = remember { CustomValidable(validDoubleField()) }
   val stock = remember { CustomValidable(validDoubleField()) }
   val unit = remember { NotEmptyValidable() }

   name.value = uiState.name
   price.value = uiState.price
   shopPrice.value = uiState.shopPrice
   stock.value  = uiState.stock
   unit.value = uiState.unit

   Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .padding(24.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
   ) {

      Text(
         text = "${stringResource(id = R.string.update)} '${uiState.name}'",
         color = MaterialTheme.colorScheme.primary,
         style = MaterialTheme.typography.titleLarge
      )

      Spacer(modifier = Modifier.height(50.dp))

      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         value = uiState.name,
         onValueChange = {
            name.value = it
            viewModel.onNameChange(it)
          },
         label = { Text(text = stringResource(id = R.string.shop_name_dialog)) },
         isError = name.hasError(),
         shape = RoundedCornerShape(100)
      )
      AnimatedVisibility(visible = name.hasError()) {
         Text(
            text = name.errorMessage  ?: "",
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(MaterialTheme.colorScheme.error)
         )
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         value = uiState.price,
         onValueChange = {
            price.value = it
            viewModel.onPriceChange(it)
                         },
         label = { Text(text = stringResource(id = R.string.price)) },
         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
         isError = price.hasError(),
         shape = RoundedCornerShape(100)
      )
      AnimatedVisibility(visible = price.hasError()) {
         Text(
            text = price.errorMessage  ?: "",
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(MaterialTheme.colorScheme.error)
         )
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         value = uiState.shopPrice,
         onValueChange = {
            shopPrice.value = it
            viewModel.onShopPriceChange(it)
                         },
         label = { Text(text = stringResource(id = R.string.shop_price)) },
         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
         isError = shopPrice.hasError(),
         shape = RoundedCornerShape(100)
      )
      AnimatedVisibility(visible = shopPrice.hasError()) {
         Text(
            text = shopPrice.errorMessage  ?: "",
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(MaterialTheme.colorScheme.error)
         )
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         value = uiState.stock,
         onValueChange = {
            stock.value = it
            viewModel.onStockChange(it)
                         },
         label = { Text(text = stringResource(id = R.string.stock)) },
         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
         isError = stock.hasError(),
         shape = RoundedCornerShape(100)
      )
      AnimatedVisibility(visible = stock.hasError()) {
         Text(
            text = stock.errorMessage  ?: "",
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(MaterialTheme.colorScheme.error)
         )
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
         modifier = Modifier.fillMaxWidth(),
         value = uiState.unit,
         onValueChange = {
            unit.value = it
            viewModel.onUnitChange(it)
                         },
         label = { Text(text = stringResource(id = R.string.unit)) },
         isError = unit.hasError(),
         shape = RoundedCornerShape(100)
      )
      AnimatedVisibility(visible = unit.hasError()) {
         Text(
            text = unit.errorMessage  ?: "",
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(MaterialTheme.colorScheme.error)
         )
      }

      Spacer(modifier = Modifier.height(50.dp))

      Button(
         onClick = {
             withValidable(name, price, shopPrice, stock, unit) {
                viewModel.updateState(uiState.name, uiState.price,
                   uiState.shopPrice, uiState.stock, uiState.unit)
                viewModel.updateProduct(shopId, popScreen)
             }
         },
         modifier = Modifier.fillMaxWidth(),
         shape = RoundedCornerShape(100)
      ) {
         Text(text = stringResource(id = R.string.update))
      }
   }

}

private fun validDoubleField() :  (String) -> Boolean = {
   it.toDoubleOrNull()?.isFinite() ?: false
}

/*@Composable
@Preview
fun FormPreview() {
   Form(shopId = "", popScreen = { }, viewModel = null)
}*/
