package com.example.colorfight.data.color.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ColorRequestDTO(var action:String="sendColors", var colors: ColorCountsDTO) {
}