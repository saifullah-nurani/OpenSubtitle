import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.vanniktech.maven.publish") version libs.versions.mavenpublishing
}

android {
    namespace = "io.github.saifullah.nurani.opensubtitle.okhttp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minimumSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    api(libs.okhttp)
    implementation(project(":opensubtitle-core"))
}

mavenPublishing {
    coordinates("io.github.saifullah-nurani", "opensubtitle-okhhtp", libs.versions.libVersion.get())
    pom {
        name.set("Open Subtitle OkHttp")
        description.set("Optional OkHttp implementation for Open Subtitle library.")
        url.set("https://github.com/saifullah-nurani/OpenSubtitle")
        inceptionYear.set("2025")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/saifullah-nurani/OpenSubtitle/blob/main/LICENSE")
            }
        }
        developers {
            developer {
                id.set("saifullah-nurani")
                name.set("Saifullah Nurani")
                email.set("donaldperryman04@gmail.com")
                url.set("https://github.com/saifullah-nurani")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/saifullah-nurani/OpenSubtitle.git")
            developerConnection.set("scm:git:https://github.com/saifullah-nurani/OpenSubtitle.git")
            url.set("https://github.com/saifullah-nurani/OpenSubtitle")
        }
    }
    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    // Enable GPG signing for all publications
    signAllPublications()
}



