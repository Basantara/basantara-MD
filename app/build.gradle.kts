plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "lastsubmission.capstone.basantaraapps"
    compileSdk = 34

    defaultConfig {
        applicationId = "lastsubmission.capstone.basantaraapps"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_URL", "\"https://basantara-api-2akzaauh3q-et.a.run.app/\"")
        buildConfigField("String", "API_ML", "\"https://dev-basantara-ml-api-2akzaauh3q-et.a.run.app/\"")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
}

dependencies {

    //Layout
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Android KTX
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")


    implementation("com.loopj.android:android-async-http:1.4.11")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")


    implementation("com.github.bumptech.glide:glide:4.16.0")



    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit
    implementation("io.coil-kt:coil:2.4.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //splashscreen
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Tensorflow
    implementation("org.tensorflow:tensorflow-lite-task-vision:0.4.4")


    //uCrop
    implementation("com.github.yalantis:ucrop:2.2.8")


    //cameraX
    val cameraxVersion = "1.3.0"
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")


    //Material design
    implementation ("com.google.android.material:material:1.4.0")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")


    //Paging
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")
    implementation("androidx.paging:paging-runtime:3.3.0")
    implementation("androidx.room:room-paging:2.6.1")





}