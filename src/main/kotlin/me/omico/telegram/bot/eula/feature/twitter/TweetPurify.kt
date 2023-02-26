package me.omico.telegram.bot.eula.feature.twitter

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.message
import eu.vendeli.tgbot.interfaces.sendAsync
import eu.vendeli.tgbot.types.Message
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.internal.onFailure
import me.omico.telegram.bot.utility.deleteMessage
import me.omico.telegram.bot.utility.mention

internal suspend fun Message.setupTweetPurify(bot: TelegramBot) {
    val text = text ?: return
    val user = from ?: return
    if (!tweetRegex.matches(text)) return
    val tweetUrl = text.substringBefore("?")
    println("Received tweet link: $tweetUrl")
    buildString {
        append("From").append(" ").appendLine(user.mention)
        append(
            tweetUrl
                .replace("twitter.com", "vxtwitter.com")
                .replace(".", "\\.")
                .replace("_", "\\_")
        )
    }.also(::println).let(::message)
        .options { parseMode = ParseMode.MarkdownV2 }
        .sendAsync(to = chat.id, via = bot)
        .await()
        .onFailure(::println)
    bot.deleteMessage(this)
}

private val tweetRegex =
    "(http(s)?://)(twitter.com)(/)([a-zA-Z0-9_]+)(/status/)(\\d+)((\\?.*)?)".toRegex()
