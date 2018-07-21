package mhashim6.lib.ratemonitor

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.defaultSharedPreferences

object RateConditionsMonitor {

    private const val MONITOR = "MONITOR"
    private const val LAUNCH_TIMES = "LAUNCH_TIMES"
    private const val REMINDER_MODE = "REMINDER_MODE"
    private const val REMIND_TIMES = "REMIND_TIMES"

    private lateinit var preferences: SharedPreferences

    private var launchTimesPref
        get() = preferences.getInt(LAUNCH_TIMES, 0)
        set(value) {
            preferences.edit {
                putInt(LAUNCH_TIMES, value)
            }
        }

    private var remindTimesPref
        get() = preferences.getInt(REMIND_TIMES, 0)
        set(value) {
            preferences.edit {
                putInt(REMIND_TIMES, value)
            }
        }

    private var monitorPref
        get() = preferences.getBoolean(MONITOR, true)
        set(value) {
            preferences.edit {
                putBoolean(MONITOR, value)
            }

        }

    private var reminderModePref
        get() = preferences.getBoolean(REMINDER_MODE, false)
        set(value) {
            preferences.edit {
                putBoolean(REMINDER_MODE, value)
            }

        }

    val isConditionsMet: Boolean
        get() = conditions.debug || monitorPref
                && (launchTimesPref >= conditions.launchTimes)
                && (!reminderModePref || (remindTimesPref >= conditions.remindTimes))


    private val conditions = RateConditions(launchTimes = 3, remindTimes = 7, debug = false)

    fun init(context: Context) {
        preferences = context.defaultSharedPreferences

        launchTimesPref++
        if (reminderModePref)
            remindTimesPref++
    }

    fun applyConditions(launchTimes: Int = 3, remindTimes: Int = 7, debug: Boolean = false) {
        conditions.apply {
            this.launchTimes = launchTimes
            this.remindTimes = remindTimes
            this.debug = debug
        }
    }

    fun rated() {
        monitorPref = false
        reminderModePref = false
    }

    fun denied() {
        monitorPref = false
    }

    fun later() {
        remindTimesPref = 0
        reminderModePref = true
    }

    private class RateConditions(var launchTimes: Int, var remindTimes: Int, var debug: Boolean)

    @SuppressLint("ApplySharedPref")
    private inline fun SharedPreferences.edit(
            commit: Boolean = false,
            action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        action(editor)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }

}