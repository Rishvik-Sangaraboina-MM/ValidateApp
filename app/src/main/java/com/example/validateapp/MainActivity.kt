package com.example.validateapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.validateapp.databinding.ActivityMainBinding
import com.example.validateapp.util.DateView
import com.example.validateapp.util.MainVMProviderFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(), DOBTextChangeListener {
  private lateinit var binding: ActivityMainBinding
  private lateinit var mainVM: MainVM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    mainVM = ViewModelProvider(this, MainVMProviderFactory()).get(MainVM::class.java)
    binding.viewModel = mainVM
    addObservers()
    addListeners()
  }

  private fun addListeners() {
    binding.dateView.addDOBTextChangeListener(this)
    binding.next.setOnClickListener {
      Toast.makeText(applicationContext, "Submitted! Successfully", Toast.LENGTH_SHORT).show()
      finish()
    }
    binding.noPan.setOnClickListener {
      finish()
    }
  }

  private fun addObservers() {
    mainVM.dobState.observe(this) {
      setDOBState(binding.dateView, it)
    }
    mainVM.panState.observe(this) {
      setPANState(binding.panLayout, it)
    }
    mainVM.next.observe(this) {
      setNextButtonEnabled(binding.next, it)
    }
  }

  private fun setNextButtonEnabled(
    materialButton: MaterialButton,
    viewState: ViewState
  ) {
    when (viewState) {
      is INVALID -> materialButton.isEnabled = false
      is VALID -> materialButton.isEnabled = true
    }
  }

  private fun setPANState(
    textInputLayout: TextInputLayout,
    viewState: ViewState
  ) {
    when (viewState) {
      is INVALID -> textInputLayout.error = viewState.msg
      is VALID -> textInputLayout.error = viewState.msg
    }
  }

  private fun setDOBState(
    dateView: DateView,
    viewState: ViewState
  ) {
    when (viewState) {
      is INVALID -> dateView.error = viewState.msg
      is VALID -> dateView.error = viewState.msg
    }
  }

  override fun onTextChanged(dob: String) {
    mainVM.dob.value = dob
  }
}