<!-- # event-api

## 📁 Endpoints

### URL padrão
```
/events
```

### 1. Criar Novo Evento
```
POST /events
Content-Type: application/json
```

**Body:**
```json
{
  "title": "Tech Summit 2025",
  "description": "Um evento focado em inovação e tecnologia.",
  "address": {
    "street": "Av. Paulista",
    "number": "1000",
    "city": "São Paulo",
    "state": "SP",
    "zipCode": "01310-000"
  },
  "eventDate": "2025-12-20",
  "organizerId": "d2c4d9b2-5e63-4bd8-88fb-9f8d0f2b7a11"
}
```

---

### 2. Listar Todos os Eventos
```
GET /events
```

---

### 3. Buscar Eventos por Título
```
GET /events/{eventTitle}
```

---

### 4. Atualizar o Status de um Evento
```
PATCH /events/{eventId}/status
Content-Type: application/json

{
  "status": "FINISHED",
  "requesterId": "d2c4d9b2-5e63-4bd8-88fb-9f8d0f2b7a11"
}
``` -->
