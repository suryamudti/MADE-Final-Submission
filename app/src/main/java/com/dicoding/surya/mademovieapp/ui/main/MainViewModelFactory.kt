package com.dicoding.surya.mademovieapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.surya.mademovieapp.data.repositories.AppRepository

/**
 * Created by suryamudti on 27/08/2019.
 */
class MainViewModelFactory(val repository: AppRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}