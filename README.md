# Campus Survivor: Enschede Edition — Release 2

Complete vernieuwde Java-game op basis van SaxionGameApp.

## Release 2
- Geïllustreerde 2.5D-kaart van Enschede met zeven locaties en ingangen.
- Pixel-art hoofdpersonage en 12 NPC's.
- Groene ingang-iconen die oplichten als de speler dichtbij komt.
- WASD: vrij bewegen op de kaart; E: gebouw binnengaan of NPC aanspreken.
- Foto-interieurs, 21 missies, statistieken, examens en gebeurtenissen.
- TAB: kaartoverzicht; Q: gebouw verlaten; 1/2/3: missies; 4: slapen in de flat.

## Starten (Windows)
Open een terminal in deze map en voer uit:

    .\gradlew.bat run

## Git: Release 2 naast Sprint 2 bewaren
Gebruik de **oude repository met zijn bestaande .git-map**. Maak daar eerst een
nieuwe branch `release-v2` en kopieer vervolgens de inhoud van deze map naar
de oude repository. Kopieer nooit een andere `.git`-map mee.
Zie `GIT-RELEASE-2-INSTRUCTIES.md` voor een veilig stappenplan.

## Opmerkingen
De kaart is een artistieke reconstructie en geen geografisch exacte plattegrond.
De interactieve posities zijn handmatig afgestemd. De game is in deze omgeving
niet volledig gecompileerd of live getest.
Oudere ontwikkelnotities staan in `docs/ontwikkeling/`.
