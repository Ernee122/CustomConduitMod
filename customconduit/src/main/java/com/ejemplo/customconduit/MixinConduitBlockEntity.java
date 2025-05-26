
package com.ejemplo.customconduit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConduitBlockEntity.class)
public class MixinConduitBlockEntity {

    @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
    private static void changeConduitStructure(Level level, BlockPos pos, ConduitBlockEntity conduit, CallbackInfo ci) {
        boolean valid = true;

        // Pilar 3x3 justo debajo del canalizador
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos checkPos = pos.offset(dx, -1, dz);
                BlockState state = level.getBlockState(checkPos);
                if (!state.is(Blocks.PRISMARINE) && !state.is(Blocks.PRISMARINE_BRICKS) && !state.is(Blocks.DARK_PRISMARINE)) {
                    valid = false;
                    break;
                }
            }
        }

        if (valid) {
            conduit.effectBlocks.clear();
            conduit.effectBlocks.add(pos.below());
            conduit.setActive(true);
            ci.cancel();
        }
    }
}
