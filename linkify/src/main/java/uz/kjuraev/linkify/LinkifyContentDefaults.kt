package uz.kjuraev.linkify

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

/**
 * Created by: androdev
 * Date: 03-11-2024
 * Time: 1:41 PM
 * Email: Kjuraev.001@mail.ru
 */

object LinkifyContentDefaults {
    val defaultSpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    )

    val defaultLinkMatcher = LinkMatcher.webUrlMatcher

    val defaultWordDividers = setOf(' ', '\n')
}