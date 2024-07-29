package com.redepotter.souls.farm.listener

import com.redepotter.souls.farm.item.Item
import com.redepotter.souls.farm.misc.util.ActionBarUtil
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockGrowEvent
import org.bukkit.event.player.PlayerInteractEvent

class GeneralListener : Listener {

    private val actionBarUtil = ActionBarUtil()
    private val item = Item()

    @EventHandler
    fun onBlockGrow(event: BlockGrowEvent) {
        val block = event.block
        if (block.type == Material.CARROT) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block

        if (block.type == Material.CARROT) {
            val player = event.player
            val itemInHand = player.inventory.itemInHand

            if (!item.axeItem(itemInHand, player.name)) {
                event.isCancelled = true
                player.sendMessage("§cVocê precisa de um machado especial para quebrar esta plantação!")
                return
            }

            val blockData = block.data.toInt()

            if (blockData < 7) {
                event.isCancelled = true
                player.sendMessage("§cA cenoura ainda não está madura!")
                return
            }

            item.increaseCarrotsBroken(player.name)

            val level = item.getAxeLevel(player.name)
            val quantity = 1 + level

            player.inventory.addItem(item.carrot)
            actionBarUtil.send("§6Cenoura §l(+$quantity)", player)

            block.type = Material.CARROT
            block.data = 0

            val updatedAxeItem = item.getAxe(player.name)
            player.inventory.itemInHand = updatedAxeItem

            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val action = event.action
        val itemInHand = event.player.inventory.itemInHand

        if ((action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) &&
            item.shearItem(itemInHand)) {
            val clickedBlock = event.clickedBlock

            if (clickedBlock != null && clickedBlock.type == Material.CARROT) {
                clickedBlock.type = Material.AIR
                clickedBlock.getState().update(true)

                actionBarUtil.send("§aVocê removeu uma semente de cenoura!", event.player)

                event.isCancelled = true
            }
        }
    }
}
