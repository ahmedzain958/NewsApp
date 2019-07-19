package com.zainco.newsapp.data.exception

data class APIException(override var message: String) :
    RuntimeException()