package com.otaman.users_list.ui.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.otaman.users_list.domain.models.UserData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ProfileType: NavType<UserData>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UserData? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, UserData::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): UserData {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<UserData> = moshi.adapter(UserData::class.java)

        return checkNotNull(jsonAdapter.fromJson(value))
    }

    override fun put(bundle: Bundle, key: String, value: UserData) {
        bundle.putParcelable(key, value)
    }
}