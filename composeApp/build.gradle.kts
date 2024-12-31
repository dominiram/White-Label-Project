import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.buildkonfig.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.codingfeline.buildkonfig") version "0.15.1"
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export("io.github.mirzemehdi:kmpnotifier:1.3.0")
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "https://github.com/dominiram/White-Label-Project/"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "ComposeApp"
            isStatic = true
            export(libs.kmpNotifier)
        }

        pod("FirebaseCore") { linkOnly = true }
        pod("FirebaseMessaging")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }

        commonMain.dependencies {
            //compose main
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //main - viewModel, async image, constraint layout, coil, etc.
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.kotlin.coroutines)
            implementation(libs.compose.webview.multiplatform)
            implementation(libs.coil3.coil.compose)
            implementation(libs.constraintlayout.compose.multiplatform)
            implementation(libs.kamel.image)

            //navigation
            implementation(libs.navigator)
            implementation(libs.navigator.screen.model)
            implementation(libs.navigator.transitions)
            implementation(libs.navigator.koin)
            implementation(libs.tab.navigator)

            //ktor
            implementation(libs.coil.network.ktor)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.bundles.ktor)
            implementation(libs.kotlin.serialization)

            //koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodelNavigation)

            //data storage
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.androidx.datastore.core)


            //image picker
            implementation(libs.peekaboo.ui)
            implementation(libs.peekaboo.image.picker)

            //push notifications
            api(libs.kmpNotifier)

            //permissions
            implementation(libs.permissions)

            //logger
            implementation(libs.logging)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "org.nsquare.whitelabelproject"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.nsquare.whitelabelproject"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

buildkonfig {
    packageName = "whitelabelproject.buildKonfig"
    // objectName = "YourAwesomeConfig" - default is BuildKonfig

    // default config is required
    defaultConfigs {
        buildConfigField(STRING, "CONFIG_NAME", "CONFIG_NAME")
        buildConfigField(STRING, "BASE_URL", "BASE_URL")
        buildConfigField(STRING, "API_KEY", "API_KEY")
    }

    // flavor is passed as a first argument of defaultConfigs
    defaultConfigs("dev") {
        buildConfigField(STRING, "BASE_URL", "base_url_dev")
    }

    defaultConfigs("rtvPancevo") {
        buildConfigField(STRING, "CONFIG_NAME", "rtvPancevo")
        buildConfigField(STRING, "BASE_URL", "https://rtvpancevo.nsquaredapps.com")
        buildConfigField(STRING, "API_KEY", "%Q/JE31Su;H%Z*8.KuHY")
    }

    defaultConfigs("dunavTv") {
        buildConfigField(STRING, "CONFIG_NAME", "tvDunav")
        buildConfigField(STRING, "BASE_URL", "https://dunavtelevizija.nsquaredapps.com")
        buildConfigField(STRING, "API_KEY", "|47_r£8R9<z6y(98)ARs")
    }

    // flavor is passed as a first argument of targetConfigs - is this needed??
    targetConfigs("dev") {
        create("ios") {
            buildConfigField(STRING, "BASE_URL", "devValueIos")
        }

        create("android") {
            buildConfigField(STRING, "BASE_URL", "devValueAndroid")
        }
    }
}
