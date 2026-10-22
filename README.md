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

To avoid merge conflicts, each team member is responsible for creating and maintaining their assigned files.

### 1. Nate (Group Leader)
* **Role:** System Architecture, UI Integration, Explanation Module, & Project Lead.
* **Assigned Files:**
  * `Main.java`
  * `MainViewController.java`
  * `ExplanationView.java`

### 2. Ahmed
* **Role:** Scenario C Lead (LaptopWise) & Knowledge Graph Engine.
* **Assigned Files:**
  * `LaptopWise.java`
  * `LaptopCatalog.java`
  * `KnowledgeGraph.java`
  * `LaptopWiseTest.java`

### 3. Devin
* **Role:** Scenario D Lead (SecureSteps) & Core Logic Engines.
* **Assigned Files:**
  * `SecureSteps.java`
  * `TruthTableGenerator.java`
  * `ExpressionTree.java`
  * `DecisionTree.java`
  * `LogicEngineTest.java`

---

## 🚀 Getting Started & Setup

### Prerequisites

* Java Development Kit (JDK 17 or higher) installed.
* Apache Maven installed for project building and dependency management.

### Installation & Execution

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/logicforge.git](https://github.com/your-username/logicforge.git)
   cd logicforge
