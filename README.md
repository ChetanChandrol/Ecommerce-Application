# 🛒 E-Commerce Application

A **Microservices-Based E-Commerce Backend Application** built using Java, Spring Boot, Spring Security, Hibernate, and MySQL. The application follows a distributed microservices architecture, where different services handle user management, product management, order processing, and payment operations independently.

The project focuses on building scalable REST APIs, implementing secure authentication and authorization, and managing communication between independent services.

## 🚀 Features

### 1. User Service
- User registration and login.
- JWT-based authentication.
- User logout and token validation.
- Role-based authorization.
- OAuth 2.0 Authorization Server integration.
- Secure user management.

### 2. Product Service
- Create, retrieve, update, and delete products.
- Product category management.
- Product search functionality.
- Category-based product filtering.
- Sorting and pagination.
- Specification-based search.

### 3. Order Service
- Add products to cart.
- Retrieve cart details.
- Update product quantities.
- Remove products from cart.
- Place orders.
- Retrieve user order history.

### 4. Payment Service
- Razorpay payment integration.
- Payment link generation.
- Payment webhook endpoint.

### 5. Configuration Server
- Centralized application configuration.
- Spring Cloud Config Server.
- Database-backed configuration management.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring MVC | REST API Development |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database Operations |
| Hibernate | ORM |
| MySQL | Relational Database |
| JWT | Token-Based Authentication |
| OAuth 2.0 | Authorization |
| Spring Cloud Config | Centralized Configuration |
| Maven | Dependency Management |
| Razorpay | Payment Integration |
| Lombok | Boilerplate Code Reduction |

## 🏗️ Microservices Architecture

The application is divided into five independent services:

```text
                 E-Commerce Application
                          |
          +---------------+---------------+
          |               |               |
      User Service   Product Service   Order Service
          |               |               |
       MySQL            MySQL            MySQL
          |
          +-------------------------------+
                          |
                   Payment Service
                          |
                       Razorpay

              Configuration Server
             (Centralized Configuration)
```

### Project Structure

```text
Ecommerce-Application/
│
├── ConfigurationServer/
│
├── EcommerceUserService/
│
├── ECommerceProductService/
│   └── ECommerceProductService/
│
├── EcommerceOrderService/
│
└── EcommercePaymentService/
```

## ⚙️ Getting Started

### Prerequisites

Make sure you have installed:

- Java Development Kit (JDK) 21
- Maven
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/ChetanChandrol/Ecommerce-Application.git
```

### 2. Navigate to the Project

```bash
cd Ecommerce-Application
```

### 3. Configure MySQL

- Create the required MySQL databases.
- Configure database URLs, usernames, and passwords in the respective `application.properties` files.
- Configure the required Razorpay credentials for payment integration.
- Configure the Configuration Server and its database settings.

Do not commit database passwords, JWT secrets, or payment credentials to GitHub.

### 4. Build the Services

Navigate to each service directory and execute:

```bash
mvn clean install
```

### 5. Run the Application

Start the services individually using:

```bash
mvn spring-boot:run
```

Start the Configuration Server first, followed by the other services after configuring their required dependencies and database connections.

## 🔗 API Overview

| Service | Endpoint | Method | Description |
|---|---|---|---|
| User | `/auth/signup` | POST | Register user |
| User | `/auth/login` | POST | User login |
| User | `/auth/logout` | PUT | User logout |
| User | `/auth/validate` | GET | Validate token |
| Product | `/product` | GET | Get all products |
| Product | `/product/{id}` | GET | Get product by ID |
| Product | `/product` | POST | Create product |
| Product | `/category` | GET | Get all categories |
| Product | `/search` | GET | Search products |
| Order | `/cart/add` | POST | Add product to cart |
| Order | `/cart` | GET | Get cart |
| Order | `/order/place` | POST | Place order |
| Payment | `/payment/{orderId}` | POST | Generate payment link |

*Note: Configure the appropriate service base URLs and authentication headers when making API requests.*

## 🔐 Security

The application implements security mechanisms including:

- Spring Security.
- JWT-based authentication.
- Role-based access control.
- OAuth 2.0 Authorization Server.
- OAuth 2.0 Resource Server support.

## 🎯 Learning Outcomes

Through this project, I explored and implemented:

- Microservices architecture using Spring Boot.
- RESTful API development.
- Authentication and authorization using Spring Security.
- JWT and OAuth 2.0 integration.
- Database integration using Spring Data JPA and Hibernate.
- Product searching, filtering, sorting, and pagination.
- Inter-service communication.
- Payment gateway integration.
- Centralized configuration using Spring Cloud Config.

## 👨‍💻 Author

**Chetan Chandrol**

- GitHub: [@ChetanChandrol](https://github.com/ChetanChandrol)
- Project: [E-Commerce Application](https://github.com/ChetanChandrol/Ecommerce-Application)

---

⭐ If you find this project interesting, consider giving it a star!

