package com.cavista.cavista

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    var mainViewModel = MainViewModel()
    private var progressDialog: ProgressDialog? = null


    /**
     * setup of coroutine for debounce
     * */
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)

        //Data binding
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)

        //set up viewmodel
        mainViewModel =  ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, mainViewModel)



        val layoutManager = GridLayoutManager(this, 2)
        recycleImagList.layoutManager = layoutManager


        /**
         * to enable keyboard done button for api call
         * */
        etxSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                mainViewModel.loadImageList()
                true
            } else false
        }


        /**
         * search with 250ms debounce on edittext
         * */
        etxSearch.addTextChangedListener(object : TextWatcher{

            private var searchFor = ""

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText

                launch {
                    delay(250)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    mainViewModel.searchValue.value = searchFor
                    mainViewModel.loadImageList()
                }
            }
        })


        setObservers()
    }


    /**
     * setup of observers
     * */
    fun setObservers(){

        mainViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
               showDialog(this)
            } else {
              dismissDialog(this)
            }
        })


        mainViewModel.showToast.observe(this, Observer { showToast ->
            if (showToast) {

                Toast.makeText(this,mainViewModel.showToastValue, Toast.LENGTH_LONG).show()
            }
        })



        mainViewModel.detailClick.observe(this, Observer{

            if (it){

                val intent = Intent(this, ImageDetailActivity::class.java)
                intent.putExtra("imageLink", mainViewModel.imageLink)
                intent.putExtra("imageTitle", mainViewModel.imageTitle)
                startActivity(intent)
            }
        })

    }


    fun showDialog(mContext: Activity) {
        if (progressDialog == null && !mContext.isFinishing) {
            progressDialog = ProgressDialog(mContext)
           progressDialog?.show()
        }
    }

    fun dismissDialog(mContext: Activity) {
        if (progressDialog != null && progressDialog!!.isShowing && !mContext.isFinishing)
           progressDialog?.dismiss()
        progressDialog = null
    }









}