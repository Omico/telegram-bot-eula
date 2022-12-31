package me.omico.telegram.bot.eula.feature.twitter

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.message
import eu.vendeli.tgbot.core.ManualHandlingDsl
import eu.vendeli.tgbot.interfaces.sendAsync
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.internal.onFailure
import me.omico.telegram.bot.utility.deleteMessage
import me.omico.telegram.bot.utility.mention

internal suspend fun ManualHandlingDsl.setupTweetPurify(bot: TelegramBot) = onMessage {
    val message = data
    val text = message.text ?: return@onMessage
    val user = message.from ?: return@onMessage
    if (!tweetRegex.matches(text)) return@onMessage
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
        .sendAsync(to = message.chat.id, via = bot)
        .await()
        .onFailure(::println)
    bot.deleteMessage(message)
}

private val tweetRegex =
    "(http(s)?://)(twitter.com)(/)([a-zA-Z0-9_]+)(/status/)(\\d+)((\\?.*)?)".toRegex()
