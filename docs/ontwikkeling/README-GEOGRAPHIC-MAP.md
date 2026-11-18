# Campus Survivor – Enschede geographic-layout update

This version keeps Java, SaxionGameApp, Gradle, and the gameplay of Sprint 0–3.

## Changes
- Repositioned the landmarks so Kennispark is northwest of the centre, Saxion lies to the southwest, and Oude Markt lies northeast of Saxion.
- Rebuilt the connected pedestrian/road tile network and repositioned entrances, interactions, NPC routines, and player spawn.
- Preserved the HUD fix, indoor scenes, exam system, student stats, activities, and V camera toggle.
- Added a shared `WorldMap.DOOR_X/DOOR_Y` source of truth to avoid interactions referring to the old map.

## Geographic accuracy
**This is a schematic approximation, not a true OpenStreetMap export.** Roads, distances, building outlines and coordinates are hand-authored for gameplay. The student flat and work/shop locations are fictional representative locations. Do not present this as an accurate street map or a reconstruction of actual buildings.

## Run
Windows: `gradlew.bat run`
macOS: `bash gradlew run`

Compile and play-test locally before marking Scrum cards as Done.
