package com.repository.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.repository.di.ViewModelKey
import com.repository.di.viewmodelfactory.CreateViewModelFactory
import com.repository.viewmodels.TrendingActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModules {

    @Binds
    @IntoMap
    @ViewModelKey(TrendingActivityViewModel::class)
    internal abstract fun bindTrendingViewModel(trendingActivityViewModel: TrendingActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(createViewModelFactory: CreateViewModelFactory): ViewModelProvider.Factory
}