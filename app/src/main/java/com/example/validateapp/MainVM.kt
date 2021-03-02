package com.example.validateapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.validateapp.util.Constant
import java.text.SimpleDateFormat
import java.util.Locale

class MainVM : ViewModel() {

  private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
  val pan: MutableLiveData<String> = MutableLiveData()
  val dob: MutableLiveData<String> = MutableLiveData()
  private val _next: MutableLiveData<ViewState> = MutableLiveData()
  val next: LiveData<ViewState> = _next

  val panState: LiveData<ViewState> = Transformations.map(pan) {
    val viewState = if (validatePAN(it)) VALID(null) else INVALID("Invalid")
    nextButtonEnable()
    viewState
  }

  val dobState: LiveData<ViewState> = Transformations.map(dob) {
    val viewState = if (validateDOB(it)) VALID(null) else INVALID("Invalid")
    nextButtonEnable()
    viewState
  }

  private fun nextButtonEnable() {
    if (panState.value is VALID && dobState.value is VALID)
      _next.value = VALID()
    else
      _next.value = INVALID()
  }

  private fun validatePAN(pan: String) = pan.matches(Constant.PAN_REGEX)

  private fun validateDOB(dob: String): Boolean {
    return try {
      simpleDateFormat.isLenient = false
      simpleDateFormat.parse(dob)
      true
    } catch (e: Exception) {
      false
    }
  }
}