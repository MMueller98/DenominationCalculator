# DenominationCalculator

## Aufgabenstellung
Erstellt werden soll eine Webanwendung, die für einen eingegebenen Betrag die optimale Stückelung in Euro-Scheine und Münzen berechnet. Dabei wird sichergestellt, dass die größtmöglichen Einheiten zuerst verwendet werden, um die Anzahl der Scheine und Münzen zu minimieren. 

Die Anwendung bietet folgende Kernfunktionen:
- Anzeige der Stückelung.
- Vergleich der aktuellen Berechnung mit der vorherigen.
- Umschaltbare Berechnung zwischen Frontend und Backend.

### Technologieanforderungen
- **Frontend:** Angular 14+, Vue.js oder React.
- **Backend:** Bevorzugt Java mit einem Webframework wie Java EE oder Spring Boot.

---

## Berechnungslogik
1. **Grundlogik zur Stückelung:**
   - Ein Greedy-Algorithmus berechnet die Stückelung.
   - Eingabewert wird als Parameter übergeben.
   - Alle verfügbaren Scheine und Münzen werden absteigend sortiert.
   - Solange der größte Schein oder die größte Münze kleiner oder gleich dem verbleibenden Wert ist, wird dieser subtrahiert.
   - Iteration zur nächstkleineren Einheit.

2. **Berechnung der Differenz:**
   - Zwei Stückelungen werden als Parameter übergeben.
   - Anzahl der gleichen Scheine/Münzen wird voneinander subtrahiert.

---

## Konzeption

### **MVP-1: Berechnung im Backend**
- **Ablauf:**
  1. Eingabe des Wertes im Frontend.
  2. Validierung der Eingabe (Zahl > 0, max. zwei Nachkommastellen).
  3. Übergabe der Eingabe an einen POST-Endpunkt des Backends.
  4. Backend berechnet die Stückelung und liefert das Ergebnis zurück.
  5. Frontend zeigt das Ergebnis in Tabellenform an und speichert die Stückelung im Speicher.
  6. Bei erneuter Berechnung vergleicht das Backend die neue Stückelung mit der vorherigen und liefert die Differenz.

---

### **MVP-2: Persistenz der Berechnung**
- Problem: Vergangene Stückelungen gehen beim Neuladen der Seite verloren.
- Lösung:
  - Backend weist jedem Nutzer ein **UserToken** zu (Endpunkt: `/v1/user/token`).
  - Frontend speichert das Token im LocalStorage.
  - Bei Backend-Anfragen wird das Token im Header mitgesendet, um den Nutzer zu identifizieren.
  - Das Backend persistiert und gibt die Stückelungen des Nutzers zurück.

---

### **MVP-3: Berechnung im Frontend**
- Der Nutzer wählt über Radio-Buttons, ob die Berechnung im Backend oder im Frontend erfolgen soll.
- Standardmäßig ist die Backend-Berechnung aktiviert.
- Bei Frontend-Berechnung:
  1. Frontend berechnet die Stückelung.
  2. Berechnetes Ergebnis wird ans Backend gesendet, um es zu persistieren.
  3. Vergleiche erfolgen lokal im Frontend basierend auf den gespeicherten Daten.

---

## Implementierung

### Backend
- **Technologie:** Spring Boot
- **Endpunkte:**
  - `/v1/user/token`: Generiert ein UserToken.
  - `/v1/denomination/calculate`: Nimmt den Betrag entgegen und berechnet die Stückelung.
  - `/v1/denomination/last-calculation`: Gibt die letzte Stückelung eines Nutzers zurück.
  - `/v1/denomination/persist`: Persistiert eine Frontend-Berechnung.
- **Services:**
  - `DenominationService`: Verbindet Controller, Logik und Persistenz.
  - `CalculationService`: Implementiert die Berechnungslogik.
  - `DenominationPersistenzService`: Verantwortlich für die Persistenz von Berechnungen.

---

### Frontend
- **Technologie:** React
- **Komponenten:**
  - **App:** Hauptkomponente der Anwendung.
  - **CalculationOption:** Auswahlfeld für die Berechnungsmethode (Frontend/Backend).
  - **UserInput:** Eingabefeld für den zu berechnenden Betrag.
  - **DenominationResultTable:** Tabelle zur Anzeige der Stückelung und des Vergleichs mit der vorherigen Berechnung.
