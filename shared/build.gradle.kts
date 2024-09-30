plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

kotlin {

    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    task("testClasses")

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //Compose
            implementation(compose.runtime)
            api(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //Coroutine
            implementation(libs.coroutines.core)

            //Ktor
            implementation(libs.bundles.ktor.common)

            //Kotlinx-Serialization
            implementation(libs.kotlinx.serialization.json)

            //ViewModel
            implementation(libs.bundles.lifecycle)

            //Koin
            implementation(libs.bundles.koin.common)

            //Navigation
            implementation(libs.jetbrains.compose.navigation)

            //Room
            implementation(libs.room.runtime)

            //Sqlite
            implementation(libs.sqlite.bundled)

            //Coil
            implementation(libs.bundles.coil)

            //Uri
            implementation(libs.uri.kmp)
        }
        androidMain.dependencies {
            //Ktor
            implementation(libs.ktor.client.cio)

            //Koin
            implementation(libs.koin.android)

            //Preview
            implementation(libs.compose.ui.tooling.preview)
        }
        iosMain.dependencies {
            //Ktor
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/commonMain/composeResources", "src/androidMain/res")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
