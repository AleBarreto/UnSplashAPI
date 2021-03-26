package com.barreto.unsplashapi.model
import com.google.gson.annotations.SerializedName


data class CurrentUserCollections (

	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("published_at") val published_at : String,
	@SerializedName("last_collected_at") val last_collected_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("cover_photo") val cover_photo : String,
	@SerializedName("user") val user : String
)