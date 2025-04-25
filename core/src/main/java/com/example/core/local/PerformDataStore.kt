package com.example.core.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = DataStoreKeys.KEY_PREFERENCES_NAME)
class PerformDataStore (private val context: Context) {
    companion object {
        val KEY_IS_SIGN_IN = booleanPreferencesKey(DataStoreKeys.KEY_IS_SIGN_IN)
        val KEY_ID = stringPreferencesKey(DataStoreKeys.KEY_USER_ID)
        val KEY_NAME = stringPreferencesKey(DataStoreKeys.KEY_NAME)
        val KEY_EMAIL = stringPreferencesKey(DataStoreKeys.KEY_EMAIL)
        val KEY_PHONE = stringPreferencesKey(DataStoreKeys.KEY_PHONE)
    }
    suspend fun putBoolean(key : Preferences.Key<Boolean>, value: Boolean)
    {
        context.dataStore.edit { it[key] = value }
    }
    suspend fun getBoolean(key : Preferences.Key<Boolean>, value: Boolean = false) : Flow<Boolean>
    {
        return context.dataStore.data.map { it[key] ?: value }
    }
    suspend fun putString (key: Preferences.Key<String>, value : String)
    {
        context.dataStore.edit { it[key] = value }
    }
    suspend fun getString(key : Preferences.Key<String>, value : String = "") : Flow<String>
    {
        return context.dataStore.data.map { it[key] ?: value }
    }
    suspend fun clear()
    {
        context.dataStore.edit { it.clear() }
    }
}