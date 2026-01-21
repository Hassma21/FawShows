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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "fawshows"
include(":app")
include(":feature:fawshows_main")
include(":feature:fawshows_search")
include(":feature:fawshows_favourites")
include(":feature:fawshows_detail")
include(":core:data")
include(":core:domain")
include(":core:common")
