package com.safronov_original_app_online_store.data.storage.exception

class StorageException(
    errorMessage: String, errorCause: Throwable? = null
): RuntimeException(
    errorMessage, errorCause
) {

}