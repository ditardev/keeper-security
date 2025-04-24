plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.micro"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	//Security
	implementation("org.springframework.boot:spring-boot-starter-security:3.4.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.4.4")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")

	//Mail
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")

	//Eureka
	//implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.2.1")

	//Postgres
	implementation("org.postgresql:postgresql:42.7.5")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:11.7.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")

	//Addiction
	implementation("io.micrometer:micrometer-registry-prometheus:1.14.5")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.4.4")
	implementation("org.springframework.boot:spring-boot-devtools:3.4.4")
	implementation("org.springframework.boot:spring-boot-configuration-processor:3.4.4")

	compileOnly("org.projectlombok:lombok:1.18.38")

	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
