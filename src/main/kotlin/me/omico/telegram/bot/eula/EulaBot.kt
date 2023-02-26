@file:JvmName("EulaBot")

package me.omico.telegram.bot.eula

import eu.vendeli.tgbot.TelegramBot
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import me.omico.telegram.bot.eula.feature.bilibili.setupBilibiliVideoPurify
import me.omico.telegram.bot.eula.feature.twitter.setupTweetPurify
import me.omico.telegram.bot.utility.autoRetry

suspend fun main(arguments: Array<String>) {
    val parser = ArgParser("EulaBot")
    val token by parser
        .option(
            type = ArgType.String,
            shortName = "t",
            description = "Bot Token",
        )
        .required()
    parser.parse(arguments)
    val bot = TelegramBot(
        token = token,
        commandsPackage = "me.omico.telegram.bot.eula",
    )
    bot.autoRetry {
        onMessage {
            with(data) {
                setupTweetPurify(bot)
                setupBilibiliVideoPurify(bot)
            }
        }
    }
}
