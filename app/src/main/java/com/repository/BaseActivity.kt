package com.repository

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.repository.di.viewmodelfactory.CreateViewModelFactory
import dagger.android.AndroidInjection

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    protected open fun inject() {
        AndroidInjection.inject(this)
    }

    protected fun <T : ViewModel> createViewModel(
        viewModel: Class<T>,
        viewModelFactory: CreateViewModelFactory
    ): T {
        return ViewModelProvider(
            this,
            viewModelFactory
        ).get(viewModel)
    }
}