package com.foyangtech.shop_management.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.foyangtech.shop_management.util.HOME_SCREEN
import com.foyangtech.shop_management.util.SPLASH_SCREEN

import com.foyangtech.shop_management.model.service.AccountService
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.ui.screens.ShopManagementViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : ShopManagementViewModel(logService) {
  val showError = mutableStateOf(false)

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {

    showError.value = false
    if (accountService.hasUser) openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
    else createAnonymousAccount(openAndPopUp)
  }

  private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
    launchCatching(snackbar = false) {
      try {
        accountService.createAnonymousAccount()
      } catch (ex: FirebaseAuthException) {
        showError.value = true
        throw ex
      }
      openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
    }
  }
}
