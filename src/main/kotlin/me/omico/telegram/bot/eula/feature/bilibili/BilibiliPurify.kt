package me.omico.telegram.bot.eula.feature.bilibili

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.message
import eu.vendeli.tgbot.core.ManualHandlingDsl
import eu.vendeli.tgbot.interfaces.sendAsync
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.onFailure
import me.omico.telegram.bot.utility.deleteMessage
import me.omico.telegram.bot.utility.mention

internal suspend fun ManualHandlingDsl.setupBilibiliVideoPurify(bot: TelegramBot) = onMessage {
    val message = data
    val text = message.text ?: return@onMessage
    val user = message.from ?: return@onMessage
    if (!videoRegex.matches(text)) return@onMessage
    val url = text.substringBefore("?")
    println("Received tweet link: $url")
    buildString {
        append("From").append(" ").appendLine(user.mention)
        append(
            url.replace(".", "\\.")
                .replace("_", "\\_")
        )
    }.also(::println).let(::message)
        .options { parseMode = ParseMode.MarkdownV2 }
        .sendAsync(to = message.chat.id, via = bot)
        .await()
        .onFailure(::println)
    bot.deleteMessage(message)
}

private val videoRegex =
    "(http(s)?://)(m|www).(bilibili.com)(/video/)(BV[a-zA-Z0-9_]+)((/)?)((\\?.*)?)".toRegex()
