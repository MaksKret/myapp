package com.example.firstapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstapp.ui.theme.FirstappTheme
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Doing the App's theme will allow your composables to inherit styles as defined in your app's theme ensuring consistency across your app.
            FirstappTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text("Wallah")
    }
}

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    FirstappTheme() {
        Surface() {
            Row(modifier = Modifier.padding(all = 8.dp)) {
                Image(
                    painter = painterResource(R.drawable.__4ksihremsmcwwff),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        // Set image size to 40 dp
                        .size(40.dp)
                        // Clip image to be shaped as a circle
                        .clip(CircleShape)
                        // Add a border around the image
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                // Add a horizontal space between the image and the column
                Spacer(modifier = Modifier.width(8.dp))

                var isExpanded by remember { mutableStateOf(false) }
                // surfaceColor will be updated gradually from one color to the other
                val surfaceColor by animateColorAsState(
                    if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                )

                Column(modifier = Modifier.clickable{ isExpanded = !isExpanded }) {
                    Text(
                        text = msg.author,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleSmall
                    )
                    // Add a vertical space between the author and message texts
                    Spacer(modifier = Modifier.height(4.dp))
                    // Surface basically allows to group elements together and add a common design to them
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        shadowElevation = 1.dp,
                        // surfaceColor color will be changing gradually from primary to surface
                        color = surfaceColor,
                        // animateContentSize will change the Surface size gradually
                        modifier = Modifier.animateContentSize().padding(1.dp)
                    ) {
                        Text(
                            text = msg.body,
                            modifier = Modifier.padding(all = 4.dp),
                            // If the message is expanded, we display all its content
                            // otherwise we only display the first line
                            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    FirstappTheme() {
        Conversation(SampleData.conversationSample)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstappTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MessageCardPreview() {
    FirstappTheme() {
        Surface() {
            MessageCard(
                Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}