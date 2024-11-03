package uz.kjuraev.linkify

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString

/**
 * Created by: androdev
 * Date: 03-11-2024
 * Time: 1:22 PM
 * Email: Kjuraev.001@mail.ru
 */

@ExperimentalTextApi
data class LinkifyContent(
    val originalText: String,
    private val spanStyle: SpanStyle = LinkifyContentDefaults.defaultSpanStyle,
    private val linkMatcher: LinkMatcher = LinkifyContentDefaults.defaultLinkMatcher,
    private val wordDividers: Set<Char> = LinkifyContentDefaults.defaultWordDividers
) {
    private var annotatedString: AnnotatedString

    init {
        annotatedString = createAnnotatedString(originalText)
    }

    private fun createAnnotatedString(text: String): AnnotatedString = buildAnnotatedString {
        fun processWord(word: String, wordStartIndex: Int, wordEndIndex: Int) {
            append(word)

            if (linkMatcher.isLink(word)) {
                addStyle(spanStyle, wordStartIndex, wordEndIndex + 1)
                addUrlAnnotation(UrlAnnotation(word), wordStartIndex, wordEndIndex + 1)
            }
        }

        val wordTracker = StringBuilder()
        var wordStartingIndex: Int = -1

        text.forEachIndexed { index, char ->
            if (char in wordDividers) {
                val word = wordTracker.toString()
                processWord(word, wordStartingIndex, index - 1).also {
                    wordTracker.clear()
                    wordStartingIndex = -1
                }

                append(char)
            } else if (index == text.lastIndex) {
                wordTracker.append(char)
                processWord(wordTracker.toString(), wordStartingIndex, index)
            } else {
                wordTracker.append(char)

                if (wordStartingIndex == -1) {
                    wordStartingIndex = index
                }
            }
        }
    }

    fun getAnnotatedString() = annotatedString

    fun getLinkAt(offset: Int): String? {
        return annotatedString
            .getUrlAnnotations(offset, offset)
            .firstOrNull()?.item?.url
    }
}