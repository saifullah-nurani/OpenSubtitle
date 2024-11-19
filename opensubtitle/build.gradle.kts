import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.vanniktech.maven.publish") version "0.29.0"
}

android {
    namespace = "org.opensubtitle"
    compileSdk = 34

    defaultConfig {
        minSdk = 16

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.jsoup)
}
mavenPublishing {
    coordinates("io.github.saifullah-nurani", "opensubtitle", "1.1.0")
    pom {
        name.set("Open Subtitle")
        description.set("Open Subtitle is free open source library to download subtitles from www.opensubtitles.org")
        url.set("https://github.com/saifullah-nurani/OpenSubtitle")
        inceptionYear.set("2024")
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





