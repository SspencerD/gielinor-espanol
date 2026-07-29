# Gielinor Español

**Gielinor Español** is a RuneLite plugin that provides a community-driven Spanish localization layer for Old School RuneScape.

The goal of this plugin is to make OSRS more accessible to Spanish-speaking players by translating menu options, items, NPCs, world objects, widgets, and other interface elements progressively.

> This plugin does not modify the game client, does not automate gameplay, and does not interact with the game world. It only translates visible text from RuneLite menu entries and interface widgets.

---

## Features

### Current features

- Translates common menu options:
    - Walk here
    - Use
    - Examine
    - Talk-to
    - Attack
    - Pickpocket
    - Trade
    - Buy
    - Sell
    - Withdraw
    - Deposit

- Translates menu targets:
    - Items
    - NPCs
    - World objects
    - Interface widgets

- Supports item variants:
    - Amulet of glory(3)
    - Rune dart(p)
    - Iban's staff (u)
    - Items with charges or suffixes

- Supports NPC combat level text:
    - Guard (level-21) → Guardia (nivel-21)

- Supports dynamic menu options:
    - Buy 50 → Comprar 50
    - Sell 10 → Vender 10
    - Withdraw-All → Retirar todo
    - Deposit-X → Depositar X

- Local missing translation capture:
    - Saves untranslated entries locally.
    - Does not send data to the internet.
    - Useful for improving the community dictionaries.

---

## Configuration

The plugin includes several configuration options:

### Translate menu options

Translates actions such as:

```text
Use
Examine
Walk here
Talk-to
Attack
Pickpocket
```
### Capture missing translations

When enabled, the plugin save unstranslated entries locally to:
```code
~/.runelite/gielinor-espanol/missing-menu-translations.json
```
This option is disabled by default.

No data is uploaded or shared automatically.

### Technical menu inspector

Development-only option that logs menu entry details such as:

```text
Option
Target
Type
Identifier
Param0
Param1
Widget group
```
Useful for debugging and improving translation coverage.

### Translation files

Translations are stored as JSON dictionaries inside:
```text
src/main/resources/translations/es/
```

Current translation groups:
```text
menu/options.json
menu/targets.json
objects/objects.json
npcs/npcs.json
items/items.json
widgets/widgets.json
```

Example:
```json 
{
"Walk here": "Caminar aquí",
"Examine": "Examinar",
"Talk-to": "Hablar con"
}
```

### Validating translation files

Before running or committing changes, validate the translation JSON files:

```pycon
python3 scripts/validate-translations.py
```

This script checks for:

- Invalid JSON syntax
- Duplicate translation keys
- Missing translation files

This helps prevent issues where one duplicated key can stop an entire dictionary from loading.

---

## Development

### Requirements
- Java 11 or compatible RuneLite development setup
- Gradle wrapper
- RuneLite external plugin environment

### Run locally
```text
./gradlew run
```
On Windows:
```text 
gradlew.bat run
```

### Clean and run
```text
./gradlew clean run
```
---

## Privacy

Gielinor Español is designed to work locally.

The plugin does not automatically upload:

- Player names
- Chat messages
- Account data
- Coordinates
- Credentials
- Inventory data
- Bank data

Missing translation capture is local-only.

If community synchronization is added in the future, it should be explicit, optional, and privacy-focused.

---

## Roadmap

### v0.1 - MVP
- Context menu translation
- Partial item translation
- Partial NPC translation
- Partial object translation
- Item variants
- Combat level normalization
- Dynamic menu options
- Local missing translation capture
- Basic configuration options
### v0.2 - Interface and widgets

- Bank interface
- Equipment interface
- Shop interface
- Quest tab
- Prayer tab
- Spellbook
- Settings panels
- Jewellery box and teleport interfaces

### v0.3 - Dialogues

- NPC dialogue text
- Conversation options
- Quest-related dialogue fragments

### v0.4 - Community translation system
 
- Optional online contribution system
- Translation suggestions
- Review workflow
- Community-maintained dictionaries

## Contributing

Contributions are welcome.

Useful contributions include:

- New translations
- Translation corrections
- Neutral Spanish improvements
- Missing item/NPC/object entries
- Widget classification improvements
- Bug reports

Please keep translations clear, neutral, and understandable for Spanish-speaking OSRS players across different regions.

---
## Disclaimer

Old School RuneScape and RuneLite are not affiliated with this project.

This plugin is an unofficial community localization project.