# Отчёт по разработке Zoo Management System

## 1. Реализованный функционал

- **Животные:**  
  – `POST /api/animals` -> `AnimalService.addAnimal`  
  – `GET /api/animals[/{id}]` -> `AnimalService.getAll/getById`  
  – `DELETE /api/animals/{id}` -> `AnimalService.removeAnimal`  

- **Вольеры:**  
  – `POST /api/enclosures` -> `EnclosureService.addEnclosure`  
  – `GET /api/enclosures[/{id}]` -> `EnclosureService.getAll/getById`  
  – `DELETE /api/enclosures/{id}` -> `EnclosureService.removeEnclosure`  

- **Перемещение:**  
  – `POST /api/operations/animals/{animalId}/transfer/{targetEnclosureId}` -> `AnimalTransferService.transferAnimal` + `AnimalMovedEvent`  

- **Кормления:**  
  – `GET /api/feeding-schedule[/{id}|/animal/{animalId}]` -> `FeedingOrganizationService.get…`  
  – `POST /api/feeding-schedule` -> `FeedingOrganizationService.addFeedingSchedule`  
  – `POST /api/feeding-schedule/{id}/mark-done` -> `FeedingOrganizationService.markFeedingDone`  
  – `DELETE /api/feeding-schedule/{id}` -> `FeedingOrganizationService.deleteSchedule`  

- **Статистика:**  
  – `GET /api/statistics` -> `ZooStatisticsService.getStatistics`

## 2. DDD

- **Entities:** `Animal`, `Enclosure`, `FeedingSchedule` (идентификаторы как VO: `AnimalId`, `EnclosureId`, `FeedingScheduleId`)  
- **Value Objects:** `Species` (name + isPredator), `FoodType`, `Gender`, `HealthStatus`, `EnclosureType` (логика совместимости)  
- **Aggregates:** корни – `Animal`, `Enclosure`, `FeedingSchedule` (методы контроля инвариантов внутри)  
- **Repositories:** интерфейсы в `domain`, реализации `InMemory…` в `infrastructure`  
- **Domain Events:** `AnimalMovedEvent`, `FeedingTimeEvent`; публикация через `LoggingDomainEventPublisher`

## 3. Clean Architecture

- **Модули:**  
  `domain` <- `application` -> (`infrastructure`, `presentation`)  
- **Зависимости через интерфейсы:**  
  `application` -> репозитории `domain`;  
  `infrastructure` -> реализации;  
  `presentation` -> `application`  
- **Изоляция логики:**  
  бизнес-правила и валидация в `domain`, координация в `application`, HTTP и хранение только во внешних слоях

## 4. Тестирование

- Swagger UI: `http://localhost:8080/swagger-ui.html`  
- Unit‑тесты: покрытие ≥ 65% (пока не реализовано)