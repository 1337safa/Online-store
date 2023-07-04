package com.safronov_original_app_online_store.presentation.exception

class PresentationException(
    errorMessage: String, errorCause: Throwable? = null
): RuntimeException(
    errorMessage, errorCause
) {
}