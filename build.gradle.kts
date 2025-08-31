plugins {
    id("java")
    id("org.flywaydb.flyway") version "11.11.2"
}

val FLYWAY_VERSION = "11.11.2"
val MYSQL_DRIVER_VERSION = "8.2.0"
val LOMBOK_VERSION = "1.18.38"
val JUNIT_BOM_VERSION = "5.13.4"

val DB_URL = "jdbc:mysql://localhost/jdbcsample"
val ENV_DB_USER = "DB_MYSQL_USER"
val ENV_DB_PASSWORD = "DB_MYSQL_PASSWORD"

fun envOrFail(name: String): String =
    System.getenv(name) ?: error("Variável de ambiente $name não definida")

val dbUser: String = envOrFail(ENV_DB_USER)
val dbPassword: String = envOrFail(ENV_DB_PASSWORD)

flyway {
    url = DB_URL
    user = dbUser
    password = dbPassword
    configurations = arrayOf("runtimeClasspath")
    outOfOrder = true
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
dependencies {
    // JUnit 5 BOM e dependências
    testImplementation(platform("org.junit:junit-bom:$JUNIT_BOM_VERSION"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Flyway Core e MySQL
    implementation("org.flywaydb:flyway-core:$FLYWAY_VERSION")
    implementation("org.flywaydb:flyway-mysql:$FLYWAY_VERSION")
    implementation("com.mysql:mysql-connector-j:$MYSQL_DRIVER_VERSION")

    // Lombok
    compileOnly("org.projectlombok:lombok:$LOMBOK_VERSION")
    annotationProcessor("org.projectlombok:lombok:$LOMBOK_VERSION")
}
tasks.test {
    useJUnitPlatform()
}