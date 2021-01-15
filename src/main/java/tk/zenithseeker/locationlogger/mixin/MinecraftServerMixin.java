package tk.zenithseeker.locationlogger.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;

import java.util.function.BooleanSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import tk.zenithseeker.locationlogger.LocationLogger;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow
	private int ticks;
	@Shadow
	private PlayerManager playerManager;

	@Inject(at=@At(value="TAIL"), method="tick(Ljava/util/function/BooleanSupplier;)V")
	public void tick(BooleanSupplier bs, CallbackInfo ci) {
		if(ticks % 600 == 0)
			LocationLogger.log(playerManager.getPlayerList(), ticks % 6000 == 0);
	}
}
