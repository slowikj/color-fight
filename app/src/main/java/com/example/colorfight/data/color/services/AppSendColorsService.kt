package com.example.colorfight.data.color.services

import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import com.example.colorfight.data.color.model.colorcounts.ColorRequestDTO
import com.example.colorfight.data.common.socket.EventSocket

class AppSendColorsService(private val eventSocket: EventSocket<ColorRequestDTO, ColorCountsDTO>)
	: SendColorsService {

	override fun send(colorsRequest: ColorRequestDTO) {
		eventSocket.send(colorsRequest)
	}
}