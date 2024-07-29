package com.redepotter.souls.farm.misc.util

import com.redepotter.souls.farm.FarmPlugin
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.material.MaterialData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * This is a chainable builder for [ItemStack]s in [Bukkit]
 * <br></br>
 * Example Usage:<br></br>
 * `ItemStack is = new ItemBuilder(Material.LEATHER_HELMET).amount(2).data(4).durability(4).enchantment(Enchantment.ARROW_INFINITE).enchantment(Enchantment.LUCK, 2).name(ChatColor.RED + "the name").lore(ChatColor.GREEN + "line 1").lore(ChatColor.BLUE + "line 2").color(Color.MAROON).build();`
 *
 * @author MiniDigger
 * @version 1.2
 */
class ItemBuilder {
    private val `is`: ItemStack

    /**
     * Inits the builder with the given [Material]
     *
     * @param mat the [Material] to start the builder from
     * @since 1.0
     */
    constructor(mat: Material?) {
        `is` = ItemStack(mat)
    }

    /**
     * Inits the builder with the given [ItemStack]
     *
     * @param is the [ItemStack] to start the builder from
     * @since 1.0
     */
    constructor(`is`: ItemStack) {
        this.`is` = `is`
    }

    /**
     * Changes the amount of the [ItemStack]
     *
     * @param amount the new amount to set
     * @return this builder for chaining
     * @since 1.0
     */
    fun amount(amount: Int): ItemBuilder {
        `is`.amount = amount
        return this
    }

    /**
     * Changes the display name of the [ItemStack]
     *
     * @param name the new display name to set
     * @return this builder for chaining
     * @since 1.0
     */
    fun name(name: String?): ItemBuilder {
        val meta = `is`.itemMeta
        meta.displayName = name
        `is`.setItemMeta(meta)
        return this
    }

    /**
     * Adds a new line to the lore of the [ItemStack]
     *
     * @param name the new line to add
     * @return this builder for chaining
     * @since 1.0
     */
    fun lore(name: String?): ItemBuilder {
        val meta = `is`.itemMeta
        var lore = meta.lore
        if (lore == null) {
            lore = ArrayList()
        }
        lore.add(name)
        meta.lore = lore
        `is`.setItemMeta(meta)
        return this
    }

    /**
     * Changes the durability of the [ItemStack]
     *
     * @param durability the new durability to set
     * @return this builder for chaining
     * @since 1.0
     */
    fun durability(durability: Int): ItemBuilder {
        `is`.durability = durability.toShort()
        return this
    }

    /**
     * Changes the data of the [ItemStack]
     *
     * @param data the new data to set
     * @return this builder for chaining
     * @since 1.0
     */
    @Suppress("deprecation")
    fun data(data: Int): ItemBuilder {
        `is`.data = MaterialData(`is`.type, data.toByte())
        return this
    }

    /**
     * Adds an [Enchantment] with the given level to the [ItemStack]
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return this builder for chaining
     * @since 1.0
     */
    fun enchantment(enchantment: Enchantment?, level: Int): ItemBuilder {
        `is`.addUnsafeEnchantment(enchantment, level)
        return this
    }

    /**
     * Adds an [Enchantment] with the level 1 to the [ItemStack]
     *
     * @param enchantment the enchantment to add
     * @return this builder for chaining
     * @since 1.0
     */
    fun enchantment(enchantment: Enchantment?): ItemBuilder {
        `is`.addUnsafeEnchantment(enchantment, 1)
        return this
    }

    /**
     * Changes the [Material] of the [ItemStack]
     *
     * @param material the new material to set
     * @return this builder for chaining
     * @since 1.0
     */
    fun type(material: Material?): ItemBuilder {
        `is`.type = material
        return this
    }

    /**
     * Clears the lore of the [ItemStack]
     *
     * @return this builder for chaining
     * @since 1.0
     */
    fun clearLore(): ItemBuilder {
        val meta = `is`.itemMeta
        meta.lore = ArrayList()
        `is`.setItemMeta(meta)
        return this
    }

    /**
     * Clears the list of [Enchantment]s of the [ItemStack]
     *
     * @return this builder for chaining
     * @since 1.0
     */
    fun clearEnchantments(): ItemBuilder {
        for (e in `is`.enchantments.keys) {
            `is`.removeEnchantment(e)
        }
        return this
    }

    /**
     * Sets the [Color] of a part of leather armor
     *
     * @param color the [Color] to use
     * @return this builder for chaining
     * @since 1.1
     */
    fun color(color: Color?): ItemBuilder {
        if (`is`.type == Material.LEATHER_BOOTS || `is`.type == Material.LEATHER_CHESTPLATE || `is`.type == Material.LEATHER_HELMET || `is`.type == Material.LEATHER_LEGGINGS) {
            val meta = `is`.itemMeta as LeatherArmorMeta
            meta.color = color
            `is`.setItemMeta(meta)
            return this
        } else {
            throw IllegalArgumentException("color() only applicable for leather armor!")
        }
    }

    /**
     * ~~wearing the item~~ (later) or consuming it
     *
     * @param type      the [PotionEffectType] to apply
     * @param duration  the duration in ticks (-1 for endless)
     * @param amplifier the amplifier of the effect
     * @param ambient   ambient status
     * @return this builder for chaining
     * @since 1.2
     */
    fun effect(type: PotionEffectType?, duration: Int, amplifier: Int, ambient: Boolean): ItemBuilder {
        effect(PotionEffect(type, duration, amplifier, ambient))
        return this
    }

    /**
     * ~~wearing the item~~ (later) or consuming it
     *
     * @param effect the effect to apply
     * @return this builder for chaining
     * @since 1.2
     */
    fun effect(effect: PotionEffect): ItemBuilder {
        if (!listener) {
            Bukkit.getPluginManager().registerEvents(this as Listener, plugin)
            listener = true
        }
        var name = `is`.itemMeta.displayName
        while (effects.containsKey(name)) {
            name = "$name#"
        }
        effects[name] = effect
        return this
    }

    /**
     * ~~wearing the item~~ (later) or consuming it
     *
     * @param type      the [PotionEffectType] to apply
     * @param duration  the duration in ticks (-1 for endless)
     * @param amplifier the amplifier of the effect
     * @return this builder for chaining
     * @since 1.2
     */
    fun effect(type: PotionEffectType?, duration: Int, amplifier: Int): ItemBuilder {
        effect(PotionEffect(type, if (duration == -1) 1000000 else duration, amplifier))
        return this
    }

    /**
     * ~~wearing the item~~ (later) or consuming it
     *
     * @param type     the [PotionEffectType] to apply
     * @param duration the duration (-1 for endless)
     * @return this builder for chaining
     * @since 1.2
     */
    fun effect(type: PotionEffectType?, duration: Int): ItemBuilder {
        effect(PotionEffect(type, if (duration == -1) 1000000 else duration, 1))
        return this
    }

    /**
     * Builds the [ItemStack]
     *
     * @return the created [ItemStack]
     * @since 1.0
     */
    fun build(): ItemStack {
        return `is`
    }

    @EventHandler
    fun onItemConsume(e: PlayerItemConsumeEvent) {
        if (e.item.hasItemMeta()) {
            val copy = effects.clone() as HashMap<String, PotionEffect>
            var name = e.item.itemMeta.displayName
            while (copy.containsKey(name)) {
                e.player.addPotionEffect(copy[name], true)
                copy.remove(name)
                name += "#"
            }
        }
    }

    companion object {
        private val plugin: FarmPlugin = FarmPlugin.instance
        private var listener = false
        private val effects = HashMap<String, PotionEffect>()
    }
}
