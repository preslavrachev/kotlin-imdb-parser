package com.preslavrachev.imdbparser

import com.preslavrachev.imdbparser.model.Movie
import com.preslavrachev.imdbparser.model.MovieRef
import com.preslavrachev.imdbparser.model.Plot
import org.jsoup.Jsoup
import rx.Observable
import rx.schedulers.Schedulers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by preslavrachev on 11/06/16.
 */
class ImdbParser {

    private val IMBD_URL_PREFIX = "http://www.imdb.com/title/";
    private val IMDB_PLOT_SUMMARY_URL_SUFFIX = "/plotsummary";

    fun parse(id: String): Movie {

        return fetchBasicInfo(id)
                .zipWith(fetchPlotSummary(id),
                        { movieInfo, plotSummary -> movieInfo.apply { plot = plotSummary } })
                .subscribeOn(Schedulers.io())
                .toBlocking().first();
    }

    private fun fetchBasicInfo(id: String): Observable<Movie> {
        val observable = Observable.create<Movie> {
            sub ->

            val doc = Jsoup.connect(IMBD_URL_PREFIX + id + "/").get();
            val name = doc.select(".title_wrapper h1").first().text();
            val releaseData = doc.select("meta[itemprop='datePublished']").first().attr("content");
            val relatedMovies = doc.select(".rec_item").map {
                element ->
                MovieRef(element.attr("data-tconst"), element.select("img").first().attr("title"))
            }

            sub.onNext(
                    Movie(
                            id = id,
                            name = name,
                            releaseDate = LocalDate.parse(releaseData, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            relatedMovies = relatedMovies
                    )
            )
        }

        return observable;
    }

    private fun fetchPlotSummary(id: String): Observable<Plot> {

        val observable = Observable.create<Plot> {
            sub ->

            val doc = Jsoup.connect(IMBD_URL_PREFIX + id + IMDB_PLOT_SUMMARY_URL_SUFFIX).get();
            val plotSummary = doc.select(".plotSummary").first().text();

            sub.onNext(
                    Plot(plotSummary)
            )
        }

        return observable;

    }

}