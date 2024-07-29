package com.redepotter.souls.farm.misc.util

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

class ActionBarUtil {
    fun send(text: String, player: Player) {
        val chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"$text\"}")
        val packet = PacketPlayOutChat(chatComponent, 2.toByte())
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }
}
