package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class HeavyArmor extends BaseHullMod {

	public static final float MANEUVER_PENALTY = 20f;
	
	
	private static Map mag = new HashMap();
	static {
		mag.put(HullSize.FRIGATE, 100f);
		mag.put(HullSize.DESTROYER, 200f);
		mag.put(HullSize.CRUISER, 300f);
		mag.put(HullSize.CAPITAL_SHIP, 400f);
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		
		stats.getArmorBonus().modifyFlat(id, (Float) mag.get(hullSize));
		
		stats.getAcceleration().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
		stats.getDeceleration().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
		stats.getTurnAcceleration().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
		stats.getMaxTurnRate().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
		
		//stats.getCargoMod().modifyFlat(id, -70);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue();
		if (index == 4) return "" + (int) MANEUVER_PENALTY + "%";
		return null;
		//if (index == 0) return "" + ((Float) mag.get(hullSize)).intValue();
		//return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		//if (ship.getMutableStats().getCargoMod().computeEffective(ship.getHullSpec().getCargo()) < 70) return false;

		return true;
	}
}
