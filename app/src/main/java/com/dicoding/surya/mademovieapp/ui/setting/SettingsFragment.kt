package com.dicoding.surya.mademovieapp.ui.setting


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.ui.main.MainViewModel
import com.dicoding.surya.mademovieapp.ui.main.MainViewModelFactory
import com.dicoding.surya.mademovieapp.utils.AlarmReceiver
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class SettingsFragment : PreferenceFragmentCompat(), KodeinAware {

    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()
    private lateinit var viewModel : MainViewModel

    private var alarmReceiver = AlarmReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)

        val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("daily_reminder")
        val releaseReminderSwitch = findPreference<SwitchPreferenceCompat>("release_reminder")
        val languagePreference = findPreference<Preference>("preference_language")

        dailyReminderSwitch?.isChecked = viewModel.isDailyReminderEnabled() == true

        releaseReminderSwitch?.isChecked = viewModel.isReleaseReminderEnabled() == true

        dailyReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, _ ->
                val dailySwitch = preference as SwitchPreferenceCompat

                if (dailySwitch.isChecked){
                    viewModel.setDailyReminder(false)
                    alarmReceiver.cancelAlarm(context as Context, AlarmReceiver().TYPE_DAILY)

                } else{
                    viewModel.setDailyReminder(true)
                    alarmReceiver.setRepeatingAlarm(context as Context, AlarmReceiver().TYPE_DAILY, "07:00",
                        getString(R.string.daily_notif_message))

                }
                true
            }

        releaseReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, _ ->
                val releaseSwitch = preference as SwitchPreferenceCompat

                if (releaseSwitch.isChecked){
                    viewModel.setReleaseReminder(false)
                    alarmReceiver.cancelAlarm(context as Context, AlarmReceiver().TYPE_RELEASE)


                } else{
                    viewModel.setReleaseReminder(true)
                    alarmReceiver.setRepeatingAlarm(context as Context, AlarmReceiver().TYPE_RELEASE, "08:00",
                        getString(R.string.release_notif_message))
                }
                true
            }

        languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }


}
