# Campus Survivor — 2.5D Interiors Edition

This update builds on Sprint 2 / Isometric Prototype and keeps the original SaxionGameApp Gradle project.

## Added
- Six distinct illustrated interior environments: Saxion study lounge, student apartment, Kennispark tech hub, city-centre shops, Oude Markt cafe, and workplace.
- Enter near a building entrance with **E**, exit with **Q**.
- Interior-specific contextual action prompts; Sprint 2 activities still use 1/2/3/4.
- HUD displays the current interior name and description, game clock and player statistics.
- Existing overworld camera toggle **V**, movement, NPCs and clock retained.

## Important limitations
- Interiors are procedural **2.5D illustrations**, not imported polygonal 3D meshes or exact copies of the real buildings.
- The existing exterior layout is fictional; no surveyed or georeferenced street map is included.
- Compilation and interactive playtesting have not been completed in this environment.

Run on macOS with `bash gradlew run`; Windows with `gradlew.bat run`.
