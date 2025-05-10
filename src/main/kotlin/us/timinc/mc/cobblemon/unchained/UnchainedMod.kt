package us.timinc.mc.cobblemon.unchained

import net.fabricmc.api.ModInitializer
import us.timinc.mc.cobblemon.unchained.modules.HiddenBooster
import us.timinc.mc.cobblemon.unchained.modules.IvBooster
import us.timinc.mc.cobblemon.unchained.modules.ShinyBooster
import us.timinc.mc.cobblemon.unchained.modules.SpawnChainer

object UnchainedMod : ModInitializer {
    @Suppress("unused", "MemberVisibilityCanBePrivate")
    const val MOD_ID = "unchained"

    override fun onInitialize() {
        ShinyBooster.initialize()
        HiddenBooster.initialize()
        IvBooster.initialize()
        SpawnChainer.initialize()
    }
}