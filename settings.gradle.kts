plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "applied-fp-workshop-kotlin"
include("app")
include("demo")
include("webapp")
