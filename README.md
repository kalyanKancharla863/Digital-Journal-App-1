# Digital-Journal-App
# 📓 Digital Journal App

A secure, full-stack Digital Journal application built with **Spring Boot**, **React (Vite)**, **PostgreSQL**, and **Spring Security**. This application provides a seamless, user-friendly environment for users to safely create, manage, and organize their personal thoughts and daily journal entries.

---

## 🚀 Features

*   **Secure Authentication:** User registration and login powered by Spring Security.
*   **Cookie-Based Sessions:** Robust session management utilizing secure HTTP cookies.
*   **Full CRUD Capabilities:** Create, read, update, and delete journal entries effortlessly.
*   **Smart Organization:** Categorize your entries and assign importance/difficulty levels.
*   **Quick Filters:** Easily mark and find your **Favorite** or **Important** thoughts.
*   **Data Isolation:** Multi-tenant logic ensures authenticated users can *only* access their own data.
*   **Responsive UI:** A modern, clean user interface built with React and Vite.

---

## 🛠️ Tech Stack

| Layer | Technologies Used |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot, Spring Security, Spring Data JPA, Hibernate |
| **Frontend** | React, Vite, Axios, React Router, Tailwind CSS (or your CSS framework) |
| **Database** | PostgreSQL |

---

## 🔒 Security Architecture

> 💡 **Privacy by Design:** The core of this application relies on Spring Security configured with **secure cookie-based authentication** rather than standard localStorage tokens. This mitigates XSS risks and ensures that your API endpoints are heavily guarded. 

---

## 📂 Project Structure

### 💻 Backend
*   **Authentication & Authorization:** Secure cookie generation, validation filters, and user session handling.
*   **Journal Management APIs:** RESTful endpoints for handling entry lifecycle.
*   **Metadata Management:** Category and difficulty/importance level configurations.
*   **Persistence Layer:** PostgreSQL integration via Spring Data JPA.

### 🎨 Frontend
*   **Auth Pages:** Clean, intuitive Login and Registration interfaces.
*   **Dashboard:** A central hub showing recent entries, favorites, and quick stats.
*   **Forms Wizard:** Dynamic journal entry creation with category and level selectors.
*   **Route Guards:** Protected React router elements that automatically block unauthenticated traffic.

---

## 🔮 Future Enhancements

*   [ ] **Advanced Search:** Global search with deep filtering options (by date, tag, or category).
*   [ ] **Rich Text Editor:** Support for markdown, bolding, lists, and code blocks within entries.
*   [ ] **Media Attachments:** Upload files and images securely.
*   [ ] **Dark Mode:** A sleek night theme for comfortable late-night journaling.
*   [ ] **Journal Analytics:** Visual insights and graphs tracking your journaling streaks and moods.
*   [ ] **Data Portability:** Seamless export of your journal entries to PDF or JSON.

---

## 🎯 Purpose

The **Digital Journal App** is designed to bridge the gap between traditional journaling and modern security. It helps users maintain a personal digital diary, track crucial life moments, organize thoughts efficiently, and access their data safely from anywhere.

---

🌟 **If you find this project useful, consider giving it a star!**
