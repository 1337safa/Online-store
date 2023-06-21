package com.safronov_original_app_online_store.data.network.exception

class NetworkException(
    errorMessage: String, errorCause: Throwable? = null
): RuntimeException(
    errorMessage, errorCause
) {

}