package uz.kjuraev.linkify

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

/**
 * Created by: androdev
 * Date: 03-11-2024
 * Time: 2:59 PM
 * Email: Kjuraev.001@mail.ru
 */

@Composable
@ExperimentalTextApi
fun LinkifyText(
    content: LinkifyContent,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onUrlClicked: ((String) -> Unit)? = null
) {
    if (onUrlClicked != null) {
        ClickableText(
            text = content.getAnnotatedString(),
            modifier = modifier,
            style = style,
            softWrap = softWrap,
            overflow = overflow,
            maxLines = maxLines,
            onTextLayout = onTextLayout,
            onClick = { offset ->
                onUrlClicked?.let {
                    val url = content.getLinkAt(offset)
                    if (!url.isNullOrBlank()) {
                        onUrlClicked(url)
                    }
                }
            }
        )
    } else {
        BasicText(
            text = content.getAnnotatedString(),
            modifier = modifier,
            style = style,
            softWrap = softWrap,
            overflow = overflow,
            maxLines = maxLines,
            onTextLayout = onTextLayout,
        )
    }
}