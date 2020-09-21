package com.repository.repository

import com.repository.extension.debugLog
import com.repository.models.TrendingRepo
import com.repository.models.TrendingRepoRequest
import com.repository.models.TrendingResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TrendingRepoRemoteDataSource @Inject constructor(private val apiClient: ApiClient) {

    private var trendingRepoRequest: TrendingRepoRequest = TrendingRepoRequest()

    fun setRequestData(trendingRepoRequest: TrendingRepoRequest) =
        apply { this.trendingRepoRequest = trendingRepoRequest }

    fun execute(viewModeScope: CoroutineScope, result: suspend (TrendingResponse) -> Unit) {
        viewModeScope.launch {
            try {
                val response = getResponse()
                handleResponse(response, result)
            } catch (e: Exception) {
                debugLog(e.message.toString())
                result(TrendingResponse(null, false, e.message.toString()))
            }
        }
    }

    private suspend fun getResponse(): Response<List<TrendingRepo>> =
        withContext(Dispatchers.Default) {
            apiClient.getTrendingRepository(
                trendingRepoRequest.language ?: "",
                trendingRepoRequest.since ?: "",
                trendingRepoRequest.spokenLanguageCode ?: ""
            )
        }

    private suspend fun handleResponse(
        response: Response<List<TrendingRepo>>,
        result: suspend (TrendingResponse) -> Unit
    ) = withContext(Dispatchers.Default) {
        if (response.isSuccessful && response.code() == 200) {
            val body = response.body() ?: throw Exception("Response body is null")
            result(TrendingResponse(body, true, ""))
            debugLog(body.toString())
        } else result(TrendingResponse(null, false, response.message()))
    }
}

