Debug Properties Plus
=====================

This ia a Minecraft fabric mod that adds a conventient UI to modify [debug properties](https://minecraft.wiki/w/Debug_property)
at runtime. The UI can be opened using F3+F7 (can be changed in controlls). This also allow changing debug properties
of the server when the mod is also installed on the server and the player has OP permissions.

Additionally, adds a `/debugproperties` command to set properties on the server, even when the mod is not installed on
the client.

Because debug properties are very diverse in area of application the mod does differenciate between different types:

### Client properties:
These properties affect some behavior of the client. Those properties can be set globally and then affect all sessions and
worlds
- Some of these properties (mostly debug renderers) require the player to have OP permissions ingame. A warning is shown
  when permissions are missing.
- Some of these properties don't work at all in multiplayer. A warning is shown when on a server.

### Global server properties:
These properties affect the behavior of the server. They are set globally and affect all worlds in singleplayer, but need
to be set seperately on a server. Requires OP permissions to set on a server. Can't be changed by visitors of a LAN server
(as that would affect the properties of the host even in different worlds).

### Per world properties:
Those are the most dangerous properties, as they affect the world directly (i.e. an inadvetantly enabled property can cause
lasting damage to a world). Therefore, they are set on a per-world basis and have to be enabled seperately for each world.

During world creation there is an option to set those properties before the world is created. Default values for can be set
when opening the F3+F7 menu while in the main menu. Also requires OP permissions to set on a server.

### Ineffective properties:
Some properties have no effect in vanilla and are therefore not handled at all.