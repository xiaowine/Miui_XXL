import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33
    namespace = "com.yuk.fuckMiui"
    defaultConfig {
        applicationId = namespace
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "0.1"
    }
    buildTypes {
        named("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            setProguardFiles(listOf("proguard-rules.pro", "proguard-log.pro"))
        }
    }
    androidResources {
        additionalParameters("--allow-reserved-package-id", "--package-id", "0x45")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.majorVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.bin"
            excludes += "/*.json"
        }
        dex {
            useLegacyPackaging = true
        }
        applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName = "FuckMiui-$versionName($versionCode)-$name.apk"
            }
        }
    }
}

dependencies {
    compileOnly(project(":hidden-api"))
    compileOnly("de.robv.android.xposed:api:82")

    implementation(project(":blockmiui"))
    implementation("com.github.kyuubiran:EzXHelper:1.0.3")
    implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")
}
