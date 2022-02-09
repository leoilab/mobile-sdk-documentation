# Image suitability prediction - Android 

The `SuitabilityPredictionClient` provides the functionality to run images through a suitability model.
In this case, "suitability" is specific to use for dermatology, and the model checks if the photo quality is good enough, as well as whether there is skin present in the photo or not.

It will return its confidence in all the following properties for any given image:
* Clinically normal skin
* Skin not visible
* No evaluation possible
* Lesion

Where "no evaluation possible" means that the quality of the photo is very bad (e.g. due to blur or bad lighting).

## Installation

1. To use the library in your project add the following line in your module gradle build file:

```gradle
implementation 'com.omhu:suitability-prediction:1.0.1'
```

2. Since the library is hosted in a private Maven repository you need to include the repository in your top level gradle build file:

```gradle
allprojects {
    repositories {
        // Your other repositories such as google() and mavenCentral() go here
        maven { 
            // Repository access token should be provided in the URL
            url "https://www.myget.org/F/omhu-partner/auth/[REPOSITORY ACCESS TOKEN]/maven" 
        }
    }
}
```

## Usage

### Initialization

The `SuitabilityPredictionClient` must be initialized with a context and private API key:

```kotlin
val suitabilityPredictionClient = SuitabilityPredictionClient(context, [YOUR_API_KEY_HERE])
```

### Running the predictor

`SuitabilityPredictionClient` offers a single predict function that takes a bitmap to run predictions on:

```kotlin
val suitabilityResults = suitabilityPredictionClient.predict(bitmap)
```

The predict function returns a `SuitabilityResult` which describes the confidence in these properties:

```kotlin
data class SuitabilityResult(
    val cns: Float, // Clinically normal skin
    val snv: Float, // Skin not visible
    val nep: Float, // No evaluation possible
    val lesion: Float // Lesion
) 
```

### Considerations

The suitability prediction client library has a transitive dependency on the OpenCV library for preprocessing input images. This is quite a big library, which means it has a significant impact on the APK size for the consumers. We understand this is not ideal and have plans to address that in the near future. In the meantime, you can exclude the uncommon ABIs in your project to reduce the final APK size. To do so add the following in your module build file:

```gradle
android {
    defaultConfig {
        ndk {
            abiFilters 'arm64-v8a', 'armeabi-v7a'
        }
    }
}
```