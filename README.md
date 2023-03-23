# RecipeFinderApp

This android app provides users with 10 various recipes based on the word they enter. For example, it provides 10 apple based recipe when you put apple is an input. Used
RestAPI from https://rapidapi.com/apininjas/api/recipe-by-api-ninjas. 

## Built with

* Kotlin
* RestAPI
* Android Studio

## Acknowledgments

* RapidAPI

## For Developers

To get data from the Rest API, you need to create a variable in the local.properties file for your API key(gotten from RapidAPI). And call this variable when setting up your API connection.

If you are using Android Studio, make your API key variable in local.properties file, add this dependency to the build.gradle project file-under plugins(
    id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin' version '2.0.1' apply false), sync your project. Then finally call BuildConfig.(your api key variable) when setting up your API connection.


