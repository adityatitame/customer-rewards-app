# 🏆 Customer Rewards App

A Spring Boot REST API that calculates **customer reward points** based on their transaction history. Customers earn reward points on purchases over $50, with higher rewards for spending above $100. The API also supports calculating reward summaries dynamically for any given time frame.

---

## 🚀 Features

- ✅ Add new customers and transactions
- ✅ Automatically calculate and store reward points
- ✅ View reward points earned per transaction
- ✅ Get reward points summary by month and total within a given date range
- ✅ Dynamic API filters for custom time periods
- ✅ Built using Spring Boot, MySQL, JPA, and ModelMapper

---

## 🧮 Reward Calculation Logic

- 🟢 1 point for every $1 spent over **$50** up to **$100**
- 🟢 2 points for every $1 spent over **$100**

> 💡 Example:  
> A transaction of **$120** earns:  
> - (120 - 100) x 2 = 40  
> - (100 - 50) x 1 = 50  
> → **Total = 90 points**

---

## 🛠 Tech Stack

| Layer       | Technology                 |
|-------------|----------------------------|
| Language    | Java 17                    |
| Framework   | Spring Boot                |
| Database    | MySQL                      |
| ORM         | Spring Data JPA            |
| Mapping     | ModelMapper                |
| Testing     | JUnit 5 + Mockito          |
| Build Tool  | Maven                      |
| Client      | Postman                    |

---

## 📁 Project Structure

```
src/
 └── main/
     ├── java/com/infy/rewardsapp
     │   ├── controller
     │   ├── dto
     │   ├── entity
     │   ├── repository
     │   ├── service
     │   ├── exception
     │   └── utility
     └── resources/
         ├── application.properties
         ├── log4j2.properties
         ├── RewarsAppTableScript.sql
         ├── ValidationMessages.properties
README.md
pom.xml

```

---

## 📡 API Endpoints

### ➕ Add a Customer
```
POST /rewards/register
```
```json
{
  "name": "Amit Patil",
  "email": "amit@example.com",
  "contact": "9876543210"
}
```

---

### ➕ Add a Transaction
```
POST /rewards/newTransaction
```
```json
{
  "amount": 120.0,
  "date": "2025-06-10",
  "customerDTO": {
    "customerId": 1
  }
}
```

---

### 📊 Get Reward Summary for Date Range
```
GET /rewards/rewardSummary/1/2025-04-01/2025-06-30
```

**Sample Response:**
```json
{
  "customerDTO": {
    "customerId": 1,
    "name": "Amit Patil",
    "email": "amit@example.com",
    "contact": "9876543210",
    "totalRewardPoints": 180
  },
  "from": "2025-04-01",
  "to": "2025-06-30",
  "monthlyRewards": [
    {
      "month": "APRIL 2025",
      "rewardPoints": 90
    },
    {
      "month": "MAY 2025",
      "rewardPoints": 60
    }
  ],
  "totalPointsForRange": 150
}
```

---

## 🧪 Testing

- Unit tests using **JUnit 5** and **Mockito**
- Reward calculation logic is covered
- Run with:
```bash
mvn test
```

---

## 🏁 Getting Started

1. Clone the repo:
```bash
git clone https://github.com/adityatitame/customer-rewards-app.git
```

2. Set up your MySQL DB and update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rewards_app_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

3. Run the application:
```bash
mvn spring-boot:run
```

---

## 📌 Future Enhancements

- Add Swagger/OpenAPI docs
- Pagination and sorting of transactions
- Role-based authentication
- Docker containerization

---

## 👨‍💻 Author

**Aditya Titame**  
Full Stack SpringBoot Developer  
[LinkedIn](https://www.linkedin.com/in/adityatitame1110)  
Email: adityatitame1110@gmail.com

---

## 📝 License

This project is free to use for personal and commercial purposes.

