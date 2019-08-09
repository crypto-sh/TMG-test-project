package nl.tmg.core.base



import androidx.lifecycle.LifecycleObserver

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nl.tmg.core.dto.NetworkState


abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    abstract fun getNetworkStatus() : LiveData<NetworkState>
}