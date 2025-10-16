# jDamageIndicator

A lightweight yet powerful Minecraft Bukkit plugin that brings combat to life with customizable floating damage indicators, critical hit markers, and healing popups — all while being performance-conscious and easy to configure. Compatible with Minecraft versions 1.21 up to 1.21.10.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Project Type: Personal](https://img.shields.io/badge/Project%20Type-Personal-blue.svg)]()

## Features

*   **Floating Damage Indicators** – See exactly how much damage was dealt in real-time.
*   **⚡ Critical Hit Detection** – Highlight those powerful hits with unique markers.
*   **Healing Indicators** – Show health restoration amounts in real-time.
*   **Customizable Colors** – Style indicators by damage type, critical hits, and healing.
*   **Extensive Configuration** – Control everything from animations and display durations to indicator offsets via the `config.yml`.
*   **Sound Effects** – Add immersive audio cues when damage or healing occurs.
*   **Performance Optimized** – Lightweight and efficient design ensures minimal server impact.
*   **Debug Mode** – Detailed logs for troubleshooting and advanced configuration.

## Installation

1.  Download the `jDamageIndicator.jar` file from the [latest release](https://github.com/Xenos-core/jDamageIndicator/releases/tag/jDamageIndicator).
2.  Place the `.jar` file into your server's `/plugins` folder.
3.  Restart your Minecraft server.
4.  Use the `/jdi` command in-game or configure the `config.yml` file in the `/plugins/jDamageIndicator/` directory to customize the plugin settings.

## Commands

| Command             | Permission   | Description                      |
| ------------------- | ------------ | -------------------------------- |
| `/jdi`              | `jdi.admin`  | Show the plugin menu.            |
| `/jdi help`         | `jdi.admin`  | List all available plugin commands. |
| `/jdi reload`       | `jdi.admin`  | Reload the plugin configuration from `config.yml`. |
| `/jdi info`         | `jdi.admin`  | Display plugin information (version, author, etc.). |
| `/jdi status`       | `jdi.admin`  | Show the current status of the plugin (enabled/disabled). |

**Aliases:** `/jdamageindicator`, `/damageindicator`

## Permissions

| Permission | Default | Description                                   |
| ---------- | ------- | --------------------------------------------- |
| `jdi.admin` | op      | Full access to all admin commands.            |
| `jdi.use`   | true    | Allows the player to see damage indicators. |

## Configuration

The `config.yml` file allows extensive customization of the plugin. Here's a basic example:

colors:
  damage: "RED"
  critical: "GOLD"
  healing: "GREEN"



> Adjust these settings to suit your server's needs. Detailed explanations of each setting can be found within the `config.yml` file itself.

## Support

> If you encounter any issues or have questions about the plugin, please visit the [support forum](t.me/CatoniSefid) or create an issue on the [GitHub repository](https://github.com/Xenos-core/).

## Contributing

> Contributions are welcome! If you have suggestions or improvements, feel free to submit a pull request on the [GitHub repository](https://github.com/Xenos-core/).

## License

This project is licensed under the MIT License .