package com.foyangtech.shop_management.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.foyangtech.shop_management.R
import com.foyangtech.shop_management.common.extensions.dropdownSelector
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardShop(shop: Shop, action: () -> Unit) {
  ElevatedCard(
    onClick = action,
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    elevation = CardDefaults.elevatedCardElevation(8.dp, 6.dp, 9.dp, 7.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = shop.name, style = MaterialTheme.typography.headlineLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = shop.description, style = MaterialTheme.typography.bodyLarge)
      Spacer(modifier = Modifier.height(8.dp))
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardShop2(shop: Shop, action: () -> Unit) {
  ElevatedCard(
    onClick = action,
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    elevation = CardDefaults.elevatedCardElevation(8.dp, 6.dp, 9.dp, 7.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Image(painter = painterResource(id = R.drawable.shop_logo_management),
        contentDescription = "Shop Management Logo",
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        contentScale = ContentScale.Inside
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = shop.name, style = MaterialTheme.typography.headlineLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = shop.description, style = MaterialTheme.typography.bodyLarge)
    }
  }
}
