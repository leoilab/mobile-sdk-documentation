# Image outline - iOS

The `ImageOutlineClient` provides the functionality to detect edges and generate a transparent outline image for any given image.
It is possible to get a white outline, or a colored one.

## Installation

The `ImageOutlineClient` supports iOS 14.0 and up.

### CocoaPods

If you use [CocoaPods](https://cocoapods.org/) to manage your dependencies, simply add this to your `Podfile`:

```
pod 'ImageOutlineClient', :git => 'https://github.com/leoilab/mobile-sdk-distribution.git'
```

## Usage

### Initialization

The `ImageOutlineClient`  must be initialized with a private API key in order to work:

```swift
let outlineClient = ImageOutlineClient.configure(withKey: "[YOUR_API_KEY_HERE]")
```

### Generating outlines

Generating the outlines can be done by calling the `outlineImage` function, using a parameter to specify whether you want a white outline or a color outline:

```swift
let whiteOutlinedImage = outlineClient.outlineImage(UIImage(named: "hand"), .white)
let coloredOutlineImage = outlineClient.outlineImage(UIImage(named: "hand"), .color)
```
