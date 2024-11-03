@file:OptIn(ExperimentalTextApi::class)

package uz.kjuraev.linkify

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Created by: androdev
 * Date: 03-11-2024
 * Time: 1:29 PM
 * Email: Kjuraev.001@mail.ru
 */

@RunWith(AndroidJUnit4::class)
class LinkifyContentTest {
    private val exampleLinks = setOf(
        "https://kjuraev.me",
        "http://kjuraev.me",
        "http://kjuraev.me/path",
        "https://kjuraev.me/path",
        "https://asd.me/fkj",
    )

    private val letters = 'a'..'z'

    private fun generateRandomWord(letterCount: Int): String = buildString {
        repeat(letterCount) {
            append(letters.random())
        }
    }

    @Test
    fun content_should_express_original_text_with_word_dividers() {
        val wordDividers = setOf(
            ' ', '&', '\n', '*' // can be any character
        )

        val text = buildString {
            repeat(200) {
                append(generateRandomWord(it % 6))
                append(wordDividers.random())
                append(exampleLinks.random())
                append(wordDividers.random())
            }
        }

        val content = LinkifyContent(text, wordDividers = wordDividers)
        assert(content.getAnnotatedString().text == text)
    }

    @Test
    fun content_should_detect_all_matching_links_and_ignore_others() {
        exampleLinks.forEach { url ->
            listOf(
                "$url this is a text with url",
                "this is a $url text with url",
                "this is a text with url $url"
            ).forEach { urlText ->
                val content = LinkifyContent(originalText = urlText)
                assertSingleUrlText(content, url)
            }
        }
    }

    @Test
    fun content_should_respect_matcher() {
        val link = generateRandomWord(6)
        val matcher = object : LinkMatcher {
            override fun isLink(text: String): Boolean {
                return link == text
            }
        }

        val text = buildString {
            repeat(10) {
                val word = generateRandomWord(5)
                if (word != link) {
                    append(word)
                    append(" ")
                }
            }
            append(link)
        }
        val content = LinkifyContent(originalText = text, linkMatcher = matcher)
        assertSingleUrlText(content, link)
    }

    @Test
    fun content_should_detect_all_matching_links_and_apply_annotations() {
        exampleLinks.forEach { url ->
            listOf(
                "$url this is a text with url",
                "this is a $url text with url",
                "this is a text with url $url"
            ).forEach { urlText ->
                val content = LinkifyContent(originalText = urlText)
                val startingIndex = content.originalText.indexOf(url)
                val endingIndex = startingIndex + url.length

                val urlAnnotation = content.getAnnotatedString().getUrlAnnotations(
                    startingIndex, endingIndex
                ).firstOrNull()
                val spanStyle = content.getAnnotatedString().spanStyles
                    .find { it.start == startingIndex && it.end == endingIndex}

                assertEquals(url, urlAnnotation?.item?.url)
                assertEquals(LinkifyContentDefaults.defaultSpanStyle, spanStyle?.item)
            }
        }
    }

    private fun assertSingleUrlText(content: LinkifyContent, url: String) {
        val startingIndex = content.originalText.indexOf(url)
        val endingIndex = startingIndex + url.length - 1

        for (index in content.originalText.indices) {
            if (index in startingIndex..endingIndex) {
                assertEquals(url, content.getLinkAt(index))
            } else {
                assertEquals(null, content.getLinkAt(index))
            }
        }
    }
}