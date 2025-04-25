# Warehouse Control System Project

This project is a **console-based warehouse control system** developed as part of the *"Architektur von Anwendungssystemen"* course at TU Berlin (Summer Term 2024). It earned the **maximum score of 14/14 points**, as reflected in the included grading screenshot.

## Project Overview

The application allows warehouse operators to manage inventory efficiently by providing the following core features:

- Display of stored products, sorted by ID and type (unit or weighted)
- Search functionality based on free-text input, matching across product attributes
- Product management: adding new products, modifying quantities, and differentiating between single and weighted items
- Cost calculation: compute monthly storage costs, including surcharges for products requiring special care
- CSV file import/export to persist and restore warehouse data
- Basic test coverage of key functionalities using JUnit, including both average and edge cases

The system is built using **Java**, with **Maven** as the build tool and **Lombok** for cleaner code via annotations. 


## Setup Instructions

1. Load the Maven project using your preferred IDE (e.g., here: IntelliJ IDEA).

2. Enable annotation processing for Lombok:
   - Go to `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors` and enable it.

3. Start the application by running the `main` method in:  
   `src/main/java/de/mcc/Storehouse/app/StorehouseApp.java`


## License

This repository is for academic and portfolio purposes only.

