plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose)
    alias(libs.plugins.serialization)
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
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            api(compose.components.resources)

            //Coroutine
            api(libs.kotlinx.coroutines.core)

            //Ktor
            implementation(libs.ktor.core )
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.logging)

            //Kotlinx-Serialization
            api(libs.kotlinx.serialization.json)

            //Image Loader
            api(libs.image.loader)

        }
        androidMain.dependencies {
            //Coroutine
            api(libs.kotlinx.coroutines.android)

            //Views
            api(libs.androidx.activity.compose)
            api(libs.androidx.appcompat)
            api(libs.androidx.core)

            //Ktor
            implementation(libs.ktor.client.cio)
        }
        iosMain.dependencies {
            //Ktor
            implementation(libs.ktor.darwin)
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
}
