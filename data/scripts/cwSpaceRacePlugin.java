package data.scripts;

/*BUSCHER:
* Based onNewGame stuff on https://github.com/WadeStar/StarSector-Modding-Tutorials/blob/master/TestPlanet/data/scripts/TPModPlugin.java
 */

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.FactionHostilityManager;
import org.apache.log4j.Logger;

//import com.fs.starfarer.api.impl.campaign.shared.SharedData;
//import com.fs.starfarer.api.PluginPick;
//import com.fs.starfarer.api.campaign.CampaignPlugin;




public class cwSpaceRacePlugin extends BaseModPlugin {

    public static Logger log = Global.getLogger(cwSpaceRacePlugin.class);

    /*
    @Override
    public void onGameLoad(boolean newGame) {
        log.info("oGL selected factions: " + ExerelinConfig.getFactions(false, false));
    }
    */

    @Override
	public void onApplicationLoad() throws ClassNotFoundException {

        try {
            Global.getSettings().getScriptClassLoader().loadClass("org.lazywizard.lazylib.ModUtils");
        } catch (ClassNotFoundException ex) {
            String message = System.lineSeparator()
                    + System.lineSeparator() + "LazyLib is required to run at least one of the mods you have installed."
                    + System.lineSeparator() + System.lineSeparator()
                    + "You can download LazyLib at http://fractalsoftworks.com/forum/index.php?topic=5444"
                    + System.lineSeparator();
            throw new ClassNotFoundException(message);
        }




    }

    @Override
    public void onNewGameAfterProcGen() {

        SectorAPI sector = Global.getSector();
        sector.addScript(new FactionHostilityManager());
        FactionHostilityManager.getInstance().startHostilities(Factions.HEGEMONY, Factions.PERSEAN);


    }

}
