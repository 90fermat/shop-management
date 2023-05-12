package com.foyangtech.shop_management.model.service

interface ConfigurationService {
  suspend fun fetchConfiguration(): Boolean
  val isShowTaskEditButtonConfig: Boolean
}
