package nl.tmg.dutchnews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nl.tmg.core.base.BaseViewModel
import nl.tmg.core.dto.NetworkState


abstract class ActDetailsViewModel : BaseViewModel() {

}


class ActDetailsViewModelImpl : ActDetailsViewModel() {

    private val networkState = MutableLiveData<NetworkState>()

    override fun getNetworkStatus(): LiveData<NetworkState>  = networkState


}