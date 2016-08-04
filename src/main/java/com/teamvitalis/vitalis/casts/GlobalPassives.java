package com.teamvitalis.vitalis.casts;

import com.teamvitalis.vitalis.Vitalis;
import com.teamvitalis.vitalis.casts.luna.LunaPassives;

/**
 * Created by Kagami95 "Carbogen" on 04/08/2016.
 */
public class GlobalPassives {
	public static void start() {
		Vitalis.plugin().getServer().getScheduler().scheduleSyncRepeatingTask(Vitalis.plugin(), new Runnable() {
			@Override
			public void run() {
				LunaPassives.runAll();
			}
		}, 0, 1L);
	}
}
