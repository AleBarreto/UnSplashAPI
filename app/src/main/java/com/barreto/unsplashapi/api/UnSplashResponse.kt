package com.barreto.unsplashapi.api

import com.barreto.unsplashapi.model.UnSplashPhoto

data class UnSplashResponse(
    val results: List<UnSplashPhoto>
)