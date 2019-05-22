package com.example.colorfight.data.color.model

interface Converter<DTO, UI> {

    fun convertToUI(dto: DTO): UI

    fun convertToDTO(ui: UI): DTO
}