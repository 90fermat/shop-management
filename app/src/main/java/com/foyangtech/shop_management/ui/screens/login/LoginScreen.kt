package com.foyangtech.shop_management.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.foyangtech.shop_management.ui.components.BasicButton
import com.foyangtech.shop_management.ui.components.BasicTextButton
import com.foyangtech.shop_management.R.string as AppText
import com.foyangtech.shop_management.common.extensions.basicButton
import com.foyangtech.shop_management.common.extensions.fieldModifier
import com.foyangtech.shop_management.common.extensions.textButton
import com.foyangtech.shop_management.ui.components.BasicToolbar
import com.foyangtech.shop_management.ui.components.EmailField
import com.foyangtech.shop_management.ui.components.PasswordField

@Composable
fun LoginScreen(
  openAndPopUp: (String, String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  BasicToolbar(AppText.login_details)

  Column(
    modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
    PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

    BasicButton(AppText.sign_in, Modifier.basicButton()) { viewModel.onSignInClick(openAndPopUp) }

    BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
      viewModel.onForgotPasswordClick()
    }
  }
}
