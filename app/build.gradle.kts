import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33
    namespace = "com.yuk.miuiXXL"
    defaultConfig {
        applicationId = namespace
        minSdk = 31
        targetSdk = 33
        versionCode = 4
        versionName = "0.4"
        buildConfigField("String", "BUILD_TIME", "\"${System.currentTimeMillis()}\"")
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
                (this as BaseVariantOutputImpl).outputFileName = "Miui_XXL-$versionName($versionCode)-$name.apk"
            }
        }
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    implementation(project(":blockmiui"))
    implementation("com.github.kyuubiran:EzXHelper:1.0.3")
}
