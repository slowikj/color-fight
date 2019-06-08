package com.example.colorfight.data.common

interface Converter<DTO, UI> {

    fun convertToUI(dto: DTO): UI

    fun convertToDTO(ui: UI): DTO
}