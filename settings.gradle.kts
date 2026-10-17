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

rootProject.name = "StudyTemplate"
include(":app")
include(":moduleone")
include(":moduletwo")
include(":modulethree")
include(":modulefour")
include(":modulefive")
include(":modulesix")
include(":moduleseven")
include(":moduleeight")
include(":modulenine")
include(":moduleten")
