package data.campaign.rulecmd;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FleetInflater;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.FactionAPI.ShipPickMode;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.CharacterCreationData;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflaterParams;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.impl.campaign.tutorial.CampaignTutorialScript;
import com.fs.starfarer.api.impl.campaign.tutorial.SpacerObligation;
import com.fs.starfarer.api.impl.campaign.tutorial.TutorialMissionIntel;
import com.fs.starfarer.api.loading.VariantSource;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import com.fs.starfarer.api.util.Misc.Token;

/**
 *	$ngcAddOfficer
 *	$ngcSkipTutorial
 *
 */
public class MyAddStandardStartingScript extends BaseCommandPlugin {

	public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
		if (dialog == null) return false;
		
		CharacterCreationData data = (CharacterCreationData) memoryMap.get(MemKeys.LOCAL).get("$characterData");
		final MemoryAPI memory = memoryMap.get(MemKeys.LOCAL);
		data.addScriptBeforeTimePass(new Script() {
			public void run() {

				boolean skipTutorial = memory.getBoolean("$ngcSkipTutorial");
				if (!skipTutorial) {
					Global.getSector().getMemoryWithoutUpdate().set(CampaignTutorialScript.USE_TUTORIAL_RESPAWN, true);
				}
			}
		});
		
		data.addScript(new Script() {
			public void run() {
				CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
				// add crew, supplies, and fuel
				int crew = 0;
				int supplies = 0;
				for (FleetMemberAPI member : fleet.getFleetData().getMembersListCopy()) {
					crew += Math.ceil(member.getMinCrew() + (member.getMaxCrew() - member.getMinCrew()) * 0.5f);
					supplies += member.getDeploymentCostSupplies() * 4f;
				}
				
				CargoAPI cargo = fleet.getCargo();
				cargo.initPartialsIfNeeded();
				
				cargo.addCrew(crew);
				cargo.addSupplies(100);
				cargo.addFuel(80);
				cargo.getCredits().add(30000000);

			}
			
		});
		return true;
	}
}







