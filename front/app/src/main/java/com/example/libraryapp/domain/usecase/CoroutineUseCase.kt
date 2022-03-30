package com.example.libraryapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.libraryapp.device.ProcessState
import com.example.libraryapp.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    fun invokeAsLiveData(params: P): LiveData<ProcessState<R>> = invokeAsFlow(params).asLiveData()

    private fun invokeAsFlow(params: P): Flow<ProcessState<R>> = flow {
        emit(ProcessState.Loading)
        emit(ProcessState.Finish(invoke(params)))
    }

    suspend operator fun invoke(params: P): Result<R> = try {
        withContext(coroutineDispatcher) {
            execute(params).let {
                if (it is Response<*> && it.code() >= 400) {
                    val jObjError = JSONObject(it.errorBody()!!.string())
                    return@let Result.Error(jObjError.getString("error"), HttpException(it))
                }
                Result.Success(it)
            }
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Result.Error(
                "No hay conexion - Estas conectado a internet?",
                throwable
            )
            is HttpException -> {
                if (throwable.code() == 404)
                    Result.NotFoundError
                else {
                    val jObjError = JSONObject(throwable.response()!!.errorBody()!!.string())
                    Result.Error(jObjError.getString("error"), throwable)

                }
            }
            else -> Result.Error(null, throwable)
        }
    }

    abstract suspend fun execute(params: P): R
}
