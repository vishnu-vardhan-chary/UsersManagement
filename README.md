# 🧑‍💻 Users Data Management App (Spring Boot + React.js)

This project is a full-stack application built using **Spring Boot** (backend) and **React.js** (frontend). It fetches users' data from an external API, stores it in a database, and provides a user-friendly interface to view, search, sort, and view details in a popup modal.

---

## 🔧 Tech Stack

- **Backend**: Spring Boot, REST API, JPA, MySQL
- **Frontend**: React.js (with Bootstrap via CDN)
- **External API**: [https://dummyjson.com/users](https://dummyjson.com/users)

---

## 📌 Features

### ✅ Backend (Spring Boot)

1. **Fetch & Store External API Data**  
   - Downloads users data from `https://dummyjson.com/users`
   - Stores data into the local database

2. **Get All Users**  
   - `GET /api/users`  
   - Fetches all users from the database

3. **Get User by ID**  
   - `GET /api/users/{id}`  
   - Retrieves specific user details by ID

4. **Search Users by Name or Email**  
   - `GET /api/users/search?query=searchTerm`  
   - Returns filtered users based on name or email

---

### ✅ Frontend (React.js)

1. **Users Table Display**
   - Fetches all users on page load
   - Displays them in a responsive, styled Bootstrap table

2. **Sort Functionality (Client-Side Only)**
   - Sort by: **ID**, **Name**, and **Email**

3. **User Details Modal**
   - On clicking a user's ID, opens a modal with full user details from API

4. **Search Box**
   - Filters users based on input matching name or email
   - Makes API call to `/api/users/search`

---

## ▶️ How to Run

### 🔹 Backend (Spring Boot)

1. Clone the repo and navigate to the backend folder:
   ```bash
   git clone https://github.com/vishnu-vardhan-chary/UsersManagement.git
   cd user_management-backend
