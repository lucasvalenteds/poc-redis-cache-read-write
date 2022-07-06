pluginManagement {
    repositories {
        maven(url = "https://repo.spring.io/milestone")
        gradlePluginPortal()
    }
}

rootProject.name = "poc-redis-cache-read-write"

include(":service-api")
include(":service-domain")
