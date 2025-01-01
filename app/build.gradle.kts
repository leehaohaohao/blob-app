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
    signingConfigs {
        create("release") {
            storeFile = file("C:/Users/lihao/Desktop/my-release-key.jks")  // 密钥库文件的路径
            storePassword = "123456"  // 密钥库密码
            keyAlias = "key0"  // 密钥别名
            keyPassword = "123456"  // 密钥密码
        }
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
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("io.noties.markwon:core:4.6.0")
    implementation ("io.noties.markwon:ext-strikethrough:4.6.0")  //支持 Markdown


}