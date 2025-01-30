package dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

object DataStore {
    private lateinit var dataStore: DataStore<Preferences>

    fun getDataStore(producePath: () -> String): DataStore<Preferences> =
        if (::dataStore.isInitialized) dataStore else PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        ).also { dataStore = it }
}

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
