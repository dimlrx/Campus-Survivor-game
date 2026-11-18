# Campus Survivor — Enschede 3D-modellen (prototype)

Deze ZIP bevat de bestaande SaxionGameApp-game (inclusief foto-locaties, missies en Jumbo) en **zes nieuwe originele 3D-modellen** als Wavefront OBJ/MTL.

## Modellen

- `saxion.obj` — campusgebouw met glazen entree en raamgevel
- `studentenflat.obj` — bakstenen studentenflat met meerdere verdiepingen
- `kennispark.obj` — modern kantoorgebouw met glaspartijen
- `jumbo_werk.obj` — supermarkt met gele gevelstrook en glazen entree
- `oude_markt.obj` — rij horecapanden met terrassen
- `centrum_van_heekplein.obj` — winkelplein met gevels en bomen

Locatie: `src/main/resources/models/enschede/`. De zes OBJ-bestanden verwijzen naar `enschede.mtl` in dezelfde map. Open ze bijvoorbeeld in Blender via File > Import > Wavefront (.obj). De modellen gebruiken meters als ontwerpeenheden, met Z als hoogte-as. Ze zijn **handmatig/procedureel gemaakte benaderingen**, geen scans of exacte digitale kopieën van de echte panden. De meegeleverde foto's zijn vooral referentie voor de locatie en binnenruimtes; niet alle geveldetails zijn zichtbaar op die foto's.

**Belangrijk:** SaxionGameApp in dit project rendert nog 2D/2.5D. De OBJ-modellen zijn **meegeleverde 3D-assets en worden nog niet automatisch in de game getoond**. Daarvoor is een afzonderlijke 3D-renderlaag of vooraf gerenderde sprites nodig. De bestaande speelbare code is niet vervangen, om de gameplay niet te breken.

## Start de bestaande game

Windows: `gradlew.bat run`

macOS/Linux: `bash gradlew run`

## Controle

Alle zes OBJ-bestanden zijn gegenereerd met gesloten quads voor de gebouwonderdelen en verwijzen naar een bestaande MTL. De ZIP is op integriteit gecontroleerd. De volledige Gradle-build en grafische 3D-integratie zijn niet getest.
