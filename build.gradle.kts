// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
true // Needed to make the Suppress annotation work for the plugins block