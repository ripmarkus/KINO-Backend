# Simple Gantt Schema API - Uden DTO'er

## Filosofi: Brug dine eksisterende modeller!

I stedet for 100vis af DTO'er bruger vi dine eksisterende `Screening` og `Theatre` modeller direkte. 
Det er simplere, mindre kode og følger OOP principper.

## Data du skal bruge

### Eksisterende modeller (perfekte som de er):
- **`Screening`** - Filmvisning (har allerede startTime, endTime, theatre, movie)
- **`Theatre`** - Biografsal (navn, kapacitet)  
- **`Movie`** - Film (titel, varighed)

### Ingen nye DTO'er! 🎉

## API Endpoints (Meget simple)

### 1. Hent Screenings
```
GET /api/gantt/screenings?startDate=2024-01-15&endDate=2024-01-21&theatreId=1
```

**Response:** Array af `Screening` objekter direkte fra database
```json
[
  {
    "showId": 123,
    "startTime": "2024-01-15T19:00:00",
    "endTime": "2024-01-15T22:00:00",
    "status": "PLANNED",
    "theatre": {
      "theatreId": 1,
      "name": "Sal 1",
      "numRows": 10,
      "seatsPerRow": 10
    },
    "movie": {
      "movieId": 456,
      "title": "Avatar 3", 
      "duration": 180
    },
    "availableSeats": [...],
    "reservedSeats": [...]
  }
]
```

### 2. Hent Teatre  
```
GET /api/gantt/theatres
```

**Response:** Array af `Theatre` objekter
```json
[
  {
    "theatreId": 1,
    "name": "Sal 1",
    "numRows": 10,
    "seatsPerRow": 10
  }
]
```

### 3. Convenience Endpoints
```
GET /api/gantt/today      # Dagens screenings
GET /api/gantt/week       # Denne uges screenings
```

### 4. Opdater Screening (Drag & Drop)
```
PUT /api/gantt/screening/123
```

**Body (kun de felter du vil ændre):**
```json
{
  "startTime": "2024-01-15T20:00:00",
  "theatreId": 2
}
```

## Frontend Integration

### Data transformation (hvis nødvendigt)
```javascript
// Konverter Screening objekter til Gantt format
const ganttTasks = screenings.map(screening => ({
  id: screening.showId,
  name: screening.movie.title,
  start: screening.startTime,
  end: screening.endTime,
  resourceId: screening.theatre.theatreId,
  resourceName: screening.theatre.name,
  status: screening.status,
  // Beregn udsolgthed
  progress: ((screening.theatre.numRows * screening.theatre.seatsPerRow - screening.availableSeats.length) / 
           (screening.theatre.numRows * screening.theatre.seatsPerRow)) * 100
}));

// Ressourcer (sale)
const resources = theatres.map(theatre => ({
  id: theatre.theatreId,
  name: theatre.name,
  capacity: theatre.numRows * theatre.seatsPerRow
}));
```

### Eksempel med populære Gantt biblioteker

#### Frappe Gantt
```javascript
fetch('/api/gantt/screenings?startDate=2024-01-15&endDate=2024-01-21')
  .then(response => response.json())
  .then(screenings => {
    const tasks = screenings.map(s => ({
      id: s.showId,
      name: `${s.movie.title} (${s.theatre.name})`,
      start: s.startTime,
      end: s.endTime
    }));
    
    new Gantt("#gantt", tasks);
  });
```

#### dhtmlxGantt
```javascript
gantt.config.columns = [
  {name: "text", label: "Film", width: 200},
  {name: "theatre", label: "Sal", width: 100},
  {name: "duration", label: "Varighed", width: 80}
];

// Load data direkte fra API
gantt.load("/api/gantt/screenings?startDate=2024-01-15&endDate=2024-01-21");
```

## Fordele ved denne tilgang

### ✅ Fordele:
- **Ingen DTO'er** - Mindre kode at vedligeholde
- **Genbruger eksisterende modeller** - OOP principles
- **Nem at udvide** - Tilføj bare felter til eksisterende modeller
- **Type safety** - Frontend kender strukturen
- **Mindre kompleksitet** - Færre abstraktioner

### ⚠️ Ulemper:
- **Større payloads** - Sender alt data (men ofte ikke et problem)
- **Database struktur eksponeret** - Frontend ser database design
- **Mindre kontrol** over API contracts

## Implementering detaljer

### Udvidelse af eksisterende ScreeningService
```java
// Tilføjet til din eksisterende ScreeningService:
List<Screening> getScreeningsByDateRange(LocalDate start, LocalDate end, Integer theatreId);
List<Theatre> getAllTheatres();
Screening updateScreeningSchedule(Integer id, Map<String, Object> updates);
```

### Query optimering
```java
// Bruger dine eksisterende repository queries:
screeningRepo.findByStartTimeBetween(startDateTime, endDateTime);
screeningRepo.findByTheatreTheatreIdAndStartTimeBetween(...);
screeningRepo.findOverlappingScreenings(...); // Konflikt detection
```

## Eksempel Usage

### Hent denne uges filmplan
```bash
curl "/api/gantt/week"
```

### Flyt en filmvisning
```bash
curl -X PUT "/api/gantt/screening/123" \
  -H "Content-Type: application/json" \
  -d '{"startTime": "2024-01-15T21:00:00", "theatreId": 2}'
```

### Hent kun Sal 1's program
```bash
curl "/api/gantt/screenings?startDate=2024-01-15&endDate=2024-01-21&theatreId=1"
```

## Konklusion

Med denne tilgang får du et fuldt funktionelt Gantt API med:
- 🚀 **Minimal kode** (ingen DTO'er)
- 📈 **Nem vedligeholdelse** (genbruger eksisterende)
- ⚡ **Hurtig implementering** (bygger ovenpå det du har)
- 🔄 **Fleksibilitet** (nem at udvide modeller)

Dit Gantt chart vil vise filmvisninger som tidsbaserede bjælker fordelt på sale - præcis som du ønsker!