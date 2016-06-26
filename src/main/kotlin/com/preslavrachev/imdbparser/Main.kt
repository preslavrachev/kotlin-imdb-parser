package com.preslavrachev.imdbparser

import com.preslavrachev.imdbparser.cli.Settings
import com.beust.jcommander.JCommander


/**
 * Created by preslavrachev on 12/06/16.
 */
fun main(args: Array<String>) {
    val settings = Settings();
    val parser = ImdbParser();

    JCommander(settings, *args);
    println(parser.parse(settings.movieId));
}