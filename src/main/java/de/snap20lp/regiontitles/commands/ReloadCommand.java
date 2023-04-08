package de.snap20lp.regiontitles.commands;

import de.snap20lp.regiontitles.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender)
            return true;

        Player player = (Player) sender;
        if(player.hasPermission("regiontitles.reload")) {
            Core.getInstance().loadRegions();
            player.sendMessage("§aReloaded RegionTitles");
        }


        return true;
    }
}
