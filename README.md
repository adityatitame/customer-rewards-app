🏆 Customer Rewards App
A Spring Boot REST API that calculates customer reward points based on transaction history. Customers earn points on purchases above $50, with higher rewards beyond $100. The API supports dynamic reward summaries for any date range.

🚀 Features
✅ Add new customers and transactions
✅ Automatically calculate and persist reward points
✅ View reward points per transaction
✅ Generate reward summary by month and total within a date range
✅ Filter data dynamically via request parameters
✅ Clean layered architecture with full Javadoc and unit tests
🧮 Reward Calculation Logic
🟢 1 point for every $1 spent over $50 up to $100
🟢 2 points for every $1 spent over $100
💡 Example:
A transaction of $120 earns:

(120 - 100) × 2 = 40
(100 - 50) × 1 = 50
→ Total = 90 points
🛠 Tech Stack
Layer	Technology
Language	Java 17
Framework	Spring Boot
Database	MySQL / H2
ORM	Spring Data JPA
Mapping	ModelMapper
Logging	Log4j2
Testing	JUnit 5 + Mockito
Build Tool	Maven
Client Tool	Postman
📁 Project Structure
src/
 └── main/
     ├── java/com/infy/rewardsapp
     │   ├── controller          # REST controllers
     │   ├── service             # Interfaces and implementations
     │   ├── model               # DTOs and entities
     │   ├── repository          # Spring Data JPA interfaces
     │   ├── exception           # Custom exceptions and advice
     │   └── RewardsappApplication.java
     └── resources/
         ├── application.properties
         ├── log4j2.properties
         ├── RewardsAppTableScript.sql
         ├── ValidationMessages.properties
README.md
pom.xml
📡 API Endpoints
➕ Add a Customer
POST /rewards/addCustomer
Request:

{
  "name": "Amit Patil",
  "email": "amit@example.com",
  "contact": "9876543210"
}
➕ Add a Transaction
POST /rewards/addTransaction
Request:

{
  "amount": 120.0,
  "customerDTO": {
    "customerId": 1
  }
}
📊 Get Reward Summary for a Date Range
GET /rewards/rewardSummary?customerId=1&startDate=2025-04-01&endDate=2025-06-30
Response:

{
  "customerId": 1,
  "name": "Amit Patil",
  "email": "amit@example.com",
  "contact": "9876543210",
  "totalRewardPoints": 180,
  "startDate": "2025-04-01",
  "endDate": "2025-06-30",
  "monthlyRewards": [
    {
      "month": "APRIL",
      "year": 2025,
      "rewardPoints": 90
    },
    {
      "month": "MAY",
      "year": 2025,
      "rewardPoints": 60
    }
  ],
  "totalPointsForRange": 150
}
🧪 Testing
Unit tests using JUnit 5 and Mockito
100% test coverage for service and controller layers
Test cases for:
Valid and invalid customer/transaction inputs
Reward calculation logic
API responses and exceptions
To run tests:

mvn test
🏁 Getting Started
Clone the repository
git clone https://github.com/adityatitame/customer-rewards-app.git
cd customer-rewards-app
Configure your database in src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/rewards_app_db
spring.datasource.username=root
spring.datasource.password=yourpassword
Run the application
mvn spring-boot:run
👨‍💻 Author
Aditya Titame
Java | Spring Boot | Full Stack Developer
📧 adityatitame1110@gmail.com
🔗 LinkedIn