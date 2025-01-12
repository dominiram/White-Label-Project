package repos.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PushNotificationsDataStore(
    private val dataStore: DataStore<Preferences>,
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) {
    fun getPushNotificationArticleUrl(): Flow<String?> =
        dataStore.data.map { it[stringPreferencesKey(DATA_STORE_KEY_PUSH_NOTIFICATION)] }
            .flowOn(ioCoroutineContext)

    suspend fun storePushNotificationArticleUrl(articleUrl: String?) = kotlin.runCatching {
            withContext(ioCoroutineContext) {
                articleUrl?.let { url ->
                    dataStore.edit {
                        it[stringPreferencesKey(DATA_STORE_KEY_PUSH_NOTIFICATION)] = url
                    }
                }
            }
        }

    companion object {
        private const val DATA_STORE_KEY_PUSH_NOTIFICATION = "DATA_STORE_KEY_PUSH_NOTIFICATION"
    }
}
