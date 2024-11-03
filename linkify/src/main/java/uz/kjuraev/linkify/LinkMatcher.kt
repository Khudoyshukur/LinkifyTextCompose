package uz.kjuraev.linkify

import android.util.Patterns

/**
 * Created by: androdev
 * Date: 03-11-2024
 * Time: 1:40 PM
 * Email: Kjuraev.001@mail.ru
 */

interface LinkMatcher {
    fun isLink(text: String): Boolean

    companion object {
        val webUrlMatcher = object : LinkMatcher {
            override fun isLink(text: String): Boolean {
                return Patterns.WEB_URL.matcher(text).matches()
            }
        }

        val phoneNumberMatcher = object : LinkMatcher {
            override fun isLink(text: String): Boolean {
                return Patterns.PHONE.matcher(text).matches()
            }
        }

        val emailMatcher = object : LinkMatcher {
            override fun isLink(text: String): Boolean {
                return Patterns.EMAIL_ADDRESS.matcher(text).matches()
            }
        }
    }
}

