plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    kotlin("android") version "1.8.22" apply false
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
