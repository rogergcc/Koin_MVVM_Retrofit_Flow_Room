package com.app.koin_mvvm_retrofit_flow_room.data

import android.content.Context
import com.app.koin_mvvm_retrofit_flow_room.utils.ApiStatus
import com.app.koin_mvvm_retrofit_flow_room.utils.NetWorkResult
import com.app.koin_mvvm_retrofit_flow_room.utils.Utils
import kotlin.reflect.full.memberProperties

class ApiResultHandler<T>(
    private val context: Context,
    private val onLoading: () -> Unit,
    private val onSuccess: (T?) -> Unit,
    private val onFailure: () -> Unit,
) {

    fun handleApiResult(result: NetWorkResult<T>) {
        when (result.status) {
            ApiStatus.LOADING -> {
                onLoading()
            }

            ApiStatus.SUCCESS -> {
                onSuccess(result.data)
            }

            ApiStatus.ERROR -> {
                onFailure()
                result.message?.let { Utils.showAlertDialog(context, it) }
            }
        }
    }

    @Throws(IllegalAccessException::class, ClassCastException::class)
    inline fun <reified T> Any.getField(fieldName: String): T? {
        this::class.memberProperties.forEach { kCallable ->
            if (fieldName == kCallable.name) {
                return kCallable.getter.call(this) as T?
            }
        }
        return null
    }

}
