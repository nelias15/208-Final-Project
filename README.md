# 208-Final-Project
# LogicForge — Rule-Based Decision Support System

An interactive, rule-based application built in **Java** that models propositional logic, truth tables, expression trees, sequential decision trees, and knowledge graphs to deliver transparent, explainable recommendations.

---

## 🎯 What We Are Doing

LogicForge is a decision-support prototype built to make logic transparent and verifiable. Instead of acting as a "black-box" system, LogicForge explicitly demonstrates *why* a recommendation was made by showing exact rule evaluations, generated truth tables, expression trees, and relational knowledge graphs. 

Our team is implementing two distinct real-world scenarios:
1. **Scenario C — LaptopWise (Budget-Aware Laptop Selector):** Helps users filter and rank laptops based on budget ($B$), minimum RAM ($M$), weight limit ($L$), and optional warranty requirements ($W$) using the rule $Eligible = B \land M \land L (\land W)$.
2. **Scenario D — SecureSteps (Secure Login Logic Simulator):** A safe educational simulator modeling multi-factor access control using fictional inputs ($P$ = password, $V$ = 2FA verification, $L$ = account unlocked) via the rule $AccessGranted = P \land V \land L$.

---

## ✨ Key Features

* **Interactive Interface:** A clean Java graphical interface (JavaFX/Swing) where users input preferences, toggle constraints, and view instant logic evaluations.
* **Core Logic Engine:** Evaluates propositions (AND, OR, NOT) and compound expressions deterministically.
* **Truth Table Generator:** Automatically generates truth values showing all input combinations and outcomes.
* **Expression Tree & Traversals:** Visualizes logical expression trees and computes Preorder, Inorder, and Postorder traversals.
* **Decision Trees:** Step-by-step sequential decision paths with clear outcome explanations and safe recovery actions.
* **Knowledge Graphs & Adjacency Lists:** Maps entity relationships (e.g., Laptops $\rightarrow$ Specs, Accounts $\rightarrow$ Verification Steps) and outputs adjacency lists.
* **Explainable Recommendations:** Highlights exact pass/fail conditions and identifies missing or assumed data.

---

## 🛠️ Technology Stack

* **Language:** Java (JDK 17 or higher)
* **GUI Framework:** JavaFX / Swing
* **Build Tool:** Maven or Gradle
* **Version Control:** Git & GitHub

---

## 👥 Team Responsibilities & File Assignments

To avoid merge conflicts, each team member is responsible for creating and maintaining specific Java files and packages.

### 1. Nate (Group Leader)
* **Role:** System Architecture, UI Integration, Explanation Module, & Project Lead.
* **Key Tasks:** 
  * Design the overall application flow and integrate Ahmed’s and Devin’s modules into the primary interface.
  * Implement the explanation engine to display step-by-step pass/fail logic to the user.
  * Lead technical report assembly, architecture diagramming, and presentation coordination.
* **Assigned Files:**
  * `src/main/java/com/logicforge/Main.java` — Main application launcher.
  * `src/main/java/com/logicforge/ui/MainViewController.java` — Primary Java GUI layout and tab controller.
  * `src/main/java/com/logicforge/ui/ExplanationView.java` — GUI component for explainable decision outputs.

---

### 2. Ahmed
* **Role:** Scenario C Lead (LaptopWise) & Knowledge Graph Engine.
* **Key Tasks:** 
  * Build the laptop catalog data structure and implement manual constraint inputs.
  * Implement filtering and multi-criteria ranking based on user priorities.
  * Construct the LaptopWise decision tree, knowledge graph, and adjacency list representations.
* **Assigned Files:**
  * `src/main/java/com/logicforge/scenarios/LaptopWise.java` — Scenario C logic engine and constraint filter.
  * `src/main/java/com/logicforge/scenarios/LaptopCatalog.java` — Sample laptop data store and custom specification input handler.
  * `src/main/java/com/logicforge/graph/KnowledgeGraph.java` — Knowledge graph data structure and adjacency list/matrix generator.
  * `src/test/java/com/logicforge/scenarios/LaptopWiseTest.java` — Unit tests for Scenario C filtering and ranking.

---

### 3. Devin
* **Role:** Scenario D Lead (SecureSteps) & Core Logic Engines.
* **Key Tasks:** 
  * Implement SecureSteps login logic ($AccessGranted = P \land V \land L$) and safe recovery workflow decision tree.
  * Build the general-purpose Truth Table generator and Expression Tree traversal engine (Preorder, Inorder, Postorder).
  * Write logic validation and boundary condition tests.
* **Assigned Files:**
  * `src/main/java/com/logicforge/scenarios/SecureSteps.java` — Scenario D login simulation logic and recovery outcomes.
  * `src/main/java/com/logicforge/logic/TruthTableGenerator.java` — Proposition evaluator and dynamic truth table builder.
  * `src/main/java/com/logicforge/trees/ExpressionTree.java` — Binary expression tree builder and tree traversal algorithms.
  * `src/main/java/com/logicforge/trees/DecisionTree.java` — Sequential decision path evaluator.
  * `src/test/java/com/logicforge/logic/LogicEngineTest.java` — Unit tests for truth tables, travers
