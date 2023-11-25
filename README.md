# Stock Market Application

A stock market android app developed using multimodule clean architecture and jetpack compose.

## Tech stack
- UI: Jetpack Compose
- Navigation: Compose navigation
- DB: Room Database
- Network: Retrofit
- DI: Hilt
- Architecture: Multi-module Clean Architecture
    - App Module: UI layer
    - Domain Module: Business Logic layer
    - Data Module: Data layer
- Gradle Builder: Kotlin DSL
- Dependency Management: Version catalogs
## Features
- **Caching Using Room DB**: Data fetched from remote data source is saved in local database
- **Single Source of Truth**: Data source models and domain entities are mapped using mappers to avoid easy data tampering
- **Unidirectional Data Flow**: UI states flow from data module towards UI module and UI Events flow in the opposite direction
- **Separation of Concern**: UI layer handles UI related logics, domain layer handles business logic and data layer handles application data
![img](https://github.com/HagosAlema/stock-market/screenshots/img.png)
![stock_search](https://github.com/HagosAlema/stock-market/screenshots/stock_search.png)
![stock_detail](https://github.com/HagosAlema/stock-market/screenshots/stock_detail.png)
