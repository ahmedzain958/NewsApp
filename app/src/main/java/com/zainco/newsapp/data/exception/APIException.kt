package com.zainco.newsapp.data.exception

data class APIException(var status: String, var code: String, override var message: String) :
    RuntimeException()