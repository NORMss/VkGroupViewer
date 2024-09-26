package com.norm.vkgroupviewer.di

import android.app.Application
import android.content.Context
import com.norm.vkgroupviewer.data.manager.LocalUserManagerImpl
import com.norm.vkgroupviewer.data.remote.AndroidHttpClientProvider
import com.norm.vkgroupviewer.data.repository.VkRepositoryImpl
import com.norm.vkgroupviewer.domain.manager.LocalUserManager
import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.usecases.token_info.ReadTokenInfo
import com.norm.vkgroupviewer.usecases.token_info.SaveTokenInfo
import com.norm.vkgroupviewer.usecases.token_info.TokenInfoUseCases
import com.norm.vkgroupviewer.usecases.vk.GetGroups
import com.norm.vkgroupviewer.usecases.vk.GetProfileInfo
import com.norm.vkgroupviewer.usecases.vk.GetUsersInfo
import com.norm.vkgroupviewer.usecases.vk.ResolveScreenName
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application,
    ): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideTokenInfoUseCases(
        localUserManager: LocalUserManager,
    ): TokenInfoUseCases {
        return TokenInfoUseCases(
            saveTokenInfo = SaveTokenInfo(localUserManager),
            readTokenInfo = ReadTokenInfo(localUserManager),
        )
    }

    @Provides
    @Singleton
    fun provideHttpClientProvide(): HttpClientProvider {
        return AndroidHttpClientProvider()
    }

    @Provides
    @Singleton
    fun provideVkRepository(
        httpClientProvider: HttpClientProvider,
    ): VkRepository {
        return VkRepositoryImpl(
            httpClientProvider = httpClientProvider,
        )
    }

    @Provides
    @Singleton
    fun provideVkUseCases(
        vkRepository: VkRepository,
    ): VkUseCases {
        return VkUseCases(
            getProfileInfo = GetProfileInfo(vkRepository),
            getGroups = GetGroups(vkRepository),
            getUsersInfo = GetUsersInfo(vkRepository),
            resolveScreenName = ResolveScreenName(vkRepository),
        )
    }
}
