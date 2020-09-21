package com.repository.di.modules

import com.repository.views.TrendingRepoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeTrendingRepoActivity(): TrendingRepoActivity
}