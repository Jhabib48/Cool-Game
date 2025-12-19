# Cool-Game
Sum-Awesome Game: A Java math-based combat game featuring addition puzzles on a 3x3 grid. Complete fills to attack opponents using weapons and rings with special abilities. Implements Observer and Null Object patterns with clean MVC architecture.


Sum-Awesome Game - CMPT 213 Assignment 4
A strategic mathematics-based combat game implementation featuring addition-based gameplay, equipment systems, and design pattern adherence. Developed as part of the CMPT 213 Software Engineering curriculum at Simon Fraser University.

Project Overview
This Java application implements a turn-based game where players solve addition problems on a 3x3 grid to complete "fills" and attack opponent characters. The game features an extensible equipment system, comprehensive statistics tracking, and a clean architectural design following SOLID principles.

Core Features
Gameplay Mechanics
3×3 Number Grid System: Players enter sums matching center + outer cell values

Fill Completion System: Complete all 8 outer cells to trigger attacks

Position-Based Targeting: Attacks target left, middle, or right opponents based on final filled cell position

Health Management: Balance offensive moves with defensive considerations against random enemy attacks

Equipment System
Six Unique Weapons: Each with distinct activation conditions and targeting behaviors

Six Magical Rings: Provide damage bonuses based on fill strength properties

Extensible Design: New equipment can be added without modifying existing code

Game Management
Real-time Statistics: Track equipment activations, matches won/lost, damage dealt/received

Cheat Command System: Developer commands for testing and debugging

Persistent Progression: Equipment carries between matches

Observer Pattern Implementation: Strict separation between model and view components

Architectural Design
Design Patterns Implemented
Observer Pattern: Clean Model-View-Controller separation with model classes never performing output

Null Object Pattern: Safe handling of empty equipment states

Open-Closed Principle: Extensible weapons and rings system using composition

Composition Over Inheritance: Flexible behavior combinations through component-based design

Package Structure
text
src/
├── Model/          # Game logic and state management
│   ├── weapons/    # Weapon system with behavioral components
│   │   └── behaviors/ # Targeting, condition, and damage modifier interfaces
│   ├── rings/      # Ring system with condition interfaces
│   └── GameText.java # Content and message formatting
└── UI/             # Presentation layer
    ├── TextView.java # Game state display and user interaction
    └── Main.java    # Application entry point and command processing
Equipment Details
Weapons
Lightning Wand: Activates on fast fill completion (<10 seconds), targets additional random opponent

Fire Staff: Activates with large fills (15+ cells), hits main target and adjacent opponents

Frost Bow: Activates with ascending number selection, hits all opponents

Stone Hammer: Activates with medium fills (10+ cells), hits all opponents at reduced damage

Diamond Sword: Activates with descending number selection, hits main target with side damage

Sparkle Dagger: Activates on moderate fill completion (<20 seconds), targets additional random opponent

Rings
The Big One: 50% damage bonus if fill strength ≥ 160

The Little One: 50% damage bonus if fill strength ≤ 90

Ring of Ten-acity: 50% damage bonus if fill strength divisible by 10

Ring of Meh: 10% damage bonus if fill strength divisible by 5

The Prime Directive: 100% damage bonus if fill strength is prime

The Two Ring: 1000% damage bonus if fill strength is a power of 2

Game Commands
Standard Commands
Enter a numeric sum to make a move

gear: Display currently equipped weapon and rings

stats: Show game statistics including matches, damage, and equipment activations

new: End current match (counts as loss) and start new match

quit: Exit the game

Cheat Commands (prefixed with cheat)
lowhealth: Set all enemies to low health (10 HP)

highhealth: Set all enemies to high health (200 HP)

weapon #: Equip specified weapon (1-6)

rings # # #: Equip up to three rings (1-6, 0 for empty slot)

max #: Set maximum grid value (0-# inclusive)

stats: Display statistics (alternative to direct command)

new: Start new match (alternative to direct command)

Technical Implementation
Key Design Decisions
Model-View Separation: Game model emits structured events; UI handles all display logic

Behavioral Composition: Weapons and rings built from reusable condition and modifier components

Statistical Tracking: Centralized statistic tracker using observer pattern for event monitoring

Damage Calculation: Proper rounding to nearest integer as per specification requirements

Compliance with Requirements
Implements all specified weapons and rings with correct behaviors

Follows Open-Closed Principle for equipment extensibility

Uses composition over inheritance for behavior customization

Maintains clean separation between model and UI layers

Provides comprehensive statistics tracking

Includes full cheat system for testing

Building and Running
Compilation
bash
javac -d out UI/*.java Model/*.java Model/weapons/*.java Model/weapons/behaviors/*.java Model/rings/*.java
Execution
bash
java -cp out UI.Main
Academic Context
Course: CMPT 213 - Software Engineering
Institution: Simon Fraser University
Instructor: Dr. Brian Fraser
Assignment: 4 - Design Patterns and Game Implementation
Grade Achieved: 100/100

This project demonstrates practical application of software engineering principles including design patterns, architectural separation, and extensible system design in a complete game implementation.
