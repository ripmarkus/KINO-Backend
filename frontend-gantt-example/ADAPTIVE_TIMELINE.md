# 🕐 Adaptiv Film Timeline - Smart Tidsramme

## Koncept: Intelligent tidsvisning

Timeline'en tilpasser automatisk tidsrammen baseret på **faktiske filmvisninger** - ingen spild af skærmplads på tomme nattetimer!

## 🧠 Smart Tidslogik

### **Scenario 1: Normal biograf dag (08:00-24:00)**
```
Screenings: 16:00, 18:30, 21:30
Timeline:   |08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23|
Result:     Viser 15:00-24:00 (med buffer)
```

### **Scenario 2: Kun aften screenings (16:00-23:00)**  
```
Screenings: 16:00, 19:00, 21:30
Timeline:   |15|16|17|18|19|20|21|22|23|24|
Result:     Viser kun 15:00-24:00 (spring 03:00-07:00 over)
```

### **Scenario 3: Nattevisninger inkluderet (22:00-03:00)**
```
Screenings: 22:00, 00:30, 02:45
Timeline:   |00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23|
Result:     Viser hele døgnet (00:00-24:00)
```

## 📊 Algoritme detaljer

```javascript
calculateOptimalTimeRange(screenings) {
    // 1. Find tidligste og seneste screening
    let earliestHour = 24, latestHour = 0;
    
    screenings.forEach(screening => {
        earliestHour = Math.min(earliestHour, startHour);
        latestHour = Math.max(latestHour, endHour);
    });
    
    // 2. Tilføj buffer timer
    earliestHour = Math.max(0, earliestHour - 1);
    latestHour = Math.min(24, latestHour + 1);
    
    // 3. Smart nattetid håndtering
    if (earliestHour >= 8 && latestHour <= 3) {
        return { start: 8, end: 24 };        // Skip 03:00-07:00
    } else if (earliestHour <= 3 || latestHour >= 22) {
        return { start: 0, end: 24 };        // Vis hele døgnet  
    } else {
        return { start: earliestHour, end: latestHour }; // Tilpasset range
    }
}
```

## 🎯 Fordele

### ✅ **Optimal skærmudnyttelse**
- Ingen tomme nattetimer (03:00-07:00) medmindre nødvendigt
- Fokuserer på aktivitetsperioder
- Bedre oversigt på mindre skærme

### ✅ **Automatisk tilpasning** 
- Ingen manuel konfiguration nødvendig
- Tilpasser sig forskellige biografers åbningstider
- Håndterer både dag- og nattevisninger

### ✅ **Kontekstuel information**
- Viser aktuel tidsramme i titlen: "(16:00 - 24:00)"
- Console logs optimal range beregning
- Visual feedback om hvad der vises

## 📱 Responsive Adfærd

### **Desktop (bred skærm)**
```
[Sal 1] |15|16|17|18|19|20|21|22|23|24|
        ████████████████████████████████
```

### **Mobile (smal skærm)** 
```
[Sal 1] |16|17|18|19|20|21|22|23|
        ████████████████████████████
```

## 🔧 Teknisk Implementation

### **Dynamic CSS Grid**
```javascript
// Header
`<div class="timeline-grid" style="grid-template-columns: repeat(${totalHours}, 1fr);">`

// Time lines  
`<div class="hour-lines" style="grid-template-columns: repeat(${totalHours}, 1fr);">`
```

### **Relative Position Beregning**
```javascript
timeToPercent(date) {
    const totalMinutes = hours * 60 + minutes;
    const visibleStartMinutes = this.visibleHours.start * 60;
    const relativeMinutes = totalMinutes - visibleStartMinutes;
    
    return (relativeMinutes / visibleTotalMinutes) * 100;
}
```

## 🎬 Eksempel Resultater

Med dine **The Matrix** screenings:
- **16:00-17:45** (105 min)
- **18:30-20:55** (145 min)  
- **21:30-23:40** (130 min)

**Timeline viser:** `15:00 - 24:00` (9 timer i stedet for 24)

**Titel opdateres til:** `🎬 KinoXP Film Timeline (15:00 - 24:00)`

## 🚀 Brug

```bash
# Åbn den adaptive timeline
open simple-gantt-v2.html
```

**Automatisk tilpasning til:**
- ☀️ **Dagens screenings** - viser kun relevante timer
- 🌙 **Nattevisninger** - inkluderer nattetimer hvis nødvendigt  
- 📅 **Ugens program** - optimal range for hele ugen
- 🎯 **Alle screenings** - fuld oversigt med smart range

Timeline'en **tænker selv** og viser kun det der er relevant! ⚡