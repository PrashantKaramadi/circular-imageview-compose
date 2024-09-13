# CircularImageView Library

**CircularImageView** is a Jetpack Compose library that provides a customizable circular image view component. It allows you to display images in a circular shape with various customization options, including borders, loading indicators, and click handling.

## Features

- **Circular Image Display**: Renders images in a circular shape.
- **Customizable Size**: Set height and width independently.
- **Image Loading**:
  - Load images from a URL or drawable resource.
  - Provide placeholder and error images.
- **Loading Indicator**: Optionally display a loading spinner while the image loads.
- **Click Handling**: Handle click events with customizable actions.
- **Border Customization**: Configure border color and width.

## Installation

### Gradle

Add the following to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.pk.circularimageview:circularimageview:1.0.0'
}
```
## Usage
### Example with Drawable Resource
```
@Composable
fun CircleImage() {
    CircularImageView(
        imageRes = R.drawable.ic_launcher_background, // Pass drawable resource
        contentDescription = "Profile Picture",
        height = 120.dp,
        width = 120.dp,
        borderColor = Color.Green,
        borderWidth = 4.dp,
        loadingIndicator = true,
        loadingIndicatorColor = Color.Green,
        onClick = { /* Handle Click */ }
    )
}
```
### Example with URL
Here's an example of how to use the CircularImageView component to load an image from a URL:
```
@Composable
fun NetworkProfileScreen() {
    CircularImageView(
        imageUrl = "https://fastly.picsum.photos/id/703/200/300.jpg?hmac=NKQDtZdJOFjdXxez-vNSj9VLDpR9jIewJzUhoOJoZVI", // Pass URL of the image
        contentDescription = "Network Profile Picture",
        height = 150.dp,
        width = 150.dp,
        borderColor = Color.Blue,
        borderWidth = 3.dp,
        loadingIndicator = true,
        loadingIndicatorColor = Color.Blue,
        onClick = { /* Handle Click */ }
    )
}

```
## Parameters
- **imageUrl (String?)**: URL of the image to load (for remote images). If null, the imageRes will be used.
- **imageRes (Int?)**: Resource ID of the drawable image (for local images). If null and imageUrl is also null, a placeholder or error image will be used.
- **contentDescription (String?)**: Descriptive text for accessibility.
- **height (Dp)**: Height of the circular image (default: 100.dp).
- **width (Dp)**: Width of the circular image (default: 100.dp).
- **borderColor (Color)**: Color of the border around the image (default: Transparent).
- **borderWidth (Dp)**: Width of the border (default: 2.dp).
- **hasBorder (Boolean)**: Whether to show a border around the image (default: true).
- **backgroundColor (Color)**: Background color of the image container (default: Transparent).
- **placeholder (Painter?)**: Placeholder image shown while loading.
- **errorImage (Painter?)**: Fallback image if loading fails.
- **loadingIndicator (Boolean)**: Whether to show a loading indicator (default: false).
- **loadingIndicatorColor (Color)**: Color of the loading indicator (default: Gray).
- **onClick (() -> Unit)?**: Optional click handler for the image.
- **modifier (Modifier)**: Modifier to apply to the image container.
 ## loadImageFromUrl
- **Purpose**: Asynchronously loads an image from a provided URL and returns it as a Bitmap.
- **Parameters**:
- **url**: The URL of the image to load.
- **Behavior**: Utilizes Dispatchers.IO for network operations. Handles exceptions and logs errors if image loading fails.
  
## Contact
For any questions or support, please reach out via prashant.karamadi31@gmail.com


