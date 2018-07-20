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

    private var launchTimes
        get() = preferences.getInt(LAUNCH_TIMES, 0)
        set(value) {
            preferences.edit {
                putInt(LAUNCH_TIMES, value)
            }
        }

    private var remindTimes
        get() = preferences.getInt(REMIND_TIMES, 0)
        set(value) {
            preferences.edit {
                putInt(REMIND_TIMES, value)
            }
        }

    private var monitor
        get() = preferences.getBoolean(MONITOR, true)
        set(value) {
            preferences.edit {
                putBoolean(MONITOR, value)
            }

        }

    private var reminderMode
        get() = preferences.getBoolean(REMINDER_MODE, false)
        set(value) {
            preferences.edit {
                putBoolean(REMINDER_MODE, value)
            }

        }

    val isConditionsMet: Boolean
        get() = monitor
                && (launchTimes >= conditions.launchTimes)
                && (!reminderMode || (remindTimes >= conditions.remindTimes))


    private var conditions = RateConditions(launchTimes = 3, remindTimes = 7)

    fun init(context: Context) {
        preferences = context.defaultSharedPreferences

        launchTimes++
        if (reminderMode)
            remindTimes++
    }

    fun applyConditions(launchTimes: Int = 3, remindTimes: Int = 5) {
        conditions = RateConditions(launchTimes, remindTimes)
    }

    fun rated() {
        monitor = false
        reminderMode = false
    }

    fun denied() {
        monitor = false
    }

    fun later() {
        remindTimes = 0
        reminderMode = true
    }

    private class RateConditions(val launchTimes: Int, val remindTimes: Int)

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