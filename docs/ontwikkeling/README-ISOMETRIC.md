# Campus Survivor – Isometric prototype (Sprint 2)

This version adds an optional oblique 2.5D view. Press **V** to switch between the original overhead map and the new angled map. WASD, E, P, and 1–4 work as before.

The new view is procedurally rendered with elevated buildings and depth sorting. Gameplay collision and interactions continue to use the existing tilemap.

**Important limitation:** This is a stylized prototype, NOT a georeferenced recreation of Enschede. Actual street geometry, correct building footprints, photographic building references and real 3D models have not been integrated. The current map positions are fictional.

To achieve a faithful recreation, obtain licensed map data (e.g. OpenStreetMap with attribution), verify locations and footprints, create building meshes, and migrate to a real 3D renderer.

Run: `./gradlew run` (macOS/Linux) or `gradlew.bat run` (Windows).
