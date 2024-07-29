package com.redepotter.souls.farm.item

import com.redepotter.souls.farm.misc.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class Item {

    val carrot: ItemStack = ItemBuilder(Material.CARROT)
        .amount(1)
        .name("§6Cenoura")
        .lore("§7Este item pode ser vendido através")
        .lore("§7do comando §8(/vender)§7.")
        .build()

    private val playerProgress = mutableMapOf<String, PlayerItemData>()

    fun getAxe(playerName: String): ItemStack {
        val data = playerProgress.computeIfAbsent(playerName) { PlayerItemData() }
        return createAxeItem(data.level, data.carrotsBroken)
    }

    private fun createAxeItem(level: Int, carrotsBroken: Int): ItemStack {
        val progress = (carrotsBroken % 500).toDouble() / 500.0
        val progressBar = "§a" + "□".repeat((progress * 8).toInt()) + "§8" + "□".repeat(8 - (progress * 8).toInt())

        return ItemBuilder(Material.WOOD_AXE)
            .name("§8Machado de Madeira §l[$level]")
            .lore("§7O ganho de plantações")
            .lore("§7aumenta a cada evolução.")
            .lore("")
            .lore("§7Progresso: $progressBar")
            .lore("§7Cenouras quebradas: $carrotsBroken/500")
            .build()
    }

    fun increaseCarrotsBroken(playerName: String) {
        val data = playerProgress.computeIfAbsent(playerName) { PlayerItemData() }
        data.carrotsBroken++
        if (data.carrotsBroken >= 500) {
            data.carrotsBroken = 0
            data.level++
        }
    }

    fun getAxeLevel(playerName: String): Int {
        return playerProgress[playerName]?.level ?: 0
    }

    fun getCarrotsBroken(playerName: String): Int {
        return playerProgress[playerName]?.carrotsBroken ?: 0
    }
    fun axeItem(itemStack: ItemStack, playerName: String): Boolean {
        if (itemStack.type != Material.WOOD_AXE) return false

        val meta: ItemMeta? = itemStack.itemMeta
        if (meta == null) return false

        val axeItemStack = getAxe(playerName)
        val axeMeta: ItemMeta? = axeItemStack.itemMeta
        if (axeMeta == null) return false

        return meta.hasDisplayName() && axeMeta.hasDisplayName() &&
                meta.displayName == axeMeta.displayName &&
                meta.hasLore() && axeMeta.hasLore() &&
                meta.lore == axeMeta.lore
    }

    val shear: ItemStack = ItemBuilder(Material.SHEARS)
        .name("§8Removedor de Sementes")
        .lore("§7Você pode utilizar para remover")
        .lore("§7sementes de sua plantação.")
        .build()

    fun shearItem(itemStack: ItemStack): Boolean {
        val meta: ItemMeta? = itemStack.itemMeta
        val shearsMeta: ItemMeta? = shear.itemMeta

        return meta != null && shearsMeta != null &&
                meta.hasDisplayName() && shearsMeta.hasDisplayName() &&
                meta.displayName == shearsMeta.displayName &&
                meta.hasLore() && shearsMeta.hasLore() &&
                meta.lore == shearsMeta.lore
    }

    data class PlayerItemData(var level: Int = 0, var carrotsBroken: Int = 0)
}
