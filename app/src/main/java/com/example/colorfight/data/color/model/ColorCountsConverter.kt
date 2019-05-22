package com.example.colorfight.data.color.model

object ColorCountsConverter :
    Converter<ColorCountsDTO, ColorCounts> {

    override fun convertToUI(dto: ColorCountsDTO): ColorCounts =
        ColorCounts(
            red = dto.red,
            green = dto.green,
            blue = dto.blue
        )

    override fun convertToDTO(ui: ColorCounts): ColorCountsDTO =
        ColorCountsDTO(
            red = ui.red,
            green = ui.green,
            blue = ui.blue
        )
}