// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false

    alias(libs.plugins.devtools.ksp) apply false

//    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false

//    id "org.jetbrains.kotlin.jvm" version "1.9.0"
}
true // Needed to make the Suppress annotation work for the plugins block