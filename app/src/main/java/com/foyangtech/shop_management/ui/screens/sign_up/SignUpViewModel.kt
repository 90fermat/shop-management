package com.foyangtech.shop_management.ui.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.R.string as AppText
import com.foyangtech.shop_management.util.SETTINGS_SCREEN
import com.foyangtech.shop_management.util.SIGN_UP_SCREEN
import com.foyangtech.shop_management.common.extensions.isValidEmail
import com.foyangtech.shop_management.common.extensions.isValidPassword
import com.foyangtech.shop_management.common.extensions.passwordMatches
import com.foyangtech.shop_management.common.snackbar.SnackbarManager

import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import com.foyangtech.shop_management.model.service.AccountService
import com.foyangtech.shop_management.model.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : ShopManagementViewModel(logService) {
  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }

    launchCatching {
      accountService.linkAccount(email, password)
      openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
    }
  }
}
