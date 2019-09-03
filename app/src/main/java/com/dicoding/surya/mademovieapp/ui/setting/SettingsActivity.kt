package com.dicoding.surya.mademovieapp.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.surya.mademovieapp.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_settings, SettingsFragment()).commit()
    }
}
