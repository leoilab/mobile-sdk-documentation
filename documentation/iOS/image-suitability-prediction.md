# Image suitability prediction - iOS

The `SuitabilityPredictionClient` provides the functionality to run images through a suitability model.
In this case, "suitability" is specific to use for dermatology, and the model checks if the photo quality is good enough, as well as whether there is skin present in the photo or not.

It will return its confidence in all the following properties for any given image:
* Clinically normal skin
* Skin not visible
* No evaluation possible
* Lesion

Where "no evaluation possible" means that the quality of the photo is very bad (e.g. due to blur or bad lighting).

## Installation

The `SuitabilityPredictionClient` supports iOS 14.0 and up.

### CocoaPods

If you use [CocoaPods](https://cocoapods.org/) to manage your dependencies, simply add this to your `Podfile`:

```
 pod 'SuitabilityPredictionClient', :git => 'https://github.com/leoilab/mobile-sdk-distribution.git'
```

## Usage

### Initialization

The `SuitabilityPredictionClient` must be initialized with a private API key in order to work:

```swift
let client: SuitabilityClient = .suitability(apiKey: "[YOUR_API_KEY_HERE]")
```

We recommend initializing and storing the resulting suitability client in an application `Environment` object in order to allow easy access within your application. Initializing `SuitabilityClient` isn't a cheap operation and repeating it is discouraged.

### Running the predictor

`SuitabilityClient` offers a single `predict` function that also has an `async/await` alternative. The `predict` function returns a `SuitabilityResult` which describes the confidence in these properties:

```swift
struct SuitabilityResult: Equatable {
    cns: CGFloat // Clinically normal skin
    snv: CGFloat // Skin not visible
    nep: CGFloat // No evaluation possible
    lesion: CGFloat // Lesion
}
```

When calling `predict` you must provide the function with either a `UIImage`, `CVBuffer`, `AVCapturePhoto` and it will return a `SuitabilityResult` based on that input. There are two ways to consume the function:

#### Combine

`predict` allows you to observe the value through a `Publisher`.

```swift
let cancellable: AnyCancellable?
cancellable = suitabilityClient
    .predict(image)
    .sink(
        receiveCompletion: { completion in 
            switch completion {
                case .complete:
                    break
                case let .failure(error):
                    print(error)
            }
        } receiveValue { output in 
            print(output)
        }
    )
```

#### Async/await

We have provided a version of `predict` that uses the new functionality of Swift's `async/await`:

```swift
Task {
    do {
        let output = try await suitabilityClient.predict(input: image)
    } catch {
        // HANDLE ERROR
        print(error)
    }    
}
```

#### Errors

When calling `predict` there is the possibility of errors to occur during the prediction process.
There are three potential errors:

```swift
enum PredictionError: Error {
    case preprocessing // There was an issue taking the full scale image and downsampling to the models desired dimensions
    case domainMapping(from: Data) // There was an issue with the data returned from the model mapping it to `SuitabilityResult`
    case inference(Error) // There is an issue with the data being handed to the model running the prediction
}
```

### Testing

While running tests it's best to run against some more "defined" results to see how your application logic reacts to the result from the prediction client.
For testing purpose, in order to get deterministic results from the client, you can create a `mock` version:

```swift
let mockClient: SuitabilityClient = .init { _ in 
 .init(
     cns: 1.0,
     snv: 0.0,
     nep: 0.0,
     lesion: 0.0
 )
}
```

This will make sure that you always get the exact same result when calling `mockClient.predict(UIImage())` in your tests.
