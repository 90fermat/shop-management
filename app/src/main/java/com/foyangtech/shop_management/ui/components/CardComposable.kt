package com.foyangtech.shop_management.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.common.extensions.dropdownSelector
import com.foyangtech.shop_management.model.Product
import com.foyangtech.shop_management.model.Shop

@ExperimentalMaterial3Api
@Composable
fun DangerousCardEditor(
  @StringRes title: Int,
  @DrawableRes icon: Int,
  content: String,
  modifier: Modifier,
  onEditClick: () -> Unit
) {
  CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.primary, modifier)
}

@ExperimentalMaterial3Api
@Composable
fun RegularCardEditor(
  @StringRes title: Int,
  @DrawableRes icon: Int,
  content: String,
  modifier: Modifier,
  onEditClick: () -> Unit
) {
  CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.onSurface, modifier)
}

@ExperimentalMaterial3Api
@Composable
private fun CardEditor(
  @StringRes title: Int,
  @DrawableRes icon: Int,
  content: String,
  onEditClick: () -> Unit,
  highlightColor: Color,
  modifier: Modifier
) {
  Card(
    modifier = modifier,
    onClick = onEditClick
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

      if (content.isNotBlank()) {
        Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
      }

      Icon(painter = painterResource(icon), contentDescription = "Icon", tint = highlightColor)
    }
  }
}

@Composable
@ExperimentalMaterial3Api
fun CardSelector(
  @StringRes label: Int,
  options: List<String>,
  selection: String,
  modifier: Modifier,
  onNewValue: (String) -> Unit
) {
  Card(modifier = modifier) {
    DropdownSelector(label, options, selection, Modifier.dropdownSelector(), onNewValue)
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardShop(
  shop: Shop,
  onClickAction: () -> Unit,
  menuItems: List<ContextMenuItem>
) {
  var showMenu by remember { mutableStateOf(false) }
  val onDismiss = { showMenu = !showMenu }

  ElevatedCard(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
      .pointerInput(Unit) {
        detectTapGestures (
          onTap = { onClickAction() },
          onLongPress = { showMenu = true }
        )
      },
    elevation = CardDefaults.elevatedCardElevation(8.dp, 6.dp, 9.dp, 7.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = shop.name, style = MaterialTheme.typography.headlineLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = shop.description, style = MaterialTheme.typography.bodyLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopCenter)) {
        ContextMenuCard(
          showMenu = showMenu,
          menuItems = menuItems,
        ) {
          onDismiss()
        }
      }
      Image(painter = painterResource(id = R.drawable.shop_logo_management),
        contentDescription = "Shop Management Logo",
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        contentScale = ContentScale.Inside
      )
    }
  }
}
@Composable
fun CardProduct(
  product: Product,
  onClickAction: () -> Unit,
  menuItems: List<ContextMenuItem>,
  currency: String,
) {
  var showMenu by remember { mutableStateOf(false) }
  val onDismiss = { showMenu = !showMenu }

  Card(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
      .pointerInput(Unit) {
        detectTapGestures (
          onTap = { onClickAction() },
          onLongPress = { showMenu = true }
        )
      },
    elevation = CardDefaults.cardElevation(8.dp, 6.dp, 9.dp, 7.dp)
  ) {
    Row(modifier = Modifier.padding(16.dp)) {
      Image(painter = painterResource(id = R.drawable.shop_logo_management),
        contentDescription = "Shop Management Logo",
        modifier = Modifier
          .height(100.dp),
        contentScale = ContentScale.Inside
      )

      Box(modifier = Modifier.wrapContentSize(Alignment.TopCenter)) {
        ContextMenuCard(
          showMenu = showMenu,
          menuItems = menuItems,
          ) {
          onDismiss()
        }
      }
      Spacer(modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp))

      Column(modifier = Modifier.padding(8.dp)) {
        Text(text = product.name, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${product.stockInShop} ${product.unit}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${product.shopPrice} $currency", style = MaterialTheme.typography.bodyMedium)
      }
    }
  }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardProductPreview() {
  CardProduct(product = Product(name = "Test Product", stockInShop = 8.0, shopPrice = 3000.0),
    action = { */
/*TODO*//*
 }, currency = "FCFA")
}*/
