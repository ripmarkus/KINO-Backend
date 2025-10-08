# KinoXP Frappe Gantt Frontend Eksempel

## 🎬 Komplet JavaScript implementering med Frappe Gantt

Dette er et fuldt funktionsdygtigt frontend eksempel der viser hvordan du implementerer Gantt schema med **Frappe Gantt** og dine KinoXP API endpoints.

## 📁 Filer

### 1. `index.html` - Hovedsiden
- Komplet HTML struktur med moderne CSS
- Indlæser Frappe Gantt fra CDN
- Controls til datovælger, sal filter, etc.
- Statistik cards
- Responsive design

### 2. `gantt-api.js` - API Client
```javascript
class KinoAPI {
    // Håndterer alle API calls til dit backend
    async getScreenings(startDate, endDate, theatreId)
    async getTheatres()
    async getTodayScreenings()
    async getWeekScreenings()  
    async updateScreening(screeningId, updates)
}

class GanttDataTransformer {
    // Konverterer dine Screening objekter til Frappe Gantt format
    static screeningsToTasks(screenings)
    static generateStats(screenings)
    static formatDate(date)
}
```

### 3. `gantt-ui.js` - UI Controller
```javascript
class KinoGanttUI {
    // Håndterer hele UI og user interactions
    async loadSchedule()    // Custom dato periode
    async loadToday()       // I dag
    async loadWeek()        // Denne uge
    displayGantt(screenings) // Vis Gantt chart
    handleTaskDateChange()  // Drag & drop
    showTaskDetails()       // Click på filmvisning
}
```

## 🚀 Sådan bruger du det

### 1. Start dit KinoXP backend
```bash
cd "/path/to/KinoXP"
mvn spring-boot:run
```

### 2. Åbn frontend
```bash
# Serve filerne (fx med Python)
cd frontend-gantt-example
python -m http.server 8000

# Eller bare åbn index.html direkte i browser
open index.html
```

### 3. Opdater API URL (hvis nødvendigt)
I `gantt-api.js`, linje 7:
```javascript
constructor(baseUrl = 'http://localhost:8080/api') {
```

## 🎯 Features

### ✅ Implementeret
- **Dato filtering** - Vælg start/slut dato
- **Sal filtering** - Vis kun specifik sal
- **Convenience buttons** - "I dag", "Denne uge"
- **Drag & Drop** - Flyt filmvisninger til ny tid
- **Click for detaljer** - Se filminfo, sæder, etc.
- **Statistikker** - Antal film, sale, belægningsgrad
- **Status farver** - Forskellige farver efter screening status
- **Error håndtering** - Brugervenlige fejlmeddelelser
- **Loading states** - Viser loading under API calls

### 📊 Data Transformation
```javascript
// Din API returnerer Screening objekter:
{
  "showId": 123,
  "startTime": "2024-01-15T19:00:00",
  "endTime": "2024-01-15T22:00:00", 
  "movie": { "title": "Avatar 3", "duration": 180 },
  "theatre": { "theatreId": 1, "name": "Sal 1" },
  "status": "PLANNED"
}

// Konverteres automatisk til Frappe Gantt format:
{
  "id": "screening-123",
  "name": "Avatar 3 (Sal 1)",
  "start": Date object,
  "end": Date object,
  "progress": 75, // Baseret på sæde reservationer
  "custom_class": "PLANNED" // For CSS styling
}
```

### 🎨 UI Features

#### Controls
- **Datovælger** - Start og slut dato
- **Sal dropdown** - Filtrer på specifik sal (populeres fra API)
- **Quick buttons** - I dag, denne uge
- **Opdater knap** - Genindlæs med nye filtre

#### Gantt Chart
- **View modes** - Dag, uge, måned visning
- **Drag & drop** - Flyt filmvisninger
- **Click events** - Vis detaljer popup
- **Color coding** - Status farver (planlagt, igangværende, etc.)
- **Progress bars** - Viser sæde udsolgthed

#### Statistikker
- **Antal filmvisninger** - Total i periode
- **Aktive sale** - Hvor mange sale brugt
- **Forskellige film** - Unikke film titler
- **Belægningsgrad** - Gennemsnitlig sæde udnyttelse

## 🔧 Customization

### Ændre farver
I `index.html` CSS:
```css
.gantt .bar.PLANNED { fill: #3498db; }
.gantt .bar.IN_PROGRESS { fill: #f39c12; }
.gantt .bar.COMPLETED { fill: #27ae60; }
.gantt .bar.CANCELLED { fill: #e74c3c; }
```

### Tilføj nye view modes
I `gantt-ui.js`:
```javascript
view_modes: ['Quarter Day', 'Half Day', 'Day', 'Week', 'Month', 'Year']
```

### Custom popup
Erstat `alert()` i `showTaskDetails()` med modal eller tooltip library.

### Tilføj flere statistikker
Udvid `generateStats()` metoden i `gantt-api.js`.

## 📱 Responsive Design

Interface er responsivt og virker på:
- 💻 Desktop
- 📱 Tablets  
- 📱 Mobile (controls wrapper til flere linjer)

## 🔗 API Integration

Frontend kalder dine endpoints:
```javascript
GET /api/gantt/screenings?startDate=2024-01-15&endDate=2024-01-21&theatreId=1
GET /api/gantt/theatres
GET /api/gantt/today
GET /api/gantt/week
PUT /api/gantt/screening/123
```

## 🚀 Næste skridt

### Mulige udvidelser:
1. **WebSocket** - Real-time opdateringer
2. **Toast notifications** - Erstat alert() og console.log()
3. **Modal dialogs** - Bedre task detaljer
4. **Bulk operations** - Flyt flere screenings ad gangen
5. **Export funktioner** - PDF, Excel
6. **Print view** - Printer-venlig visning
7. **Keyboard shortcuts** - Power user features
8. **Undo/Redo** - Fortryd handlinger

### Production klargøring:
1. **Minify JavaScript** - Reducer filstørrelse
2. **Bundle assets** - Webpack/Vite setup
3. **Error tracking** - Sentry integration
4. **Performance monitoring** - Mål load times
5. **Progressive Web App** - Offline support

## 💡 Tip

Start med denne eksempel implementering og tilpas den til dine behov. Frappe Gantt er let at arbejde med og har god dokumentation på: https://frappe.io/gantt