@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidLibrary)
  id("module.publication")
  kotlin("plugin.serialization") version "2.0.20"
}

kotlin {
  jvm()
  androidTarget {
    publishLibraryVariants("release")
    @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions { jvmTarget.set(JvmTarget.JVM_21) }
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  linuxX64()
  js {
    browser()
  }
  wasmJs()

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.ktor.client.cio)
        implementation(libs.ktor.client.content)
        implementation(libs.ktor.kotlinx.json)
      }
    }
    commonTest { dependencies { implementation(libs.kotlin.test) } }
    androidMain {
      dependencies {
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.ktor.client.android)
      }
    }
    jvmMain { dependencies { implementation(libs.ktor.client.okhttp) } }
    jsMain { dependencies { implementation(libs.ktor.client.js) } }
    wasmJsMain { dependencies { implementation(libs.ktor.client.js) } }
    iosMain { dependencies { implementation(libs.ktor.client.darwin) } }
  }
}

android {
  namespace = "io.github.omydagreat.geminiwrapper"
  compileSdk = libs.versions.android.compileSdk.get().toInt()
  defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
}
