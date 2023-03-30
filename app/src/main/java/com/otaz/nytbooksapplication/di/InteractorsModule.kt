package com.otaz.nytbooksapplication.di

import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.interactors.app.DeleteMovie
import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.app.GetSavedMovies
import com.otaz.imdbmovieapp.interactors.app.SaveMovie
import com.otaz.imdbmovieapp.interactors.movie_detail.GetMovieReviews
import com.otaz.imdbmovieapp.interactors.movie_detail.GetOmdbMovie
import com.otaz.imdbmovieapp.interactors.movie_detail.GetTmdbMovie
import com.otaz.imdbmovieapp.interactors.movie_game.GetRandomTopRatedMovie
import com.otaz.imdbmovieapp.interactors.movie_list.GetMostPopularMovies
import com.otaz.imdbmovieapp.interactors.movie_list.GetTopRatedMovies
import com.otaz.imdbmovieapp.interactors.movie_list.GetUpcomingMovies
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.network.OmdbApiService
import com.otaz.imdbmovieapp.network.TmdbApiService
import com.otaz.imdbmovieapp.network.model.*
import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.network.model.BookDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetBookListUC(
        nytApiService: NYTApiService,
        bookDtoMapper: BookDtoMapper,
    ): GetBookListUC{
        return GetBookListUC(
            nytApiService = nytApiService,
            dtoMapper = movieDtoMapper,
        )
    }
}