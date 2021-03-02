package com.example.validateapp

sealed class ViewState

data class VALID(val msg : String?=null) : ViewState()

data class INVALID(val msg : String?=null) : ViewState()
