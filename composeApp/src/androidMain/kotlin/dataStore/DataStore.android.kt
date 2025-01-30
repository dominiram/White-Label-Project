package dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> = dataStore.DataStore.getDataStore {
    context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
}
