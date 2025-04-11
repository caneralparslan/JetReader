package com.amasmobile.jet_a_reader.screens.login_signup

data class LoadingState(
    val status: Status,
    val message: String? = null
){

    companion object{
        val SUCCESS = LoadingState(Status.SUCCESS)
        val FAIL = LoadingState(Status.FAIL)
        val LOADING = LoadingState(Status.LOADING)
        val IDLE = LoadingState(Status.IDLE)
    }

    enum class Status{
        SUCCESS,
        FAIL,
        LOADING,
        IDLE
    }
}
