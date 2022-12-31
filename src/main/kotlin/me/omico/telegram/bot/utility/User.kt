package me.omico.telegram.bot.utility

import eu.vendeli.tgbot.types.User

val User.mention: String
    get() = "[${username ?: firstName}](tg://user?id=$id)"
