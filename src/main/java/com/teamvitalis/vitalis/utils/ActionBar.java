package com.teamvitalis.vitalis.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.teamvitalis.vitalis.utils.ReflectionUtils.PackageType;

/**
* Modified version of @author mine-care's ActionBarUtil.
*/
public class ActionBar {
	
    private static boolean initialised = false;
    private static Constructor<?> chatSer;
    private static Constructor<?> packetChat;
    private static Method getHandle;
    private static Field playerConnection;
    private static Method sendPacket;
 
    static {
        try {
        	chatSer = ReflectionUtils.getConstructor(PackageType.MINECRAFT_SERVER.getClass("ChatComponentText"), String.class);
            packetChat = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat").getConstructor(PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent"), byte.class);
            getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
			playerConnection = ReflectionUtils.getField("EntityPlayer", PackageType.MINECRAFT_SERVER, false, "playerConnection");
			sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", PackageType.MINECRAFT_SERVER.getClass("Packet"));
            initialised = true;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            Bukkit.getServer().getLogger().warning("Cannot initialise Action Bar.");
            initialised = false;
        }
    }
 
    /**
     * This method checks if the ActionBarUtil has been initialised properly
     * @return returns a boolean indicating initialisation.
     */
    public static boolean isInitialised(){
        return initialised;
    }
   
    /**
     * This method sends an ActionBar message to a player, ChatColor works as well.
     * @param message The message to be sent to the player(s).
     * @param p The player(s) to receive the ActionBar message.
     * @return Returns a boolean indicating if the method execution was successful.
     * <br><b>NOTE: If anything goes wrong the utility will automatically
     *  stop working to prevent massive amounts of errors showing up in console.</b>
     */
    public static boolean sendActionBar(String message, Player... p) {
        if (!initialised) return false;
        try {
            Object o = chatSer.newInstance(message);
            Object packet = packetChat.newInstance(o, (byte) 2);
            sendPacket(packet, p);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            initialised = false;
        }
        return initialised;
    }
 
    /**
      * IGNORE THIS!
      */
    private static void sendPacket(Object packet, Player... pl) throws ReflectiveOperationException {
        for (Player p : pl) {
            Object entityplayer = getHandle.invoke(p);
            Object PlayerConnection = playerConnection.get(entityplayer);
            sendPacket.invoke(PlayerConnection, packet);
        }
    }
}