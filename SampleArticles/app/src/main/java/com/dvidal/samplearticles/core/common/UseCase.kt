package com.dvidal.samplearticles.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> where Type : Any? {

    abstract suspend fun run(params: Params): EitherResult<Type>

    operator fun invoke(params: Params, coroutineDispatcher: CoroutineDispatcher, job: Job,
                        onResult: (EitherResult<Type>) -> Unit = {}) {

        val scope = CoroutineScope(coroutineDispatcher + job)
        scope.launch {
            onResult(run(params))
        }
    }

    class None
}