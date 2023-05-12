package com.foyangtech.shop_management.model.service

interface LogService {
  fun logNonFatalCrash(throwable: Throwable)
}
