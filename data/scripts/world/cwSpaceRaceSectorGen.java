package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.CoreCampaignPluginImpl;
import com.fs.starfarer.api.impl.campaign.CoreScript;
import com.fs.starfarer.api.impl.campaign.events.CoreEventProbabilityManager;
import com.fs.starfarer.api.impl.campaign.fleets.DisposableLuddicPathFleetManager;
import com.fs.starfarer.api.impl.campaign.fleets.DisposablePirateFleetManager;
import com.fs.starfarer.api.impl.campaign.fleets.EconomyFleetRouteManager;
import com.fs.starfarer.api.impl.campaign.fleets.MercFleetManagerV2;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;

import data.hullmods.HeavyArmor;

import data.scripts.world.systems.Corvus;
import org.apache.log4j.Logger;

public class cwSpaceRaceSectorGen implements SectorGeneratorPlugin {

	public static Logger log = Global.getLogger(data.scripts.cwSpaceRacePlugin.class);

	public void generate(SectorAPI sector) {
		//ClassLoader cl = Global.getSettings().getScriptClassLoader();

		log.info("58 Started generate");
		
		StarSystemAPI system = sector.createStarSystem("Corvus");
		//system.getLocation().set(16000 - 8000, 9000 - 10000);
		system.setBackgroundTextureFilename("graphics/backgrounds/background4.jpg");
		
		//sector.setCurrentLocation(system);
		sector.setRespawnLocation(system);
		sector.getRespawnCoordinates().set(-2500, -3500);

		initFactionRelationships(sector);

		new Corvus().generate(sector);



		LocationAPI hyper = Global.getSector().getHyperspace();
		SectorEntityToken atlanticLabel = hyper.addCustomEntity("atlantic_label_id", null, "atlantic_label", null);
		SectorEntityToken perseanLabel = hyper.addCustomEntity("persean_label_id", null, "persean_label", null);
		SectorEntityToken luddicLabel = hyper.addCustomEntity("luddic_label_id", null, "luddic_label", null);
		SectorEntityToken zinLabel = hyper.addCustomEntity("zin_label_id", null, "zin_label", null);
		SectorEntityToken abyssLabel = hyper.addCustomEntity("opabyss_label_id", null, "opabyss_label", null);
		SectorEntityToken telmunLabel = hyper.addCustomEntity("telmun_label_id", null, "telmun_label", null);
		SectorEntityToken cathedralLabel = hyper.addCustomEntity("cathedral_label_id", null, "cathedral_label", null);
		SectorEntityToken coreLabel = hyper.addCustomEntity("core_label_id", null, "core_label", null);

		log.info("107 added entetities");

		atlanticLabel.setFixedLocation(500, -2000);
		perseanLabel.setFixedLocation(-10000, 1000);
		luddicLabel.setFixedLocation(-14000, -9500);
		zinLabel.setFixedLocation(-22000, -17000); 
		telmunLabel.setFixedLocation(-16000, 0);
		cathedralLabel.setFixedLocation(-12700, -12000);
		//BUSCHER TEST
		//coreLabel.setFixedLocation(0, -6000);
		coreLabel.setFixedLocation(0, -6000);
		
		abyssLabel.setFixedLocation(-65000, -47000);
		
		/*SectorEntityToken deep_hyperspace_test = Global.getSector().getHyperspace().addTerrain(Terrain.NEBULA, new BaseTiledTerrain.TileParams(
				"   xx     " +
				"   xxx    " +
				"  xxx x   " +
				"  xx   x  " +
				" xxxx xxx " +
				"  xxxxxxx " +
				" xxxxxxxxx" +
				" xxxxxxxxx" +
				"  xxxxxxx " +
				" xxxxxxx  " +
				" x xxxxx  " +
				"  xxxxxx  " +
				" xxxx xxx " +
				"xxxx  xxx " +
				" xxxx     " +
				"xxxxxxxxx " +
				"  xxxxxxxx" +
				" xxxxxxxxx" +
				"  xxxxxxx " +
				"   xxx    ",
				10, 20, // size of the nebula grid, should match above string
				"terrain", "deep_hyperspace", 4, 4));
		
		deep_hyperspace_test.getLocation().set(5000,5000);*/

		log.info("147 set fixed locations");
		
		SectorEntityToken deep_hyperspace = Misc.addNebulaFromPNG("data/campaign/terrain/hyperspace_map.png",
		//SectorEntityToken deep_hyperspace = Misc.addNebulaFromPNG("data/campaign/terrain/hyperspace_map_filled.png",
				  0, 0, // center of nebula
				  Global.getSector().getHyperspace(), // location to add to
				  "terrain", "deep_hyperspace", // "nebula_blue", // texture to use, uses xxx_map for map
				  4, 4, Terrain.HYPERSPACE, null); // number of cells in texture


		log.info("157 nebula");
		
		// ensure area round stars is clear
		HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
		NebulaEditor editor = new NebulaEditor(plugin);
		float minRadius = plugin.getTileSize() * 2f;
		for (StarSystemAPI curr : sector.getStarSystems()) {
			float radius = curr.getMaxRadiusInHyperspace() * 0.5f;
			editor.clearArc(curr.getLocation().x, curr.getLocation().y, 0, radius + minRadius * 0.5f, 0, 360f);
			editor.clearArc(curr.getLocation().x, curr.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
		}

		log.info("169 terrain");
		
//		PirateSpawnPoint pirateSpawn = new PirateSpawnPoint(sector, sector.getHyperspace(), 1, 15, system.getHyperspaceAnchor());
//		system.addSpawnPoint(pirateSpawn);
//		for (int i = 0; i < 2; i++) {
//			pirateSpawn.spawnFleet();
//		}
		
		// need to do this after hyperspace terrain exists
		//SectorProcGen.generate();
		// this is done through settings.json, "plugins"->"newGameSectorProcGen"


		sector.registerPlugin(new CoreCampaignPluginImpl());
		sector.addScript(new CoreScript());
		sector.addScript(new CoreEventProbabilityManager());

		log.info("185 added 2 scripts");

		sector.addScript(new EconomyFleetRouteManager());
		//sector.addScript(new MercFleetManager());
		sector.addScript(new MercFleetManagerV2());

		log.info("191 added 2 scripts");
		
		sector.addScript(new DisposablePirateFleetManager());
		sector.addScript(new DisposableLuddicPathFleetManager());

		log.info("196 added 2 scripts");

//		sector.addScript(new LuddicPathFleetManager());
//		sector.addScript(new PirateFleetManager());
//		sector.addScript(new BountyPirateFleetManager());
		
	}
	
	public static void initFactionRelationships(SectorAPI sector) {
		


		// forget why this is necessary - workaround for some JANINO issue, I think
		Class c = HeavyArmor.class;

		
		FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
		FactionAPI persean = sector.getFaction(Factions.PERSEAN);
		FactionAPI pirates = sector.getFaction(Factions.PIRATES);
		FactionAPI independent = sector.getFaction(Factions.INDEPENDENT);

		FactionAPI player = sector.getFaction(Factions.PLAYER);

		FactionAPI remnant = sector.getFaction(Factions.REMNANTS);
		FactionAPI derelict = sector.getFaction(Factions.DERELICT);
		
		player.setRelationship(hegemony.getId(), 0);
		player.setRelationship(persean.getId(), 0);
		//player.setRelationship(pirates.getId(), RepLevel.HOSTILE);
		player.setRelationship(pirates.getId(), -0.65f);
		
		player.setRelationship(independent.getId(), 0);
		//player.setRelationship(path.getId(), RepLevel.HOSTILE);


		// replaced by hostilities set in MyCoreLifecyclePluginImpl
		//hegemony.setRelationship(tritachyon.getId(), RepLevel.HOSTILE);
		//hegemony.setRelationship(persean.getId(), RepLevel.HOSTILE);
		
		hegemony.setRelationship(pirates.getId(), RepLevel.HOSTILE);
		

		pirates.setRelationship(independent.getId(), RepLevel.HOSTILE);
		pirates.setRelationship(persean.getId(), RepLevel.HOSTILE);
		

		

		persean.setRelationship(pirates.getId(), RepLevel.HOSTILE);

		
		player.setRelationship(remnant.getId(), RepLevel.HOSTILE);
		independent.setRelationship(remnant.getId(), RepLevel.HOSTILE);
		pirates.setRelationship(remnant.getId(), RepLevel.HOSTILE);
		hegemony.setRelationship(remnant.getId(), RepLevel.HOSTILE);

		persean.setRelationship(remnant.getId(), RepLevel.HOSTILE);
		

		
	}
}