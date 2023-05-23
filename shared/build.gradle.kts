plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.20"
}

kotlin {
    android()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    val KTOR_VERSION = "2.3.0"
    val COROUTINE_VERSION = "1.6.4"
    sourceSets {
        val commonMain by getting {
            dependencies {

                //Compose
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)

                //Coroutine
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINE_VERSION")

                //Ktor
                implementation("io.ktor:ktor-client-core:$KTOR_VERSION" )
                implementation("io.ktor:ktor-client-content-negotiation:$KTOR_VERSION")
                implementation("io.ktor:ktor-client-logging:$KTOR_VERSION")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$KTOR_VERSION")
                implementation("ch.qos.logback:logback-classic:1.2.11")

                //Kotlinx-Serialization
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                //Image Loader
                api("io.github.qdsfdhvh:image-loader:1.4.2")

            }
        }
        val androidMain by getting {
            dependencies {

                //Coroutine
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINE_VERSION")

                //Views
                api("androidx.activity:activity-compose:1.6.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.9.0")

                //Ktor
                implementation("io.ktor:ktor-client-cio:$KTOR_VERSION")

                //Volley
                implementation ("com.android.volley:volley:1.2.1")

            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                //Ktor
                implementation("io.ktor:ktor-client-darwin:$KTOR_VERSION")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
