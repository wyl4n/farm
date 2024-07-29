package com.redepotter.souls.farm

import com.redepotter.souls.farm.command.AxeCommand
import com.redepotter.souls.farm.command.RemoverCommand
import com.redepotter.souls.farm.command.SellCommand
import com.redepotter.souls.farm.listener.GeneralListener
import com.redepotter.souls.farm.misc.plugin.CustomPlugin

class FarmPlugin : CustomPlugin() {


    companion object {
        lateinit var instance: FarmPlugin
            private set
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        registry()
        logger.info("[Souls] Farm loaded.")
    }

    private fun registry() {
        registerListener(GeneralListener())
        registerCommands(
            AxeCommand(),
            RemoverCommand(),
            SellCommand()
        )
    }
}
