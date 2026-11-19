# Product Requirements Document: Java Swing Snake Game

## 1. Overview & Goals
- **Core Problem**: Deliver a lightweight, nostalgic, desktop-native Snake arcade experience utilizing Java SE and Swing without complex external web dependencies.
- **User Benefits**: Instant offline playability, zero-latency frame-rate responsiveness, and persistent local high-score tracking.
- **KPI Metrics**: Frame render latency < 16ms (stable 60 FPS), zero memory leaks over 1-hour sessions, high-score write success rate = 100%.

## 2. Personas (Compact)
- [Casual Desktop Gamer]: Wants an instant-launch, distraction-free arcade game to pass time offline.
- [Retro Enthusiast]: Expects authentic grid-based movement, escalating velocity scaling, and classic high-score competition.

## 3. User Stories & Epics (Dense List)
* [Game Loop & Control] As a gamer, I want to control a snake using arrow keys so that I can navigate the board smoothly without self-intersection inputs.
  - AC: [1] Given the game is active, When UP is pressed, Then set direction to NORTH unless moving SOUTH. [2] Given key inputs, When multiple keys are pressed within one tick, Then process only the first valid directional change. [3] Given game over state, When any key is pressed, Then ignore inputs until restart.
* [Scoring & Food Mechanics] As a competitive player, I want food to spawn randomly and increase my score and length so that the game becomes progressively harder.
  - AC: [1] Given the snake eats food, When collision is detected, Then snake length increments by 1 and score increases by 10. [2] Given food spawns, When generated, Then coordinates must not overlap with the snake's current body segments. [3] Given score updates, When a new high score is reached, Then persist immediately to local storage.
* [State Management] As a user, I want to pause, resume, and restart the game via UI controls or keyboard shortcuts so that I can manage my play session.
  - AC: [1] Given an active game, When SPACE is pressed, Then toggle pause state and display overlay. [2] Given game over state, When 'R' is pressed, Then reset grid, snake, and score to default values.

## 4. Functional Specs (Bullet Tree)
- **UI Architecture (Java Swing)**
  - `JFrame` (Main Window): Fixed dimensions (600x640px), non-resizable, centers on display.
  - `GamePanel` (View/Controller): Custom `JPanel` extending component rendering via `Graphics2D` double-buffering.
  - `ScorePanel` (Header): Displays current score and persistent high score using `JLabel` components.
- **Game Engine & Rules**
  - `Timer` (Tick Rate): Fires every 100ms (10 ticks/sec base speed), scaling down by 2ms every 5 points scored (min limit 40ms).
  - Collision Detection:
    - Wall Collision -> Success: Trigger Game Over state, Error: N/A.
    - Self Collision -> Success: Trigger Game Over state, Error: N/A.
    - Food Collision -> Success: Grow snake array, respawn food via RNG, increment score. Error: Retry RNG placement if coordinates conflict with snake body.
- **Data Persistence**
  - High Score Storage -> Success: Write integer to local `.snake_highscore` flat file, Error: Fallback to session memory default (0) if I/O fails.

## 5. Non-Functional Requirements
- **Performance**: Maintain steady 60 FPS rendering within Swing EDT (Event Dispatch Thread); garbage collection footprint minimized via pre-allocated coordinate arrays.
- **Compatibility**: Compatible with Java Runtime Environment (JRE) 8 through 21 across Windows, macOS, and Linux without native library dependencies.
- **Concurrency**: Thread-safe UI updates isolated strictly to the EDT via `SwingUtilities.invokeLater()` and `javax.swing.Timer`.
- **Security**: Zero external network calls; local file writes restricted strictly to user-home application config directory to prevent arbitrary filesystem exposure.

## 6. Out of Scope & Release Plan
- **Out of Scope**:
  - Multiplayer networking or online leaderboards.
  - Sound effects, background music, or custom sprite asset packs (vector-drawn primitives only).
  - Mobile touch controls or responsive web/browser deployment (Swing desktop runtime only).
- **Release Plan**:
  - *Phase 1 (Alpha)*: Core grid rendering, manual snake movement, basic wall/food collision logic, and local console logging.
  - *Phase 2 (Beta)*: Swing UI integration, HUD scoreboards, persistent file-based high scores, and pause/restart states.
  - *Phase 3 (GA)*: Final performance profiling on JRE 11/17, packaging into an executable `.jar` artifact with manifest classpath.