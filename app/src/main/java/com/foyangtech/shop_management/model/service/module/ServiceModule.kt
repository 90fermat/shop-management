package com.foyangtech.shop_management.model.service.module

import com.foyangtech.shop_management.model.service.AccountService
import com.foyangtech.shop_management.model.service.ConfigurationService
import com.foyangtech.shop_management.model.service.LogService
import com.foyangtech.shop_management.model.service.StorageService
import com.foyangtech.shop_management.model.service.impl.AccountServiceImpl
import com.foyangtech.shop_management.model.service.impl.LogServiceImpl
import com.foyangtech.shop_management.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

  @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

}
