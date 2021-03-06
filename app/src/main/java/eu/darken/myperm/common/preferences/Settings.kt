package eu.darken.myperm.common.preferences

import android.content.SharedPreferences
import androidx.preference.PreferenceDataStore

abstract class Settings {

    abstract val preferenceDataStore: PreferenceDataStore

    abstract val preferences: SharedPreferences

}