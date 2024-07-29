package com.redepotter.souls.farm.command

import com.redepotter.souls.farm.item.Item
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.command.Context
import me.saiintbrisson.minecraft.command.target.CommandTarget
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AxeCommand {

    private val item = Item()

    @Command(
        name = "machado",
        target = CommandTarget.PLAYER
    )
    fun execute(context: Context<CommandSender>) {
        val player = context.sender as Player

        val axeItem = item.getAxe(player.name)
        player.inventory.addItem(axeItem)
    }
}
