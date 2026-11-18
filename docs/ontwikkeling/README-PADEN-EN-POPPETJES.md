# Campus Survivor — speler, NPC's en ingangen

- Hoofdpersonage: blauw pixel-art poppetje uit de aangeleverde NPC-sheet (`npcs/player.png`).
- Twaalf NPC's: behouden hun pixel-art uiterlijk en dialogen.
- Bewegingsgebied: een speciaal voor de huidige kaart getekend voetpaden-/pleinmasker in `WorldMap.java`. De speler en NPC's kunnen niet door de gemarkeerde gebouwen lopen; de fontein is ook geblokkeerd.
- NPC's starten op het dichtstbijzijnde pad en kiezen lokale, bereikbare loopdoelen in plaats van rechtstreeks door gebouwen te lopen.
- Ingangen: zeven groene deurpictogrammen (`maps/entrance.png`) in de stijl van het aangeleverde voorbeeld. Loop naar het icoon en druk E.
- Bestaande missies, 12 NPC-dialogen, foto-interieurs en overige gameplay zijn behouden.

Let op: het padmasker is handmatig getekend op basis van de artistieke kaartachtergrond, niet op basis van echte geodata. Fijn-afstelling van paden en deurposities kan nodig zijn. Een volledige Java/Gradle-build is in deze omgeving niet mogelijk (Gradle ontbreekt); ZIP-inhoud is gecontroleerd.
