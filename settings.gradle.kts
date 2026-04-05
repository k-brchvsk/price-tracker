@file:Suppress("UnstableApiUsage")

import pricely.includeAll

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

plugins {
    id("pricely.gradle-extension-settings") version "1.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PriceTracker"
includeBuild("build-logic")

includeAll(
    ":app",
    ":navigation:api",
    ":navigation:internal",
    ":core:design-system",
    ":core:price-session:api",
    ":core:price-session:internal",
    ":feature:feed:api",
    ":feature:feed:internal",
    ":feature:feed:di",
    ":feature:symbol-details:api",
    ":feature:symbol-details:internal",
    ":feature:symbol-details:di",
)
