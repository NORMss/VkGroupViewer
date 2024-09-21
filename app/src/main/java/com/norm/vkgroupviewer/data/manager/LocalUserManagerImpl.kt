package com.norm.vkgroupviewer.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.norm.vkgroupviewer.domain.manager.LocalUserManager
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import com.norm.vkgroupviewer.util.Constants
import com.norm.vkgroupviewer.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context,
) : LocalUserManager {
    override suspend fun saveToken(tokenInfo: TokenInfo) {
        tokenInfo.accessToken?.let {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.ACCESS_TOKEN] = tokenInfo.accessToken
            }
        }
    }

    override fun readToken(): Flow<TokenInfo> {
        return context.dataStore.data.map { preferences ->
            TokenInfo(
                accessToken = preferences[PreferenceKeys.ACCESS_TOKEN]
            )
        }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey(name = Constants.ACCESS_TOKEN)
    }
}