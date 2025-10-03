# Gantt Schema API Guide

## Oversigt
Dette API giver data til et Gantt schema der viser filmvisninger over tid fordelt på biografens sale. 

## Data Struktur

### Hovedmodeller
Du bruger dine eksisterende modeller:
- **`Screening`** - En filmvisning (har start/slut tid, sal, film)
- **`Theatre`** - En biografsal (ressource på Gantt chart)
- **`Movie`** - Film information (titel, varighed)

### DTO'er for Gantt
- **`GanttScheduleDTO`** - Hele schema dataen
- **`GanttTheatreDTO`** - Sale (ressourcer/rækker i Gantt)
- **`GanttScreeningDTO`** - Filmvisninger (opgaver/bjælker i Gantt)

## API Endpoints

### 1. Hent Schema Data
```
GET /api/gantt/schedule?startDate=2024-01-15&endDate=2024-01-21&theatreId=1
```

**Parameters:**
- `startDate` (required): Start dato (YYYY-MM-DD format)
- `endDate` (required): Slut dato (YYYY-MM-DD format) 
- `theatreId` (optional): Filtrer på specifik sal

**Response:**
```json
{
  "startDate": "2024-01-15",
  "endDate": "2024-01-21", 
  "theatres": [
    {
      "theatreId": 1,
      "name": "Sal 1",
      "capacity": 100
    }
  ],
  "screenings": [
    {
      "screeningId": 123,
      "movieTitle": "Avatar 3",
      "movieDuration": 180,
      "startTime": "2024-01-15T19:00:00",
      "endTime": "2024-01-15T22:00:00",
      "theatreId": 1,
      "theatreName": "Sal 1",
      "status": "PLANNED",
      "availableSeats": 85,
      "totalSeats": 100
    }
  ]
}
```

### 2. Convenience Endpoints

#### Dagens schema
```
GET /api/gantt/today
```

#### Denne uges schema  
```
GET /api/gantt/week
```

### 3. Opdater Screening (Drag & Drop)
```
PUT /api/gantt/screening/123
```

**Body:**
```json
{
  "newStartTime": "2024-01-15T20:00:00",
  "newTheatreId": 2
}
```

## Frontend Integration

### Gantt Chart Struktur
```javascript
// Sådan skal frontend strukturere dataen
const ganttData = {
  // Ressourcer (Y-akse rækker)
  resources: theatres.map(theatre => ({
    id: theatre.theatreId,
    name: theatre.name,
    capacity: theatre.capacity
  })),
  
  // Opgaver (tidsmæssige bjælker)
  tasks: screenings.map(screening => ({
    id: screening.screeningId,
    resourceId: screening.theatreId,
    name: screening.movieTitle,
    start: screening.startTime,
    end: screening.endTime,
    status: screening.status,
    progress: (screening.totalSeats - screening.availableSeats) / screening.totalSeats * 100
  }))
};
```

### Populære Gantt Biblioteker
- **dhtmlxGantt** (kommerciel)
- **Frappe Gantt** (gratis, simpel)
- **BRYNTUM Gantt** (kommerciel, avanceret)
- **React Gantt Chart** (til React)

### Eksempel med Frappe Gantt
```javascript
// Konverter API data til Frappe Gantt format
const tasks = screenings.map(screening => ({
  id: `screening-${screening.screeningId}`,
  name: `${screening.movieTitle} (${screening.theatreName})`,
  start: screening.startTime,
  end: screening.endTime,
  resource: screening.theatreId,
  progress: (screening.totalSeats - screening.availableSeats) / screening.totalSeats * 100
}));

const gantt = new Gantt("#gantt", tasks);
```

## Eksempel Queries

### Hent i dag's filmvisninger
```
GET /api/gantt/today
```

### Hent næste uges filmvisninger for Sal 1
```
GET /api/gantt/schedule?startDate=2024-01-22&endDate=2024-01-28&theatreId=1
```

### Flyt en filmvisning til anden tid/sal
```
PUT /api/gantt/screening/123
{
  "newStartTime": "2024-01-15T21:00:00",
  "newTheatreId": 2
}
```

## Features

### ✅ Implementeret
- Hent schema data for dato periode
- Filtrer på specifik sal  
- Convenience endpoints (i dag, denne uge)
- Opdater screening tid/sal
- Konflikt detektion ved opdatering

### 🚀 Mulige Udvidelser
- Bulk opdateringer af flere screenings
- Optimering af sal udnyttelse
- Automatisk generering af pauser mellem film
- Integration med booking system
- Real-time opdateringer via WebSocket

## Database Queries
API'en bruger disse optimerede queries:
- `findByStartTimeBetween()` - Hent screenings i periode
- `findByTheatreTheatreIdAndStartTimeBetween()` - Filtrer på sal
- `findOverlappingScreenings()` - Tjek konflikter