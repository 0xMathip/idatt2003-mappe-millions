# Millions

Millions is a JavaFX-based stock trading simulation developed as part of the IDATx2003 Programming 2 course project.

The application allows users to simulate stock trading by buying and selling shares, managing a portfolio, tracking transactions, advancing trading weeks, and monitoring player progression through a graphical user interface.

## Features

- Start a new game with custom player name and starting capital
- Load stock data from CSV files
- Search and inspect available stocks
- Buy and sell shares
- Trade with leverage
- View portfolio holdings, performance, and transaction history
- Advance to the next trading week with updated stock prices
- Track net worth, XP, and player level
- Save and load game state

## Architecture

The project follows a layered architecture:

- `model` contains domain objects and application state
- `view` contains JavaFX pages, reusable components, factories, and UI utilities
- `controller` handles user interaction and coordinates application flow
- `service` contains business logic, calculations, summaries, and file handling

Implemented design patterns:

- MVC (Model View Controller)
- Observer
- Factory

## Technologies

- Java
- JavaFX
- Maven
- Ikonli
- JUnit 5
- CSS

## Project Structure

```text
src/
├── main/
│   ├── java/no/ntnu/group51/
│   │   ├── app/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── service/
│   │   └── view/
│   └── resources/
└── test/
```

## Running the Application

Compile:

```bash
mvn compile
```

Run tests:

```bash
mvn test
```

Package:

```bash
mvn package
```

Run application:

```bash
mvn javafx:run
```
## Contributors
Mathias J.
Martin M.
