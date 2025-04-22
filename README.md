# Zoo Management System

Веб-приложение для управления зоопарком, разработанное на Java с использованием Spring Boot, Clean Architecture и Domain-Driven Design.

## Архитектура проекта

Проект разделён на четыре модуля:

- **domain** — доменная логика: бизнес-сущности, value-объекты, доменные события, интерфейсы репозиториев
- **application** — логика использования: сервисы (use cases), DTO, специфичные исключения
- **infrastructure** — реализация интерфейсов из domain и application: in-memory репозитории, публикатор событий и т.д.
- **presentation** — слой REST API: контроллеры Spring MVC, обработка HTTP-запросов и ответов

## Как запустить

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Euggenius/java-hw-3
   ```
2. Убедитесь, что установлены JDK 17+ и Maven.
3. Перейдите в корневую папку проекта:
   ```bash
   cd zoo-management
   ```
4. Соберите проект:
   ```bash
   mvn clean package
   ```
5. Запустите приложение:
   ```bash
   java -jar presentation/target/presentation-0.0.1-SNAPSHOT.jar
   ```

После запуска приложение будет доступно по адресу:  
📍 `http://localhost:8080`

## Документация API

Для взаимодействия с API откройте Swagger UI:  
📍 `http://localhost:8080/swagger-ui.html`

Через него вы можете отправлять запросы, смотреть доступные эндпоинты и их параметры.

## Основные эндпоинты

- **Животные**
  - `GET /api/animals`
  - `POST /api/animals`
  - `GET /api/animals/{id}`
  - `DELETE /api/animals/{id}`

- **Вольеры**
  - `GET /api/enclosures`
  - `POST /api/enclosures`
  - `GET /api/enclosures/{id}`
  - `DELETE /api/enclosures/{id}`

- **Расписание кормлений**
  - `GET /api/feeding-schedule`
  - `POST /api/feeding-schedule`
  - `GET /api/feeding-schedule/{id}`
  - `DELETE /api/feeding-schedule/{id}`
  - `GET /api/feeding-schedule/animal/{animalId}`
  - `POST /api/feeding-schedule/{id}/mark-done`

- **Операции**
  - `POST /api/operations/animals/{animalId}/transfer/{targetEnclosureId}`

- **Статистика**
  - `GET /api/statistics`
