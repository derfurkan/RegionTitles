package de.snap20lp.regiontitles;

import com.google.common.collect.Lists;
import de.snap20lp.regiontitles.commands.CreateCommand;
import de.snap20lp.regiontitles.commands.ReloadCommand;
import de.snap20lp.regiontitles.util.Cuboid;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Core extends JavaPlugin implements Listener {

    @Getter
    private static Core instance;

    public final HashMap<UUID, Region> regionCreationPlayers = new HashMap<>();
    public final List<Region> loadedRegions = Lists.newArrayList();

    public final HashMap<Region, Cuboid> cuboidContainer = new HashMap<>();
    public final HashMap<UUID, Region> regionContainer = new HashMap<>();

    public final List<UUID> coolDownList = Lists.newArrayList();

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("RegionTitles starting in version " + getDescription().getVersion());
        this.saveDefaultConfig();
        if (getConfig().getConfigurationSection("Regions") == null) {
            getConfig().createSection("Regions");
            saveConfig();
        }
        getCommand("createregion").setExecutor(new CreateCommand());
        getCommand("reloadregiontitles").setExecutor(new ReloadCommand());
        loadRegions();

        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        regionCreationPlayers.clear();
    }

    public void loadRegions() {
        coolDownList.clear();
        regionCreationPlayers.clear();
        cuboidContainer.clear();
        regionContainer.clear();
        loadedRegions.clear();
        reloadConfig();
        System.out.println(" Loading regions...");
        getConfig().getConfigurationSection("Regions").getKeys(false).forEach(regionName -> {
            Region region = new Region(regionName, "Region-" + regionName, "This is a subtitle", Sound.ENTITY_PLAYER_LEVELUP.name(), 100, 1, 20, 40, 20, null, null, "*", false);
            region.setRegionEnterTitle(getConfig().getString("Regions." + regionName + ".regionEnterTitle"));
            region.setRegionEnterSubTitle(getConfig().getString("Regions." + regionName + ".regionEnterSubTitle"));
            region.setRegionEnterSound(getConfig().getString("Regions." + regionName + ".regionEnterSound"));
            region.setRegionEnterSoundVolume(getConfig().getInt("Regions." + regionName + ".regionEnterSoundVolume"));
            region.setRegionEnterSoundPitch(getConfig().getInt("Regions." + regionName + ".regionEnterSoundPitch"));
            region.setRegionEnterFadeIn(getConfig().getInt("Regions." + regionName + ".regionEnterFadeIn"));
            region.setRegionEnterStay(getConfig().getInt("Regions." + regionName + ".regionEnterStay"));
            region.setRegionEnterFadeOut(getConfig().getInt("Regions." + regionName + ".regionEnterFadeOut"));
            region.setRegionEnterPermission(getConfig().getString("Regions." + regionName + ".regionEnterPermission"));
            region.setRegionLeaveEnabled(getConfig().getBoolean("Regions." + regionName + ".regionLeaveEnabled"));
            region.setRegionLeaveTitle(getConfig().getString("Regions." + regionName + ".regionLeaveTitle"));
            region.setRegionLeaveSubTitle(getConfig().getString("Regions." + regionName + ".regionLeaveSubTitle"));
            region.setRegionLeaveSound(getConfig().getString("Regions." + regionName + ".regionLeaveSound"));
            region.setRegionLeaveSoundVolume(getConfig().getInt("Regions." + regionName + ".regionLeaveSoundVolume"));
            region.setRegionLeaveSoundPitch(getConfig().getInt("Regions." + regionName + ".regionLeaveSoundPitch"));
            region.setRegionLeaveFadeIn(getConfig().getInt("Regions." + regionName + ".regionLeaveFadeIn"));
            region.setRegionLeaveStay(getConfig().getInt("Regions." + regionName + ".regionLeaveStay"));
            region.setRegionLeaveFadeOut(getConfig().getInt("Regions." + regionName + ".regionLeaveFadeOut"));
            region.setRegionLeavePermission(getConfig().getString("Regions." + regionName + ".regionLeavePermission"));
            region.setRegionLocationOne(getConfig().getObject("Regions." + regionName + ".regionLocationOne", Location.class));
            region.setRegionLocationTwo(getConfig().getObject("Regions." + regionName + ".regionLocationTwo", Location.class));
            loadedRegions.add(region);
            System.out.println("  Loaded region " + regionName);
        });
        System.out.println(" Loaded " + loadedRegions.size() + " region(s)!");

    }

    @EventHandler
    public void on(PlayerMoveEvent playerMoveEvent) {
        loadedRegions.forEach(region -> {
            Cuboid cuboid;
            if (cuboidContainer.containsKey(region))
                cuboid = cuboidContainer.get(region);
            else {
                cuboid = new Cuboid(region.getRegionLocationOne(), region.getRegionLocationTwo());
                cuboidContainer.put(region, cuboid);
            }

            if (cuboid == null) {
                return;
            }

            if (cuboid.isIn(playerMoveEvent.getPlayer()) && regionContainer.get(playerMoveEvent.getPlayer().getUniqueId()) != region && !coolDownList.contains(playerMoveEvent.getPlayer().getUniqueId())) {

                regionContainer.put(playerMoveEvent.getPlayer().getUniqueId(), region);
                if (!coolDownList.contains(playerMoveEvent.getPlayer().getUniqueId()) && playerMoveEvent.getPlayer().hasPermission(region.getRegionEnterPermission())) {
                    String title = region.getRegionEnterTitle();
                    String subTitle = region.getRegionEnterSubTitle();
                    if (title.contains("%player%"))
                        title = title.replace("%player%", playerMoveEvent.getPlayer().getName());
                    if (subTitle.contains("%player%"))
                        subTitle = subTitle.replace("%player%", playerMoveEvent.getPlayer().getName());

                    title = ChatColor.translateAlternateColorCodes('&', title);
                    subTitle = ChatColor.translateAlternateColorCodes('&', subTitle);

                    playerMoveEvent.getPlayer().sendTitle(title, subTitle, region.getRegionEnterFadeIn(), region.getRegionEnterStay(), region.getRegionEnterFadeOut());
                    playerMoveEvent.getPlayer().playSound(playerMoveEvent.getPlayer().getLocation(), Sound.valueOf(region.getRegionEnterSound()), region.getRegionEnterSoundVolume(), region.getRegionEnterSoundPitch());
                    coolDownList.add(playerMoveEvent.getPlayer().getUniqueId());
                    Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> coolDownList.remove(playerMoveEvent.getPlayer().getUniqueId()), getConfig().getInt("RegionTitles.titleCooldownTicks"));
                }
            } else if (!cuboid.isIn(playerMoveEvent.getPlayer()) && regionContainer.get(playerMoveEvent.getPlayer().getUniqueId()) == region) {

                regionContainer.remove(playerMoveEvent.getPlayer().getUniqueId());
                if (!coolDownList.contains(playerMoveEvent.getPlayer().getUniqueId()) && region.isRegionLeaveEnabled() && playerMoveEvent.getPlayer().hasPermission(region.getRegionLeavePermission())) {


                    String title = region.getRegionLeaveTitle();
                    String subTitle = region.getRegionLeaveSubTitle();
                    if (title.contains("%player%"))
                        title = title.replace("%player%", playerMoveEvent.getPlayer().getName());
                    if (subTitle.contains("%player%"))
                        subTitle = subTitle.replace("%player%", playerMoveEvent.getPlayer().getName());
                    title = ChatColor.translateAlternateColorCodes('&', title);
                    subTitle = ChatColor.translateAlternateColorCodes('&', subTitle);

                    playerMoveEvent.getPlayer().sendTitle(title,subTitle, region.getRegionLeaveFadeIn(), region.getRegionLeaveStay(), region.getRegionLeaveFadeOut());
                    playerMoveEvent.getPlayer().playSound(playerMoveEvent.getPlayer().getLocation(), Sound.valueOf(region.getRegionLeaveSound()), region.getRegionLeaveSoundVolume(), region.getRegionLeaveSoundPitch());
                    coolDownList.add(playerMoveEvent.getPlayer().getUniqueId());
                    Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> coolDownList.remove(playerMoveEvent.getPlayer().getUniqueId()), getConfig().getInt("RegionTitles.titleCooldownTicks"));
                }
            }
        });
    }

}
