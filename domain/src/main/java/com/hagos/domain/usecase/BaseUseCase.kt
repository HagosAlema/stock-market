package com.hagos.domain.usecase

import com.hagos.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<T, R>{
    suspend fun invoke(t:T): Flow<Resource<R>> = flow{
        emit(Resource.Loading(isLoading = true))
        runCatching {
            return@runCatching implement(t)
        }.onFailure {
            emit(Resource.Error(message = it.localizedMessage))
        }.onSuccess {
            emit(Resource.Success(data = it))
        }
        emit(Resource.Loading(isLoading = false))
    }

    abstract suspend fun implement(input:T): R
}