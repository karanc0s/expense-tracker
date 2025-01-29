## **Expense Tracker**

### _Must Have_
* User is able to Login and SignUp
* User is able to add/ remove the expense manually
* User is able to see his expenses, Categorised expenses
* User is able to weekly, monthly, yearly report and statistics about the spending
* statistics can be downloaded in file (csv, pdf)

### Good to have
* fault tolerance, scalable system
* system latency < 100ms
* config driven system ,,, less code changes in future

### Future
* User is able to track their financial behaviour
* AI can suggest 
* App should be able ti add expense on its own by reading and parsing sms
* Notification indicating various things like ris, overspending etc (WhatsApp , SMS)


### Auth HLD
```mermaid
graph LR;
    CLIENT-->API-GATEWAY
    
    subgraph BACKEND
        DB-->AUTH-SERVICE
        AUTH-SERVICE-->DB
        AUTH-SERVICE-->QUEUE
        QUEUE-->USER-SERVICE
    end
    AUTH-SERVICE-->API-GATEWAY
    API-GATEWAY-->AUTH-SERVICE
```
 
