package com.preslavrachev.imdbparser.model

import java.time.LocalDate

/**
 * Created by preslavrachev on 11/06/16.
 */
//@formatter:off
data class Movie(
        val id: String,
        val name: String,
        val releaseDate: LocalDate,
        var plot: Plot = Plot("No summary")
) {
//@formatter:on
}