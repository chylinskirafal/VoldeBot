package pl.chylu.VoldeBot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;
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
    private static List<Member> users;

    private static ArrayList<String> replyAnswer = new ArrayList<>();

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
        commandData.add(Commands.slash("cord", "serio?"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "update":
                update(event);
                event.deferReply().queue();
                break;
            case "cord":
                event.reply("W sprawie Corda daj spokój. Nie przekonasz go do niczego. " + event.getUser().getAsMention() + " Wyjaśniłem?").queue();
            default:
                break;
        }
    }

    void update(SlashCommandInteractionEvent event) {
        try {
            db = new DbConnect();
            Guild guild = event.getGuild();

            categories = guild.getCategories();
            channelVoice = guild.getVoiceChannels();
            channelsText = guild.getTextChannels();
            roles = guild.getRoles();
            guild.loadMembers().onSuccess(members -> {
                users = members;
                replyAnswer.add("\nużytkownicy");
                for (Member member : members) {
                    users.add(member);
                    replyAnswer.add(member.getUser().getId());
                    String answers = String.join(", " + replyAnswer);
                    event.getHook().sendMessage(answers).queue();
                }
                replyAnswer.add("Koniec udostępniania danych");
            });
            replyAnswer.add("Kategorie");
            for (Category category : categories) {
                replyAnswer.add(category.getName());
                replyAnswer.add(category.getId());
            }
            replyAnswer.add("Kanały głosowe");
            for (Channel voice : channelVoice) {
                replyAnswer.add(voice.getName());
                replyAnswer.add(voice.getId());
            }
            replyAnswer.add("Kanały tekstowe");
            for (Channel text : channelsText) {
                replyAnswer.add(text.getName());
                replyAnswer.add(text.getId());
            }
            replyAnswer.add("Role");
            for (Role role : roles) {
                replyAnswer.add(role.getName());
                replyAnswer.add(role.getId());
            }
            replyAnswer.add("Koniec udostępniania danych");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


