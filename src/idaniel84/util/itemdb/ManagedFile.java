// 

// 

package idaniel84.util.itemdb;

import idaniel84.base.BasePlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class ManagedFile{
	private static final int BUFFER_SIZE = 8192;
	private final transient File file;

	public ManagedFile(final String filename, final JavaPlugin plugin){
		super();
		this.file = new File(plugin.getDataFolder(), filename);
		if(!this.file.exists()){
			try{
				copyResourceAscii('/' + filename, this.file);
			}catch(IOException ex){
				plugin.getLogger().log(Level.SEVERE, "items.csv has not been loaded", ex);
			}
		}
	}

	public static void copyResourceAscii(final String resourceName, final File file) throws IOException{
		try(final InputStreamReader reader = new InputStreamReader(ManagedFile.class.getResourceAsStream(resourceName), StandardCharsets.UTF_8)){
			final MessageDigest digest = getDigest();
			try(final DigestOutputStream digestStream = new DigestOutputStream(new FileOutputStream(file), digest);
			    final OutputStreamWriter writer = new OutputStreamWriter(digestStream, StandardCharsets.UTF_8)){
				final char[] buffer = new char[8192];
				int length;
				while((length = reader.read(buffer)) >= 0){
					writer.write(buffer, 0, length);
				}
				writer.write("\n");
				writer.flush();
				digestStream.on(false);
				digestStream.write(35);
				digestStream.write(new BigInteger(1, digest.digest()).toString(16).getBytes(StandardCharsets.UTF_8));
			}
		}
	}

	public static MessageDigest getDigest() throws IOException{
		try{
			return MessageDigest.getInstance("MD5");
		}catch(NoSuchAlgorithmException ex){
			throw new IOException(ex);
		}
	}

	public File getFile(){
		return this.file;
	}

	public List<String> getLines(){
		try(final BufferedReader reader = Files.newBufferedReader(Paths.get(this.file.getPath()), StandardCharsets.UTF_8)){
			final List<String> lines = new ArrayList<String>();
			String line;
			while((line = reader.readLine()) != null){
				lines.add(line);
			}
			return lines;
		}catch(IOException ex){
			BasePlugin.getPlugin().getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			return Collections.emptyList();
		}
	}
}