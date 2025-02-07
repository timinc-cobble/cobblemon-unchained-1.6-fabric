package us.timinc.mc.cobblemon.unchained.modules

import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction
import com.cobblemon.mod.common.api.spawning.spawner.PlayerSpawnerFactory
import com.cobblemon.mod.common.pokemon.IVs
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.unchained.api.AbstractBoostConfig
import us.timinc.mc.cobblemon.unchained.api.AbstractBooster
import us.timinc.mc.cobblemon.unchained.api.AbstractInfluenceBooster

object IvBooster : AbstractBooster<IvBoosterConfig>(
    "ivBooster",
    IvBoosterConfig::class.java
) {
    override fun subInit() {
        PlayerSpawnerFactory.influenceBuilders.add { IvBoosterInfluence(it, config, ::debug) }
    }
}

class IvBoosterInfluence(
    override val player: ServerPlayer,
    override val config: IvBoosterConfig,
    override val debug: (String) -> Unit
): AbstractInfluenceBooster(player, config, debug) {
    override fun boost(
        action: PokemonSpawnAction,
        pokemon: Pokemon,
        species: ResourceLocation,
        form: String,
        points: Int
    ) {
        debug("${player.name.string} wins with $points points, $points perfect IVs")
        if (points <= 0) {
            debug("conclusion: player didn't get any perfect IVs")
            return
        }
        if (action.props.ivs == null) action.props.ivs = IVs.createRandomIVs(points)
        debug("conclusion: set $points IVs to perfect")
    }
}

class IvBoosterConfig : AbstractBoostConfig() {
    override val koStreakPoints = 0
    override val koCountPoints = 0
    override val captureStreakPoints = 1
    override val captureCountPoints = 0
    override val thresholds: Map<Int, Int> = mutableMapOf(Pair(5, 1), Pair(10, 2), Pair(20, 3), Pair(30, 4))
}