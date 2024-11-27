plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.lihao.blob"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lihao.blob"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")          // Retrofit 核心库
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")   // JSON 转换器
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")  // 如果需要支持 RxJava 的话
}