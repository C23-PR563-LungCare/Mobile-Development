# LungCare Mobile Application

## Introduction

LungCare is an application that can reduce misdiagnosis using machine learning models as well as
giving patients an early diagnosis. LungCare is made to give patients a quick diagnosis to their
lung x-ray. This would help patients who have to wait days for their doctors appointment by speeding
up the diagnosis process. The application will also give patients tips and help on handling their
situation so that they can act on it rather than wait for a doctors appointment.

Download APK: https://drive.google.com/file/d/1jKBW07y22U9wNQAtmnO-BRyjpwrnLyFl/view?usp=drive_link

## Screenshots

![Image](https://github.com/C23-PR563-LungCare/Mobile-Development/blob/master/image_1.png)
![Image](https://github.com/C23-PR563-LungCare/Mobile-Development/blob/master/image_2.png)


### Dependencies

```Gradle
dependencies {
    // ui
    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    implementation "androidx.fragment:fragment-ktx:1.5.7"
    implementation "androidx.activity:activity-ktx:1.7.2"
    
    // navigation
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

    // lifecycle
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"

    // datastore
    implementation "androidx.datastore:datastore-preferences:1.0.0"

    // coroutine support
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    // rxjava
    implementation "io.reactivex.rxjava2:rxjava:2.2.19"
    implementation "com.jakewharton.rxbinding2:rxbinding:2.0.0"

    // networking
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation "com.squareup.okhttp3:logging-interceptor:$logging_interceptor_version"

    // cameraX
    implementation "androidx.camera:camera-camera2:$camerax_version"
    implementation "androidx.camera:camera-lifecycle:$camerax_version"
    implementation "androidx.camera:camera-view:$camerax_version"

    // firebase support
    implementation platform('com.google.firebase:firebase-bom:32.1.0')
    implementation 'com.google.firebase:firebase-auth-ktx'
    implementation 'com.google.android.gms:play-services-auth:20.5.0'

    // hilt
    implementation "com.google.dagger:hilt-android:2.44"
    kapt "com.google.dagger:hilt-android-compiler:2.44"
}
```
### Clone this App

To get started with this project, you can clone it to your local machine by running the following command:

```bash
$ git clone https://github.com/C23-PR563-LungCare/Mobile-Development.git
```