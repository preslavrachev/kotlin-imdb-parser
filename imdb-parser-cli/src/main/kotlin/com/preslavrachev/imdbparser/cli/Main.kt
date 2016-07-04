package com.preslavrachev.imdbparser.cli

import com.beust.jcommander.JCommander
import com.preslavrachev.imdbparser.ImdbParser


/**
 * Created by preslavrachev on 12/06/16.
 */
fun main(args: Array<String>) {
    val settings = Settings();
    val parser = ImdbParser();

    JCommander(settings, *args);
    println(parser.parse(settings.movieId));
}