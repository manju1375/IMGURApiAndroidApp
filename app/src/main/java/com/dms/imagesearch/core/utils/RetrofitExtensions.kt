package com.dms.imagesearch.core.utils

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import retrofit2.Retrofit

// Retrofit

/**
 * Synthetic sugaring to create Retrofit Service.
 */
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

/**
 * Creates a fake error response.
 */
fun <T> responseError(): List<T> = emptyList()