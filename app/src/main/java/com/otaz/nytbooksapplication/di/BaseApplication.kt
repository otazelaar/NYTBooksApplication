package com.otaz.nytbooksapplication.di

import android.app.Application
import com.otaz.nytbooksapplication.di.AppModule.provideApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application()