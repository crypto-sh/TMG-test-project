package nl.tmg.core.base

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import nl.tmg.core.dto.NetworkState


abstract class ActParent<T : BaseViewModel, E : ViewDataBinding>  : AppCompatActivity(){

    open val detailsIntentKey = "data"

    lateinit var viewModel  : T

    lateinit var dataBinding: E

    abstract fun getResourceLayoutId() : Int

    abstract fun getViewModelClass(): Class<T>

    abstract fun getFactory(): ViewModelProvider.Factory

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        dataBinding = DataBindingUtil.setContentView(this, getResourceLayoutId())
        viewModel = ViewModelProviders.of(this, getFactory()).get(getViewModelClass())
        viewModel.getNetworkStatus().observe(this, Observer {
              when(it){
                  NetworkState.LOADED -> hideProgress()
                  NetworkState.LOADING -> showProgress()
                  else -> showError(it.msg)
              }
        })



        lifecycle.addObserver(viewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
             onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    open fun showProgress(){}
    open fun hideProgress(){}
    open fun showError(error : String?){}
}