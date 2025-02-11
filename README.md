# 線上投票系統 - 專案說明

## 功能概述

### 權限管理
- **未註冊使用者**：
  - 查看所有投票項目及目前累積的投票數。
  
- **一般使用者**：
  - 查看所有投票項目及目前累積的投票數。
  - 查看自身的投票紀錄。
  - 對投票項目進行多選投票操作，確保使用者有靈活的選擇範圍。

- **管理者**：
  - 查看所有投票項目及目前累積的投票數。
  - 查看自身的投票紀錄。
  - 對投票項目進行多選投票操作。
  - 進入後台進行管理操作，包括新增、更新和刪除投票項目，確保系統內容可以動態調整。

---

## 技術架構與實現

### 系統架構
- **前端**：基於 **Vue.js** 框架進行開發。
  - 動態渲染投票資料，避免頁面重載。
  - 實現了用戶端表單驗證，確保基本輸入安全性。
  - 因為對 Vue 尚未完整掌握，如果頁面未能重新渲染，請手動 F5 進行數據更新。
  
- **後端**：採用 **Java Spring Boot** 框架，並以 **Maven** 建立專案。
  - 使用多層式架構設計，包括展示層、業務層、資料層以及共用層：
    - **展示層**：負責處理與前端的互動，並以 RESTful API 風格設計。
    - **業務層**：包含核心業務邏輯，如投票計數、項目新增等，並實現 **Transaction** 管理，確保數據一致性，避免因併發操作導致數據錯亂。
    - **資料層**：使用 JPA 與 MySQL 資料庫進行交互，通過 **Stored Procedure** 提高數據操作效率。
    - **共用層**：密碼工具、全局設定等。
  - 實現了 **@Valid** 驗證機制，在後端進行輸入數據的深度驗證，確保系統穩定性和安全性。
  - 要連線資料庫，請先協助更改 Java 中 application.properties 的連線設定。

- **資料庫**：使用 **MySQL** 關聯式資料庫。
  - 運用 **Stored Procedure** 處理核心數據操作，確保查詢性能最佳化。
  - DDL 和 DML 可以參考專案中的 /DB 資料夾。
    
---

### 安全設計
- **SQL Injection 防禦**：通過 ORM 框架 JPA 與 **Stored Procedure** 的結合，有效防止 SQL 注入攻擊，確保數據庫安全。
- **XSS 攻擊防禦**：前端不直接通過網址參數獲取敏感數據，並對所有表單輸入在前後端進行雙重驗證，防止腳本注入攻擊。
- **資料驗證**：
  - 前端提供基本的表單驗證，確保用戶輸入格式正確。
  - 後端使用 Spring 的 **@Valid** 註解進行深入驗證，雙重保護用戶數據。

---
