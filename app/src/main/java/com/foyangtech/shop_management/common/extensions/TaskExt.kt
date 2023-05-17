package com.foyangtech.shop_management.common.extensions

import com.foyangtech.shop_management.model.Task

fun Task?.hasDueDate(): Boolean {
  return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTime(): Boolean {
  return this?.dueTime.orEmpty().isNotBlank()
}
