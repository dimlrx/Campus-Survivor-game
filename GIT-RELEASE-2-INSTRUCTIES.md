# Release 2 committen zonder Sprint 2 kwijt te raken

**Oude Git-repository:** `C:\Users\laryn\Documents\Campus-Survivor-Enschede`

**Nieuwe Release 2:** deze uitgepakte projectmap.

1. Maak eerst een reservekopie van de oude Git-repository.
2. Open PowerShell in de oude Git-repository.
3. Voer `git status` uit. Ga alleen door als er geen belangrijke ongecommitte wijzigingen zijn.
4. Voer `git switch -c release-v2` uit (als deze branch al bestaat: `git switch release-v2`).
5. Verwijder in de oude Git-repository de oude `src`-map. Kopieer daarna uit deze
   release de nieuwe `src`, `gradle`, `build.gradle`, `settings.gradle`,
   `gradlew`, `gradlew.bat`, `.gitignore`, `README.md`, `docs` en deze instructies.
   Laat de oude `.git`-map ongemoeid.
6. Verwijder eventueel overgebleven oude projectbestanden die niet in deze release
   horen, maar **nooit** `.git`. Kopieer geen `.gradle`, `.idea` of `build`.
7. Voer uit:

       git add -A
       git status
       git commit -m "feat: Release 2 - complete Campus Survivor redesign"
       git push -u origin release-v2

Op GitHub kun je daarna een Pull Request maken van `release-v2` naar `main`.
