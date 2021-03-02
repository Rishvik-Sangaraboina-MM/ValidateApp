package com.example.validateapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.validateapp.MainVM

class MainVMProviderFactory : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return MainVM() as T
  }
}