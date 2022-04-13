# Image outline - Android

The `OutlineGenerator` provides the functionality to detect edges and generate a transparent outline image for any given image.
It is possible to get a white outline, or a colored one.

## Installation

1. To use the library in your project add the following line in your module gradle build file:

```gradle
implementation 'com.omhu:image-outline:1.1.0'
```

2. Since the library is hosted in a private Maven repository you need to include the repository in your top-level gradle build file:

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

The `OutlineGenerator` must be initialized with a context and private API key:

```kotlin
val outlineGenerator = OutlineGenerator(context, [YOUR_API_KEY_HERE])
```

### Generating outlines

Generating the outlines can be done by calling the `outlineImage` function with the following parameters:

```kotlin
outlineGenerator.generateOutline(
    sourcePath = [PATH TO IMAGE YOU WANT TO OUTLINE],
    minimumWidth = [THE WIDTH FOR THE GENERATED OUTLINE, DEFAULTED to 0],
    minimumHeight = [THE HEIGHT FOR THE GENERATED OUTLINE, DEFAULTED to 0],
    outlineType = [OutlineType.OUTLINE_COLOR or OutlineType.OUTLINE_WHITE],
    callback = [CALLBACK FOR OUTLINE COMPLETION]
)
```

Example of a callback:

```kotlin
val callback = object : OutlineGenerator.OnOutlineGeneratedCallback {
    override fun onOutlineGenerated(path: String) {
        // Do something with the generated outline
    }

    override fun onError(reason: Throwable) {
        // Do something with the error
    }
}
```
