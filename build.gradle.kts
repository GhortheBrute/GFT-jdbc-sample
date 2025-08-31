val dbUser: String? = project.findProperty("dbUser") as String? ?: System.getenv("DB_USER")
val dbPassword: String? = project.findProperty("dbPassword") as String? ?: System.getenv("DB_PASSWORD")

plugins {
    id("java")
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5 BOM e dependências
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // MySQL Connector
    implementation("com.mysql:mysql-connector-j:9.4.0")

    // Flyway Core e MySQL
    implementation("org.flywaydb:flyway-core:11.11.2")
    implementation("org.flywaydb:flyway-mysql:11.11.2")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}

tasks.test {
    useJUnitPlatform()
}