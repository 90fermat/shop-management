package com.foyangtech.shop_management.ui.screens.shop.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopHomeScreen(
    shopId: String,
    viewModel: ShopHomeViewModel = hiltViewModel()
) {
    val shop by viewModel.shop
    val products by viewModel.products

    LaunchedEffect(Unit) {
        viewModel.initialize(shopId)
    }

    ShopHome(shop, products)

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ShopHome(shop: Shop, products: Flow<List<Product>>) {

    val listState = productFlow(products)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        header(shop, this)

        productInfos(this, listState)

    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun header(shop: Shop, lazyListScope: LazyListScope) {
    lazyListScope.item {
        Text(text = "SHOP N°-${shop.id}",
            style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(5.dp))

        Image(painter = painterResource(id = R.drawable.shop_local),
            contentDescription = "Shop Logo",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(8.dp))

        DateCalendar()

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Shop Infos",
            fontWeight = FontWeight.W800,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        ShopInfo(shop)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Product Infos",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateCalendar() {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier.size(50.dp),
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Today Date",
            
        )

        Text(
            text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd")),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,

        )
    }
}

@Composable
fun ShopInfo(shop: Shop) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column() {
            DisplayProperty(
                property = stringResource(id = R.string.shop_name),
                value = shop.name
            )
            Spacer(modifier = Modifier.height(5.dp))
            DisplayLongProperty(
                property = stringResource(id = R.string.shop_description),
                value = shop.description
            )
            Spacer(modifier = Modifier.height(5.dp))
            DisplayProperty(
                property = stringResource(id = R.string.shop_currency),
                value = shop.currency
            )
        }
    }
}
@Composable
fun DisplayProperty(property: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        Arrangement.SpaceBetween
    ) {
        Text(
            text = "$property:",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = value,
            fontWeight = FontWeight.Thin,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
@Composable
fun DisplayLongProperty(property: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        Arrangement.SpaceBetween
    ) {
        Text(
            text = "$property:",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = value,
            fontWeight = FontWeight.Thin,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}


private fun productInfos(
    lazyListScope: LazyListScope,
    listState: State<List<Product>>
) {
    lazyListScope.item {
        Card(modifier = Modifier.padding(16.dp)) {
            if (listState.value.isEmpty()) {
                lazyListScope.item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Filled.FilterNone,
                            modifier = Modifier.size(100.dp),
                            contentDescription = null
                        )
                        Text(text = "NO INFOS", style = MaterialTheme.typography.headlineLarge)
                    }
                }
            } else {
                lazyListScope.items(listState.value) {
                    if (it.stockInShop <= 6)
                        Text(text = stringResource(id = R.string.sold_out_info, it.name, it.stockInShop))
                    else
                        Text(text = stringResource(id = R.string.still_enough_info, it.name, it.stockInShop))

                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun productFlow(products: Flow<List<Product>>): Flow<List<Product>> {
    return products.flatMapLatest {
        flowOf( it.filter { product ->
            product.stockInShop <= 6 || product.stockInShop >= 35
        })
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ShopHomeScreenPreview() {
    val p1 = Product("1", "Bste", stockInShop = 2.0)
    val p2 = Product("2", "Stone", stockInShop = 37.0)
    val p3 = Product("3", "Nixon", stockInShop = 22.0)
    val shop = Shop("zwe63", "My Best Shop",
        "I new see a shop like this", currency = "DOLLAR")
    val flowList = flowOf(listOf(p1, p2, p3))
    ShopHome(shop, flowList)
}