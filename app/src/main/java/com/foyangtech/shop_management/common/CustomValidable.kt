package com.foyangtech.shop_management.common

import androidx.compose.runtime.MutableState
import tech.devscast.validable.BaseValidable

class CustomValidable(isFieldValid: ((String) -> Boolean)):
   BaseValidable(validator = isFieldValid, errorFor = ::fieldValidationError)
{
}

private fun fieldValidationError(text: String): String {
   return "This field is not valid"
}


