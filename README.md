# DenominationCalculator

## Aufgabenstellung 
Geschrieben werden soll eine Applikation, die auf einer Weboberfläche für einen gegebenen Betrag die Stückelung des Betrages in Euro zurück gibt. 
Hierbei sollen immer die größtmöglichen Scheine, bzw. Münzen zuerst genommen werden, damit soll erreicht werden, dass möglichst wenige Scheine und Münzen benötigt werden.
Nachdem die Berechnung einmal stattgefunden hat, soll bei der nächsten Berechnung angezeigt werden, wie sich die Stückelung zur vorhergehenden Ausgabe geändert hat.

Der Berechnungsteil der Applikation soll im Frontend oder im Backend ausgeführt werden. Dies soll über die Oberfläche umschaltbar sein.
Für das Frontend soll Angular 14+, Vue.js oder React verwendet werden. Im Backend frei in der Verwendung, bevorzugt Java auf einem beliebigen Webframework (Java EE, Spring Boot oder ähnlich).

## Berechnungslogik
- Zur Berechnung der Stückelung wird Greedy Algorithmus implementiert
- Der zu zerstückelnde Wert wird als Parameter entgegengenommen
- Alle Ausprägungen von Scheinen & Münzen werden nach Wert absteigend sortiert
- Solange der größtmögliche Schein/Münze größer als der Wert ist, wird dieser subtrahiert
- Anschließend wird zum nächst größeren Schein/Münze iteriert


- Zur Berechnung der Differenz werden die letzten beiden Zerstücklungen als Parameter entgegengenommen
- Die Anzahl der gleichartigen Scheine/Münzen wird voneinander subtrahiert 

## Konzeption
### MVP-1: Berechnung im Backend
- User Input wird im Frontend entgegengenommen
- User Input wird im Frontend validiert (Zahlen größer 0 mit maximal zwei Nachkommstellen)
- Input wird an Post-Endpunkt im Backend gesendet
- Backend berechnet Zerstückelung und gibt diese an Frontend zurück
- Frontend zeigt das Ergebnis in Tabellenform an und hält die Zerstückelung im Speicher
- Nach erneuter Anfrage ans Backend wird die neue Zerstückelung mit der vorherigen Zerstückelung verglichen und die Differenz wird gebildet

### MVP-2: Persistenz der Berechnung
- Da die Information über vergangene Zerstückelungen eines Nutzers beim Neuladen verloren gehen, sollen die Zerstückelungen für jeden Benutzer persistiert werden
- Dazu stellt das Backend einen Endpunkt bereit, der einem Nutzer ein UserToken zuweist
- Das Frontend fragt diesen Endpunkt beim ersten Laden der Seite ab und speichert das Token im LocalStorage
- Bei jeder weiteren Anfrage ans Backend wird das Token im Header mitgesendet
- Auf diese Weise kann das Backend den User identifizieren und die Zerstückelungen pro User persitieren und abfragen

### MVP-3: Berechnung im Frontend
- Der Nutzer kann über Radio Buttons steuern, ob die Berechnung der Zerstückelung im Backend oder im Frontend passieren soll
- Die Backendlogik bleibt unverändert und wird als Default gewählt
- Wählt der Nutzer die Berechnung im Frontend, wird die Zerstückelung im Frontend berechnet und angezeigt
- Anschließend wird das berechnete Ergebnis ans Backend gesendet, um den Wert zu persistieren
- Bei erneuter Berechnung wird das vorherige Ergebnis vom Backend abgerufen und mit dem neu berechneten Ergebnis im Frontend verglichen, um die Differenz zu bilden.


## Implementiereung
### Backend
- Das Backend wird mit Spring Boot implementiert
- Endpunkte:
  - `/v1/user/token`: Erstellt ein User Token das im Frontend gespeichert wird
  - `/v1/denomination/calculate`: Nimmt den Eingabewert vom User entgegen und berechnet die Zerstückelung
  - `/v1/denomination/last-calculation`: Gibt die letzte Zerstücklung eines Users zurück
  - `/v1/denomination/persist`: Persistiert eine Berechnung aus dem Frontend
- Services: 
  - `DenominationService`: Zentraler Service, der Controller, Logik und Persistenz verbindet
  - `CalculationService`: Führt die Berechnungslogik aus
  - `DenominationPersistenzService`: Persistiert Berechnungen und fragt diese ab

### Frontend
- Das Frontend wird mit React implementiert
- Components:
  - `App`: Applikation die die anderen Components einbindet
  - `CalculationOption`: Auswahl für den Nutzer, ob im Backend oder Frontend berechnet werden soll
  - `UserInput`: Input Feld für den Nutzer um den Wert anzugeben der Zerstückelt werden soll
  - `DenominationResultTable`: Container der das Ergebnis der Zerstückelung und die Differenz zur vorherigen Zerstückelung in tabellarischer Form anzeigt