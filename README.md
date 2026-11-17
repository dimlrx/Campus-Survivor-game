# Campus Survivor: Enschede Edition — Sprint 2

Based on the original Saxion GameApp Gradle template and the Sprint 1 project.

## Run
- Windows: `gradlew.bat run`
- macOS/Linux: `chmod +x gradlew && ./gradlew run`

## Controls
- WASD / arrows: move; E: talk to nearby NPC; P: pause; ESC: menu.
- **1** near Saxion entrance: study for 2 in-game hours.
- **2** near student flat entrance: sleep for 8 in-game hours.
- **3** near shops entrance: work for 4 in-game hours.
- **4** near Oude Markt entrance: go out for 3 in-game hours.

Entrances are the walkable path tiles in the middle of each building's street-facing edge. Walk near the entrance and read the bottom HUD hint. Actions skip game time and affect energy, money, stress, knowledge, alcohol and popularity. Low energy prevents studying or working; going out requires money. Alcohol reduces studying efficiency and may damage popularity; NPCs react to high alcohol and popularity.

## Sprint 2 scope
Implements the Plan van Aanpak Sprint 2 must-haves: studying, working, sleeping, going out and consequences of alcohol/reputation. Sprint 3 (exams, random events, multiple endings) is not included.

## Testing
The ZIP structure and Java source checks are performed, but a full Gradle compilation/runtime is not guaranteed. Test on your own machine and report compiler/runtime errors if encountered.

## Visual map upgrade — 2.5D edition
The Sprint 2 map now has layered roof/facade building art, window patterns, shadows, trees, road markings, plaza details, labeled landmarks and highlighted activity entrances. Saxion, student flat, Kennispark, Centrum, Oude Markt and shops/work are all visible. The underlying collision map and Sprint 2 interactions are preserved.

**Accuracy:** This is an original stylized Enschede-inspired game map, **not** a geographically accurate street map or photorealistic 3D model. It uses 2.5D-looking 2D shapes in the existing SaxionGameApp framework. A true 3D Enschede replica would require a different renderer and licensed/geospatial map or model assets.

## Sprint 3 HUD fix
The HUD uses smaller fonts, splits statistics into two lines, moves exam progress into the top bar, and uses smaller map labels. The menu fonts are also reduced. No gameplay systems were removed.
