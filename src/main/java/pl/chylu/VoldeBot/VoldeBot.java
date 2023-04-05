package pl.chylu.VoldeBot;


import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class VoldeBot {

	private static Dotenv config;
	public static ShardManager shardManager;
	public static String guildId = "1085619902892736542";
	public static Guild guildStatic;

	public Dotenv getConfig() {
		return config;
	}

	public ShardManager getShardManager() {
		return shardManager;
	}

	public static void main(String[] args) throws LoginException {
		InitMethod();
		guildStatic = shardManager.getGuildById(guildId);
		SpringApplication.run(VoldeBot.class, args);
	}


	public static void InitMethod() throws LoginException {
		config = Dotenv.configure().load();
		String token = config.get("TOKEN");

		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setActivity(Activity.playing("zgłoszenia wyCHYLenia"));
		builder.enableIntents(GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
				GatewayIntent.MESSAGE_CONTENT,
				GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
		shardManager = builder.build();

		shardManager.addEventListener(new SlashCommands());


	}
}