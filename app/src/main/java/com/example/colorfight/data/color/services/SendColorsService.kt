package com.example.colorfight.data.color.services

import com.example.colorfight.data.color.model.colorcounts.ColorRequestDTO

interface SendColorsService {

	fun send(colorsRequest: ColorRequestDTO)
}