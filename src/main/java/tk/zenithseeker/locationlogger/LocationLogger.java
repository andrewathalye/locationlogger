package tk.zenithseeker.locationlogger;

import java.time.LocalDate;
import java.time.ZoneId;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import net.minecraft.server.network.ServerPlayerEntity;

public class LocationLogger {
	private static String t = null;
	private static File f = null;
	private static BufferedWriter bw = null;
	private static final String S = "pos/";
	private static final Path P = Paths.get(S);

	public static void log(List<ServerPlayerEntity> l, boolean flush) {
		try {
			if(!Files.exists(P)) {
				Files.createDirectory(P);
			}

			if(!LocalDate.now(ZoneId.of("UTC")).toString().equals(t)) {
				if(bw != null)
					bw.close();
				t = LocalDate.now(ZoneId.of("UTC")).toString();
				f = new File(S+t+".log");
				bw = new BufferedWriter(new FileWriter(f));
			}

			for(ServerPlayerEntity spe : l) {
				bw.write(System.currentTimeMillis()+" "+spe.getName().getString()+" "+spe.getX()+" "+spe.getY()+" "+spe.getZ()+" "+spe.world.getRegistryKey().getValue().toString());
				bw.newLine();
			}

			if(flush)
				bw.flush();
		} catch(Exception e1) {
			e1.printStackTrace();
			try {
				bw.close();
			} catch(Exception e2) {
			}
		}	
	}
}
