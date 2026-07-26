// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    kotlin("jvm") version "2.4.10"
    id("com.google.devtools.ksp") version "2.3.10"
}