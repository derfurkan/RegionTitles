package de.snap20lp.regiontitles.commands;

import de.snap20lp.regiontitles.Core;
import de.snap20lp.regiontitles.Region;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender)
            return true;
        try {
            Player player = (Player) sender;
            if(!player.hasPermission("regiontitles.create")) {
                player.sendMessage("§cYou don't have the permission to do this!");
                return true;
            }
            Region regionCreationPlayer = Core.getInstance().regionCreationPlayers.get(player.getUniqueId());
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("finish")) {
                    if (regionCreationPlayer == null) {
                        player.sendMessage("§cPlease use §e/createregion <regionname> §cfirst!");
                        return true;
                    }

                    if (regionCreationPlayer.getRegionLocationOne() == null || regionCreationPlayer.getRegionLocationTwo() == null) {
                        player.sendMessage("§cYou need to set both locations before you can finish the creation!");
                        return true;
                    }

                    if (Core.getInstance().getConfig().getConfigurationSection("Regions").getKeys(false).contains(regionCreationPlayer.getRegionName())) {
                        player.sendMessage("§cThis region already exists!");
                        return true;
                    }

                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterTitle", regionCreationPlayer.getRegionEnterTitle());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterSubTitle", regionCreationPlayer.getRegionEnterSubTitle());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterSound", regionCreationPlayer.getRegionEnterSound());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterSoundVolume", regionCreationPlayer.getRegionEnterSoundVolume());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterSoundPitch", regionCreationPlayer.getRegionEnterSoundPitch());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterFadeIn", regionCreationPlayer.getRegionEnterFadeIn());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterStay", regionCreationPlayer.getRegionEnterStay());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterFadeOut", regionCreationPlayer.getRegionEnterFadeOut());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionEnterPermissions", regionCreationPlayer.getRegionEnterPermission());

                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveEnabled", false);
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveTitle", regionCreationPlayer.getRegionLeaveTitle());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveSubTitle", regionCreationPlayer.getRegionLeaveSubTitle());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveSound", regionCreationPlayer.getRegionLeaveSound());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveSoundVolume", regionCreationPlayer.getRegionLeaveSoundVolume());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveSoundPitch", regionCreationPlayer.getRegionLeaveSoundPitch());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveFadeIn", regionCreationPlayer.getRegionLeaveFadeIn());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveStay", regionCreationPlayer.getRegionLeaveStay());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeaveFadeOut", regionCreationPlayer.getRegionLeaveFadeOut());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLeavePermissions", regionCreationPlayer.getRegionLeavePermission());


                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationOne", regionCreationPlayer.getRegionLocationOne());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationTwo", regionCreationPlayer.getRegionLocationTwo());

 /*                   Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationOne.X", regionCreationPlayer.getRegionLocationOne().getBlockX());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationOne.Y", regionCreationPlayer.getRegionLocationOne().getBlockY());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationOne.Z", regionCreationPlayer.getRegionLocationOne().getBlockZ());

                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationTwo.X", regionCreationPlayer.getRegionLocationTwo().getBlockX());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationTwo.Y", regionCreationPlayer.getRegionLocationTwo().getBlockY());
                    Core.getInstance().getConfig().set("Regions." + regionCreationPlayer.getRegionName() + ".regionLocationTwo.Z", regionCreationPlayer.getRegionLocationTwo().getBlockZ());
*/
                    Core.getInstance().saveConfig();
                    player.sendMessage("§aYou have successfully created the region §e" + regionCreationPlayer.getRegionName() + "§a!");
                    Core.getInstance().regionCreationPlayers.remove(player.getUniqueId());
                    Core.getInstance().loadedRegions.add(regionCreationPlayer);
                } else if (args[0].equalsIgnoreCase("firstlocation")) {
                    Location loc = player.getLocation().clone();
                    loc.setX(loc.getBlockX());
                    loc.setY(loc.getBlockY());
                    loc.setZ(loc.getBlockZ());
                    loc.setYaw(0);
                    loc.setPitch(0);
                    regionCreationPlayer.setRegionLocationOne(loc);
                    player.sendMessage("§aYou have set the first location! to Location: " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ());
                } else if (args[0].equalsIgnoreCase("secondlocation")) {
                    Location loc = player.getLocation().clone();
                    loc.setX(loc.getBlockX());
                    loc.setY(loc.getBlockY());
                    loc.setZ(loc.getBlockZ());
                    loc.setYaw(0);
                    loc.setPitch(0);
                    regionCreationPlayer.setRegionLocationTwo(loc);
                    player.sendMessage("§aYou have set the second location! to Location: " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ());
                } else if (args[0].equalsIgnoreCase("cancel")) {
                    Core.getInstance().regionCreationPlayers.remove(player.getUniqueId());
                    player.sendMessage("§cYou have cancelled the creation of a new Region!");
                } else {
                    if (regionCreationPlayer != null) {
                        player.sendMessage("§cYou are already creating a new Region.\nPlease use §e/createregion cancel §cto cancel the creation!");
                        return true;
                    }
                    Core.getInstance().regionCreationPlayers.put(player.getUniqueId(), new Region(args[0], "Region-" + args[0], "This is a subtitle", Sound.ENTITY_PLAYER_LEVELUP.name(), 100, 1, 20, 40, 20, null, null, "*", false));
                    player.sendMessage(" ");
                    player.sendMessage("§aYou are now creating a new Region. Please use");
                    player.sendMessage(" ");
                    player.sendMessage("§e/createregion <title/subtitle/fadein/stay/fadeout> <value>\n§e/createregion <firstlocation/secondlocation> §ato set the values!");
                    player.sendMessage(" ");
                    player.sendMessage("§aWhen you are done, use §e/createregion finish");

                }
            } else if (args.length == 0) {
                player.sendMessage("§cPlease use §e/createregion <regionname>");
            }

            if (regionCreationPlayer != null) {
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("title")) {
                        StringBuilder title = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            title.append(args[i]).append(" ");
                        }
                        regionCreationPlayer.setRegionEnterTitle(title.toString());
                        player.sendMessage("§aYou have set the title to §e'" + title + "'§a!");
                    } else if (args[0].equalsIgnoreCase("subtitle")) {
                        StringBuilder subtitle = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            subtitle.append(args[i]).append(" ");
                        }
                        regionCreationPlayer.setRegionEnterSubTitle(subtitle.toString());
                        player.sendMessage("§aYou have set the subtitle to §e'" + subtitle + "'§a!");
                    } else if (args[0].equalsIgnoreCase("fadein")) {
                        regionCreationPlayer.setRegionEnterFadeIn(Integer.parseInt(args[1]));
                        player.sendMessage("§aYou have set the fadein to §e'" + args[1] + "'§a!");
                    } else if (args[0].equalsIgnoreCase("stay")) {
                        regionCreationPlayer.setRegionEnterStay(Integer.parseInt(args[1]));
                        player.sendMessage("§aYou have set the stay to §e'" + args[1] + "'§a!");
                    } else if (args[0].equalsIgnoreCase("fadeout")) {
                        regionCreationPlayer.setRegionEnterFadeOut(Integer.parseInt(args[1]));
                        player.sendMessage("§aYou have set the fadeout to §e'" + args[1] + "'§a!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("§cThere was an error. Please check the console and submit a report on GitHub or Spigot!");
        }

        return true;
    }

}
