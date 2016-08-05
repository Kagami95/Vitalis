package com.teamvitalis.vitalis.casts.luna;

import com.teamvitalis.vitalis.object.ClassType;
import com.teamvitalis.vitalis.object.MagicType;
import com.teamvitalis.vitalis.object.Mancer;
import com.teamvitalis.vitalis.object.VitalisPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Kagami95 "Carbogen" on 04/08/2016.
 */
public class LunaMethods {

	/* Returns:
	 *  4: Full Moon
	 *  3: Gibbous Moon
	 *  2: Quarter Moon
	 *  1: Crescent Moon
	 *  0: New Moon
	 */
	public static int getLunarPhase(World world) {
		long days = world.getFullTime()/24000 - ((world.getTime() < 12500) ? 1 : 0);
		int phase = (int) Math.abs(4-(days%8));
		return phase;
	}

	public static boolean isLunamancer(Player p) {
		VitalisPlayer vp = VitalisPlayer.fromPlayer(p);
		if (vp != null && vp.getClassType() == ClassType.MANCER) {
			Mancer mvp = (Mancer) vp; // "...Most Valuable Player?" LOL
			if (mvp.getMagicType() == MagicType.LUNA) {
				return true;
			}
		}

		return false;
	}
}
