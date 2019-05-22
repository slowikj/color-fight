package com.example.colorfight.data.color.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ColorCountsDTO(var red: Long = 0,
                          var green: Long = 0,
                          var blue: Long = 0)