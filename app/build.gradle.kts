import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.text.SimpleDateFormat
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.2"
    namespace = "com.yuk.miuiXXL"
    defaultConfig {
        applicationId = namespace
        minSdk = 31
        targetSdk = 33
        versionCode = 5
        versionName = "0.5" + (getGitHeadRefsSuffix(rootProject))
    }
    val properties = Properties()
    runCatching { properties.load(project.rootProject.file("local.properties").inputStream()) }
    val keystorePath = properties.getProperty("KEYSTORE_PATH") ?: System.getenv("KEYSTORE_PATH")
    val keystorePwd = properties.getProperty("KEYSTORE_PASS") ?: System.getenv("KEYSTORE_PASS")
    val alias = properties.getProperty("KEY_ALIAS") ?: System.getenv("KEY_ALIAS")
    val pwd = properties.getProperty("KEY_PASSWORD") ?: System.getenv("KEY_PASSWORD")
    if (keystorePath != null) {
        signingConfigs {
            create("release") {
                storeFile = file(keystorePath)
                storePassword = keystorePwd
                keyAlias = alias
                keyPassword = pwd
                enableV3Signing = true
            }
        }
    }
    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-log.pro")
            if (keystorePath != null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        debug {
            if (keystorePath != null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    androidResources {
        additionalParameters("--allow-reserved-package-id", "--package-id", "0x45")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
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

fun getGitHeadRefsSuffix(project: Project): String {
    // .git/HEAD描述当前目录所指向的分支信息，内容示例："ref: refs/heads/master\n"
    val headFile = File(project.rootProject.projectDir, ".git" + File.separator + "HEAD")
    if (headFile.exists()) {
        val string: String = headFile.readText(Charsets.UTF_8)
        val string1 = string.replace(Regex("""ref:|\s"""), "")
        val result = if (string1.isNotBlank() && string1.contains('/')) {
            val refFilePath = ".git" + File.separator + string1
            // 根据HEAD读取当前指向的hash值，路径示例为：".git/refs/heads/master"
            val refFile = File(project.rootProject.projectDir, refFilePath)
            // 索引文件内容为hash值+"\n"，
            // 示例："90312cd9157587d11779ed7be776e3220050b308\n"
            refFile.readText(Charsets.UTF_8).replace(Regex("""\s"""), "").subSequence(0, 7)
        } else {
            string.substring(0, 7)
        }
        println("commit_id: $result")
        return ".$result"
    } else {
        println("WARN: .git/HEAD does NOT exist")
        return ""
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    implementation(project(":blockmiui"))
    implementation("com.github.kyuubiran:EzXHelper:1.0.3")
}
