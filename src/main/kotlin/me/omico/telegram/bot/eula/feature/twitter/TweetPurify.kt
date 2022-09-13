package me.omico.telegram.bot.eula.feature.twitter

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.core.ManualHandlingDsl
import me.omico.telegram.bot.eula.utility.deleteMessage
import me.omico.telegram.bot.eula.utility.sendMessage

internal suspend fun ManualHandlingDsl.setupTweetPurify(bot: TelegramBot) = onMessage {
    val message = data
    val text = message.text ?: return@onMessage
    if (!tweetRegex.matches(text)) return@onMessage
    val tweetUrl = text.substringBefore("?")
    println("Received tweet link: $tweetUrl")
    bot.sendMessage(message.chat) {
        "From ${message.from!!.firstName}\n" +
            tweetUrl.replace("twitter.com", "vxtwitter.com")
    }
    bot.deleteMessage(message)
}

private val tweetRegex =
    "(http(s)?://)(twitter.com)(/)([a-zA-Z0-9_]+)(/status/)(\\d+)((\\?.*)?)".toRegex()
