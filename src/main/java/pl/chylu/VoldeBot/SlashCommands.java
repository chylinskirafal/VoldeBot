package pl.chylu.VoldeBot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SlashCommands extends ListenerAdapter {

    List<CommandData> commandData = new ArrayList<>();
    private static DbConnect db;
    private static List<Category> categories;
    private static List<VoiceChannel> channelVoice;
    private static List<TextChannel> channelsText;
    private static List<Role> roles;
    private static List<User> users;

    public SlashCommands() {
        try {
            db = new DbConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        commandData.add(Commands.slash("update", "zaktualizuj dane w bazie danych"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "update":
                update(event);
                break;
            default:
                break;
        }
    }

    void update(SlashCommandInteractionEvent event) {
        /*Dotenv dotenv = Dotenv.configure().load();
        String token = dotenv.get("TOKEN");
        String guildId = "1085619902892736542";*/

        try {
            Guild guild = VoldeBot.guildStatic;

            categories = guild.getCategories();
            channelVoice = guild.getVoiceChannels();
            channelsText = guild.getTextChannels();
            roles = guild.getRoles();
            List<Member> members = guild.getMembers();
            //users = guild.getMembers().stream().map(Member::getUser).collect(Collectors.toList());


            for (Member member : members) {
                String userTag = member.getUser().getAsTag(); // pobieranie tagu użytkownika
                //users.add(member.getUser());
                System.out.println(userTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


