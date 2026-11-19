# System Architecture

## 1. Tech Stack
- **Language**: Java SE 11+ (OpenJDK LTS)
- **UI Framework**: Java Swing (`javax.swing.*`, `java.awt.*`)
- **Persistence**: Flat-file JSON/Text via `java.nio.file.Files`
- **Build System**: Maven (`pom.xml`)

## 2. Topology
- **Desktop Runtime (JRE)**: Executes local Java bytecode inside the host OS process.
- **UI Layer (`JFrame` / `GamePanel`)**: Handles direct pixel rendering via `Graphics2D` double-buffering, executing completely on the Swing Event Dispatch Thread (EDT).
- **Game Engine Timer (`javax.swing.Timer`)**: Fires non-blocking tick events every 100ms (scaling down to 40ms) on the EDT to update coordinates and invoke repaints.
- **Local Persistence Store (`.snake_highscore`)**: Flat-file storage located in user home directory (`System.getProperty("user.home")`), accessed synchronously on game initialization and high-score breaches.

## 3. Database Schema
Database: None — Single-player desktop application with zero complex relational requirements; local high scores are persisted using a lightweight flat-file config in the user home directory.

## 4. API Contracts
API: None — Monolithic desktop application running entirely in-memory without network boundaries or HTTP/WebSocket endpoints.

## 5. Directory Tree
```text
snake-game/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── arcade/
                    └── snake/
                        ├── Main.java
                        ├── GameFrame.java
                        ├── GamePanel.java
                        ├── ScorePanel.java
                        ├── GameEngine.java
                        ├── Board.java
                        ├── Snake.java
                        ├── Food.java
                        ├── Direction.java
                        └── ScoreManager.java
```

## 6. Security/Perf
- **EDT Concurrency Isolation**: All UI mutations, state updates, and key event listeners are strictly sequestered to the Swing Event Dispatch Thread to prevent race conditions and painting artifacts.
- **Garbage Collection Optimization**: Snake body segments utilize pre-allocated primitive coordinate arrays (`int[] x`, `int[] y`) sized to the maximum grid dimensions (e.g., 3600 elements), eliminating runtime object allocation during the game loop.
- **Filesystem Isolation**: High-score input/output operations are strictly bounded to the user home directory (`user.home/.snake_highscore`), neutralizing path traversal risks.
- **Zero-Latency Rendering**: Hardware-accelerated offscreen image double-buffering via `GamePanel.paintComponent()` maintains stable <16ms frame render times (60+ FPS target).