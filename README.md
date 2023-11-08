# Openvolt API

API with a single endpoint to calculate energy data for a single building.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Build the Project](#build-the-project)
    - [Run the Application](#run-the-application)
- [API Endpoints](#api-endpoints)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 8 or higher installed.
- Apache Maven installed (for building the project).

## Getting Started

Follow these steps to set up and run the project locally.

### Clone the Repository

```bash
git clone https://github.com/GiatrGio/openvolt.git
cd energyAPI
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## API Endpoints

### Energy Data Endpoints

- Get building energy data: GET `http://localhost:8080/api/energyData/building`
