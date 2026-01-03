# JPirates
Licensed players are good, but what about pirates? Add the pirates you approve of by nickname.

The latest ready-made configuration: https://github.com/mitriyf/JPirates/releases/download/1.1-release/Configurations.zip

## Requirements:
- Fork BukkitFastLogin (Download the fork from here: https://github.com/mitriyf/FastLogin/releases/tag/1.1 )
Also, ProtocolLib is required:
Attention!
Choose one of the ProtocolLib versions depending on your Java version. If you took it from Configurations.zip:

  Java 17+:
    - If you have Java 17, remove the _IF_JAVA17 from the name of ProtocolLib-5.4.0.jar_IF_JAVA17
    - And remove file: ProtocolLib-5.3.0.jar_IF_JAVA11
    - or just download ProtocolLib-5.4.0 -> https://github.com/dmulloy2/ProtocolLib/releases/tag/5.4.0

  Java 11+:
    - If you have Java 11, then remove this _IF_JAVA11 from the name ProtocolLib-5.3.0.jar_IF_JAVA11
    - And remove file: ProtocolLib-5.4.0.jar_IF_JAVA17
    - or just download ProtocolLib-5.3.0 -> https://github.com/dmulloy2/ProtocolLib/releases/tag/5.3.0

## Optional requirements:
- AuthMe Reloaded (5.X)
- xAuth
- CrazyLogin
- LoginSecurity
- AdvancedLogin (Paid)
- AlixSystem
- UltraAuth
- BungeeAuth (BungeeCord/Waterfall plugin)
- and others from: https://www.spigotmc.org/resources/fastlogin.14153/

### Commands:
- /jpirates reload - Reload the plugin configuration.
- /jpirates add nickname - Add a nickname to the pirates' whitelist
- /jpirates remove nickname - Remove the nickname from the pirates' whitelist.
- /jpirates list - Get a white list of pirates.
- /jpirates enable - Enable the list of pirates (default)
- /jpirates disable - Turn off the list of pirates.

### Permission:
- **jpirates.use** - Allow the use of the command.
