package us.timinc.mc.cobblemon.unchained.mixins;

@org.spongepowered.asm.mixin.Mixin(com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity.class)
public abstract class EmptyPokeBallEntityMixin extends net.minecraft.world.entity.projectile.ThrowableItemProjectile {
    public EmptyPokeBallEntityMixin(net.minecraft.world.entity.EntityType<?> entityType, net.minecraft.world.level.Level level) {
        super((net.minecraft.world.entity.EntityType<? extends net.minecraft.world.entity.projectile.ThrowableItemProjectile>) entityType, level);
    }

    @org.spongepowered.asm.mixin.gen.Invoker("getCaptureState")
    abstract com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity.CaptureState getCaptureStateInvoker();

    @org.spongepowered.asm.mixin.gen.Invoker("drop")
    abstract void dropInvoker();

    @org.spongepowered.asm.mixin.injection.Inject(method = "onHitEntity", at = @org.spongepowered.asm.mixin.injection.At("HEAD"), remap = false, cancellable = true)
    void onHitEntityMixin(net.minecraft.world.phys.EntityHitResult hitResult, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (getCaptureStateInvoker().equals(com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity.CaptureState.NOT)) {
            if (hitResult.getEntity() instanceof com.cobblemon.mod.common.entity.pokemon.PokemonEntity && !level().isClientSide) {
                com.cobblemon.mod.common.entity.pokemon.PokemonEntity pokemonEntity = (com.cobblemon.mod.common.entity.pokemon.PokemonEntity) hitResult.getEntity();
                java.util.UUID procPlayer = pokemonEntity.getPokemon().getPersistentData().getUUID("proc-player");
                net.minecraft.server.level.ServerPlayer thrower = (net.minecraft.server.level.ServerPlayer) getOwner();

                if (!procPlayer.equals(thrower.getUUID())) {
                    thrower.sendSystemMessage(net.minecraft.network.chat.Component.translatable("unchained.reserved"));
                    dropInvoker();
                    ci.cancel();
                }
            }
        }
    }
}
