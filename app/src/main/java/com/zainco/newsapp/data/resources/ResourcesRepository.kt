package com.zainco.newsapp.data.resources

import com.zainco.newsapp.R

class ResourcesRepository(private val appResources: AppResources) {
    fun getNetworkExceptionMessage(): String = appResources.getString(R.string.no_internet_connection)
    fun getSocketTimeoutExceptionMessage(): String = appResources.getString(R.string.timeout_error_message)
    fun getGenericUnknownErrorMessage(): String = appResources.getString(R.string.generic_unknown_error)
}