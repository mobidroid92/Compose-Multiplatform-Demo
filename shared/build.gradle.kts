plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
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
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //Coroutine
            api(libs.coroutines.core)

            //Ktor
            implementation(libs.bundles.ktor.common)

            //Kotlinx-Serialization
            api(libs.kotlinx.serialization.json)

            //Image Loader
            api(libs.image.loader)

            //ViewModel
            implementation(libs.bundles.lifecycle)

            //Koin
            implementation(libs.bundles.koin.common)

        }
        androidMain.dependencies {
            //Coroutine
            api(libs.coroutines.android)

            //Views
            api(libs.bundles.androidx)

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

compose.resources {
    publicResClass = true
    generateResClass = always
}
