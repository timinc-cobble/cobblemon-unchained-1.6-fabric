package us.timinc.mc.cobblemon.unchained

import com.cobblemon.mod.common.api.events.CobblemonEvents
import net.fabricmc.api.ModInitializer
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.unchained.modules.HiddenBooster
import us.timinc.mc.cobblemon.unchained.modules.IvBooster
import us.timinc.mc.cobblemon.unchained.modules.ShinyBooster

object UnchainedMod : ModInitializer {
    @Suppress("unused", "MemberVisibilityCanBePrivate")
    const val MOD_ID = "unchained"

    override fun onInitialize() {
        ShinyBooster.initialize()
        HiddenBooster.initialize()
        IvBooster.initialize()

        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe { event ->
            val player = event.ctx.cause.entity
            if (player !is ServerPlayer) return@subscribe
            val pokemon = event.entity.pokemon
            pokemon.persistentData.putUUID("proc-player", player.uuid)
            pokemon.anyChangeObservable.emit(pokemon)
        }
    }
}