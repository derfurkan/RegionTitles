package de.snap20lp.regiontitles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class Region {

    private String regionName,
            regionEnterTitle,
            regionEnterSubTitle,
            regionLeaveTitle,
            regionLeaveSubTitle,
            regionEnterSound,
            regionLeaveSound;
    private int regionEnterSoundVolume,
            regionEnterSoundPitch,
            regionEnterFadeIn,
            regionEnterStay,
            regionEnterFadeOut,
            regionLeaveSoundVolume,
            regionLeaveSoundPitch,
            regionLeaveFadeIn,
            regionLeaveStay,
            regionLeaveFadeOut;
    private Location regionLocationOne,
            regionLocationTwo;
    private String regionEnterPermission,
            regionLeavePermission;
    private boolean regionLeaveEnabled;

    public Region(String regionName, String regionEnterTitle, String regionEnterSubTitle, String regionEnterSound, int regionEnterSoundVolume, int regionEnterSoundPitch, int regionEnterFadeIn, int regionEnterStay, int regionEnterFadeOut, Location regionLocationOne, Location regionLocationTwo, String permission, boolean regionLeaveEnabled) {
        this.regionName = regionName;
        this.regionEnterTitle = regionEnterTitle;
        this.regionEnterSubTitle = regionEnterSubTitle;
        this.regionEnterSound = regionEnterSound;
        this.regionEnterSoundVolume = regionEnterSoundVolume;
        this.regionEnterSoundPitch = regionEnterSoundPitch;
        this.regionEnterFadeIn = regionEnterFadeIn;
        this.regionEnterStay = regionEnterStay;
        this.regionEnterFadeOut = regionEnterFadeOut;
        this.regionEnterPermission = permission;

        this.regionLeaveTitle = regionEnterTitle;
        this.regionLeaveSubTitle = regionEnterSubTitle;
        this.regionLeaveSound = regionEnterSound;
        this.regionLeaveSoundVolume = regionEnterSoundVolume;
        this.regionLeaveSoundPitch = regionEnterSoundPitch;
        this.regionLeaveFadeIn = regionEnterFadeIn;
        this.regionLeaveStay = regionEnterStay;
        this.regionLeaveFadeOut = regionEnterFadeOut;
        this.regionLeavePermission = permission;

        this.regionLocationOne = regionLocationOne;
        this.regionLocationTwo = regionLocationTwo;

        this.regionLeaveEnabled = regionLeaveEnabled;
    }
}
