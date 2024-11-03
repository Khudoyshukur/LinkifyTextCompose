package uz.kjuraev.linkify

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.kjuraev.linkify.ui.theme.LinkifyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinkifyComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LinkifyDemo(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalTextApi::class)
fun MainActivity.LinkifyDemo(modifier: Modifier) {
    Column(modifier) {
        var text by remember { mutableStateOf("") }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Original Text",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = text,
            onValueChange = { text = it }
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Linkified Text",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        LinkifyText(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 90.dp)
                .padding(16.dp)
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    brush = SolidColor(Color.Black)
                )
                .padding(8.dp),
            content = LinkifyContent(originalText = text),
            onUrlClicked = { url ->
                Log.e("LLLL", url)
            }
        )
    }
}