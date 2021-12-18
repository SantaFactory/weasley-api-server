var profiles = "prod"

tasks.processResources {
    inputs.property("version", version)
    inputs.property("springProfiles", profiles)
    filesMatching("**/application.yml") {
        filter {
            it.replace("#project.version#", version as String)
        }
        filter {
            it.replace("#spring.profiles.active#", profiles)
        }
    }
}
