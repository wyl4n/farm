package com.redepotter.souls.farm.misc.plugin

import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.ViewFrame
import me.saiintbrisson.bukkit.command.BukkitFrame
import me.saiintbrisson.minecraft.command.message.MessageType
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin


abstract class CustomPlugin : JavaPlugin() {
    protected var bukkitFrame: BukkitFrame? = null
    private val viewFrame: ViewFrame = ViewFrame.create(this)

    override fun onEnable() {
    }

    override fun onDisable() {
    }

    override fun onLoad() {
    }

    protected fun registerListener(vararg listeners: Listener?) {
        for (listener in listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this)
        }

        logger.info { "Listeners registered." }
    }

    protected fun registerCommands(vararg objects: Any?) {
        val frame = BukkitFrame(this)
        val messageHolder = frame.messageHolder

        messageHolder.setMessage(MessageType.NO_PERMISSION, "§cVocê não possuí permissão para utilizar este comando.")
        messageHolder.setMessage(MessageType.INCORRECT_USAGE, "§cUtilize /{usage}.")

        frame.registerCommands(*objects)
    }

    protected fun registerViews(vararg views: View) {
        viewFrame.with(*views).register()
    }


    private fun colorize(s: String): String {
        return s.replace("&", "§")
    }
}
