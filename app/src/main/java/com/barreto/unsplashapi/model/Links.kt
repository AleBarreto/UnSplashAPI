package com.barreto.unsplashapi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links (

	@SerializedName("self") val self : String,
	@SerializedName("html") val html : String,
	@SerializedName("download") val download : String,
	@SerializedName("download_location") val download_location : String
) : Parcelable