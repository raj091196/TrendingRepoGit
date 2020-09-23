package com.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.repository.di.viewmodelfactory.CreateViewModelFactory
import com.repository.viewmodels.BaseViewModel
import dagger.android.AndroidInjection
import javax.annotation.Nullable
import javax.inject.Inject

/**
 *  Base Activity for the application
 */
abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    protected open var viewModel: T? = null

    @Inject
    lateinit var utils: Utils

    /**
     * Broadcast Event to notify any common result.
     */
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            handleBroadcast(p1?.action)
        }
    }

    private val networkStateReceiver: NetworkStateReceiver = NetworkStateReceiver()

    /**
     *  Handle the common event broadcast intent and update it to the LiveData
     *  LiveData Should be observed in the Sub class
     */
    private fun handleBroadcast(action: String?) {
        action?.let {
            if (action == NetworkStateReceiver.Action_Network_Change)
                viewModel?.networkStateLiveData?.postValue(utils.hasInternet())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView()
        initViews()
        viewModel = createViewModel(viewModel(), viewModelFactory())
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        @Suppress("DEPRECATION")
        registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        registerBroadcast()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkStateReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    private fun registerBroadcast() {
        val intentFilter = IntentFilter(NetworkStateReceiver.Action_Network_Change)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter)
    }

    /**
     * Dagger injection for Activity happens Here
     */
    protected open fun inject() {
        AndroidInjection.inject(this)
    }

    /**
     * @param viewModel viewModel object
     * @param viewModelFactory viewModel Factory
     * create the view model instance
     */
    private fun createViewModel(
        viewModel: Class<T>,
        @Nullable viewModelFactory: CreateViewModelFactory? = null
    ): T {
        return if (viewModelFactory != null)
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(viewModel)
        else ViewModelProvider(this).get(viewModel)
    }

    /**
     *  @return viewModel class
     *  implemented in the subClass which return the viewModel class
     */
    abstract fun viewModel(): Class<T>

    /**
     *  @return the ViewModel factory if the viewModel has a Constructor Params
     */
    abstract fun viewModelFactory(): CreateViewModelFactory?

    /**
     *  viewModel Observers are initialised in this method impl
     */
    abstract fun initObservers()

    /**
     *  Activity views are initialised in this method impl
     */
    abstract fun initViews()

    /**
     *  Set the contentView of the Activity in the method Impl
     */
    abstract fun setContentView()
}
