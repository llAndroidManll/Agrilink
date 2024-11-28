// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.gms.google.services) apply false

    // Dagger Hilt
    id("com.google.dagger.hilt.android") version "2.51.1" apply false

    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
}
buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    }
}
