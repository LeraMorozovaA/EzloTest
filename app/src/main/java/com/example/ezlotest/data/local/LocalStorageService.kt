package com.example.ezlotest.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStorageService(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val DELETED_DEVICES_PK_SET = stringSetPreferencesKey("deleted_devices_pk_set")
    }

    suspend fun setDeletedDevicePKSet(set: Set<String>) {
        dataStore.edit {
            it[PreferencesKeys.DELETED_DEVICES_PK_SET] = set
        }
    }

    suspend fun getDeletedDevicePKSet(): Set<String> {
        return dataStore.data
            .map { it[PreferencesKeys.DELETED_DEVICES_PK_SET] ?: setOf() }
            .first()
    }
}