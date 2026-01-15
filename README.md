# 🏴‍☠️ JPirates [![CodeFactor](https://www.codefactor.io/repository/github/mitriyf/jpirates/badge)](https://www.codefactor.io/repository/github/mitriyf/jpirates)
## 🦜 Licensed players are good, but what about pirates? Add the pirates you approve of by nickname.
This plugin adds white lists for pirates, but not for licenses. So, you can play with your pirate friends even with a license. At the same time, the chips of licensed players are saved (only for licenses)!
- $ Versions 1.7.1-1.21 are supported. (I'm not sure about 1.7.1, but if you encounter issues with this version, please contact GitHub or RuBukkit)
- $ It has been tested on versions 1.8.8 and 1.21.10.
- $ Some plugin updates on SpigotMC.ru may be delayed.
- $ The plugin requires at least Java 11+ to function.
- $ You must allow players without a license to connect in server.propetories:
online-mode: false
- $ The latest ready-made configuration: https://github.com/mitriyf/JPirates/releases/download/1.1-release/Configurations.zip

## ☠️ Don't let foreign pirates in!
Only allow your pirate friends who have been approved by their nickname.
<p align="center"><img width="836" height="216" alt="image" src="https://github.com/user-attachments/assets/2efef428-44b2-4687-8489-9b706d3f3d02" />
</p>
<p align="center"><img width="521" height="150" alt="image" src="https://github.com/user-attachments/assets/fb90c218-feee-409d-ab25-aae5555df602" /></p>

## 🚀 Requirements:
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

## 🌠 Optional requirements:
- AuthMe Reloaded (5.X)
- xAuth
- CrazyLogin
- LoginSecurity
- AdvancedLogin (Paid)
- AlixSystem
- UltraAuth
- BungeeAuth (BungeeCord/Waterfall plugin)
- and others from: https://www.spigotmc.org/resources/fastlogin.14153/

### ⌨️ Commands (/jpirates):
- /jpirates reload - Reload the plugin configuration.
- /jpirates add nickname - Add a nickname to the pirates' whitelist
- /jpirates remove nickname - Remove the nickname from the pirates' whitelist.
- /jpirates list - Get a white list of pirates.
- /jpirates enable - Enable the list of pirates (default)
- /jpirates disable - Turn off the list of pirates.

### 📖 Permission:
- **jpirates.use** - Allow the use of the command.

### 📝 Configurations:
You can view the configurations by going to src/main/resources/config.yml or by following the link: https://github.com/mitriyf/JPirates/blob/main/src/main/resources/config.yml
