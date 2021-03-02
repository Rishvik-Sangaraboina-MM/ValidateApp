package com.example.validateapp.util

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.example.validateapp.DOBTextChangeListener
import com.example.validateapp.R
import com.example.validateapp.R.layout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DateView(
  context: Context?,
  attrs: AttributeSet?
) : LinearLayout(context, attrs) {
  private var date: TextInputEditText
  private var month: TextInputEditText
  private var year: TextInputEditText
  private var dateLayout : TextInputLayout
  private lateinit var dobTextChangeListener : DOBTextChangeListener
  var error : CharSequence?=null
    set(value) {
      field = value
      dateLayout.error = value
    }

  fun addDOBTextChangeListener(listener : DOBTextChangeListener) {
    dobTextChangeListener=listener
  }
  init {
    inflate(context, layout.date_view, this)
    date = findViewById(R.id.date)
    month = findViewById(R.id.month)
    year = findViewById(R.id.year)
    dateLayout = findViewById(R.id.dateLayout)
    attachListeners()
  }

  private fun attachListeners() {
    date.doAfterTextChanged {
      afterDateChanged(it?.toString() ?: "")
    }
    month.doAfterTextChanged {
      afterMonthChanged(it?.toString() ?: "")
    }
    year.doAfterTextChanged {
      afterYearChange()
    }
  }

  private fun afterDateChanged(date: String) {
    if (date.length == 2)
      month.requestFocus()
    dobTextChangeListener.onTextChanged(getDate())
  }

  private fun afterMonthChanged(month: String) {
    if (month.length == 2)
      year.requestFocus()
    dobTextChangeListener.onTextChanged(getDate())
  }

  private fun afterYearChange(){
    dobTextChangeListener.onTextChanged(getDate())
  }



  private fun getDate() : String{
    return date.text?.toString()+"/"+month.text?.toString()+"/"+year.text?.toString()
  }




}