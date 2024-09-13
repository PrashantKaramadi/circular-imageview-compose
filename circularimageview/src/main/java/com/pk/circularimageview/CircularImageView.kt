package com.pk.circularimageview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

/**
 * Composable function to display an image inside a circular shape with optional border and loading indicator.
 *
 * @param imageUrl URL of the image to load. If null, `imageRes` must be provided.
 * @param imageRes Resource ID of the drawable image. Used if `imageUrl` is null.
 * @param contentDescription Descriptive text for accessibility purposes.
 * @param height Height of the circular image. Default is 100.dp.
 * @param width Width of the circular image. Default is 100.dp.
 * @param borderColor Color of the border around the image. Default is transparent.
 * @param borderWidth Width of the border around the image. Default is 2.dp.
 * @param hasBorder Boolean indicating whether to show a border around the image. Default is true.
 * @param backgroundColor Background color of the image container. Default is transparent.
 * @param placeholder A placeholder image to show while the actual image is being loaded. Default is null.
 * @param errorImage An image to display if loading the image fails. Default is null.
 * @param loadingIndicator Boolean indicating whether to show a loading indicator while the image is loading. Default is false.
 * @param loadingIndicatorColor Color of the loading indicator. Default is gray.
 * @param onClick Optional click handler for the image. If null, the image is not clickable.
 * @param modifier Modifier to apply to the image container.
 */
@Composable
fun CircularImageView(
    imageUrl: String? = null,  // For loading image from a URL
    imageRes: Int? = null,     // For loading image from a drawable resource
    contentDescription: String? = null,
    height: Dp = 100.dp,       // Separate height parameter
    width: Dp = 100.dp,        // Separate width parameter
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 2.dp,
    hasBorder: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    placeholder: Painter? = null,
    errorImage: Painter? = null,
    loadingIndicator: Boolean = false,
    loadingIndicatorColor: Color = Color.Gray,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Load image from URL in a coroutine if URL is provided
    if (imageUrl != null) {
        LaunchedEffect(imageUrl) {
            isLoading = true
            bitmap = loadImageFromUrl(imageUrl) // Load image asynchronously
            isLoading = false
        }
    } else if (imageRes != null) {
        // Set isLoading to false when image is loaded from resource
        isLoading = false
    }

    // Determine which painter to use based on available image
    val painter = when {
        bitmap != null -> BitmapPainter(bitmap!!.asImageBitmap()) // For URL image
        imageRes != null -> painterResource(id = imageRes)         // For drawable resource
        else -> placeholder ?: errorImage                          // Fallback to placeholder or error image
    }

    // Create a circular image view with optional border and loading indicator
    Box(
        modifier = modifier
            .size(width = width, height = height) // Set custom width and height
            .clip(CircleShape) // Clip the image to a circular shape
            .background(backgroundColor) // Set the background color
            .let {
                if (hasBorder) {
                    it.border(borderWidth, borderColor, CircleShape) // Add border if specified
                } else it
            }
            .clickable(enabled = onClick != null) { onClick?.invoke() } // Add click functionality if provided
    ) {
        // Display a loading indicator if image is loading and loadingIndicator is true
        if (isLoading && loadingIndicator) {
            CircularProgressIndicator(
                color = loadingIndicatorColor,
                modifier = Modifier.align(Alignment.Center) // Center the loading indicator
            )
        } else {
            // Display the image once loading is complete
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Fit,  // Ensure image fits within the bounds
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize() // Make sure the image fills the Box
                )
            }
        }
    }
}

/**
 * Asynchronously loads an image from a given URL.
 *
 * @param url URL of the image to load.
 * @return A Bitmap of the loaded image, or null if loading fails.
 */
suspend fun loadImageFromUrl(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.connect()
            val inputStream = connection.getInputStream()
            BitmapFactory.decodeStream(inputStream) // Decode the input stream to a Bitmap
        } catch (e: Exception) {
            e.printStackTrace() // Log the error
            null
        }
    }
}
