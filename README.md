# Gentleware Test Task
Gentleware test task for JAVA Programmer position.

# 1. Introduction.

Gentleware Test Task Database project is developed as a task for completion of JAVA Programmer position requirements. 
The project represents JAVA developer's skills in object-oriented approach, MySQL database 
creation and data processing using internal and external JAVA libraries. The project's database is developed 
to demonstrate 1:1, 1:M data relationships, 1-st, 2-nd and 3-rd normal forms of a databases architecture.

The 'gentleware_test_task' database describes a functioning of an abstract financial company which supplies 
payment service for registered clients. The company provides payments between clients accounts and writes payment
process history into the database. The console application allows to perform the database administration, create
payments and view some statistic data.

The task source codes are located at a git-hub repository URL https://github.com/Voww/gentleware_test_task . 
The task description file is .\gentleware_test_task\resources\gentleware_test_task.txt. And answers for the task 
questions there are situated at the same location: .gentleware_test_task\resources\gentleware_test_task_answers.txt.

#2. System requirements.

The Gentleware Test Task Database project was developed under certain system conditions and software which finally 
define minimal system requirements to your server. To run the project you will have installed 
software below listed:

* PHP 5.5.12 or higher;
* MySQL Community Server (GPL) 5.7.7-rc-log or higher;
* MySQL database connection JAVA driver mysql-connector-java-5.1.6.jar
* Web-server Apache-2.4.9 or higher.

The next software might be installed optionally:

* JDK 1.7.0_45 or higher;
* MySQL workbench 6.3 or compatible;
* IDE environment IntelliJ IDEA 14.1.4 or compatible.

#3. Installation.

The The Gentleware Test Task Database project is a JAVA console application. To pick up the project on your local 
machine, please follow these installation instructions.

* Download on your local machine the Gentleware Test Task Database source code from a git-hub repository link 
https://github.com/Voww/gentleware_test_task.
* Open path .\gentleware_test_task\resources\ and find a file named 'gentleware_test_task_db_install.sql'. Execute 
the sql script from MySQL console or using MySQL workbench or IDE.
* Create at the MySQL database a new user 'root' with a password 'toor' having administration rights on the 
'gentleware_test_task' database. You can change host name, user name and password parameters by editing 
a property file .\src\ua\com\gentleware\zaietsv\connector\GentlewareTestTaskConnectorBundle.properties.
* Verify the database installation typing in a MySQL console "USE gentleware_test_task;" (Enter) then
"SHOW TABLES FROM gentleware_test_task;" (Enter). If the database installed correctly, your will obtain the such list 
of tables: account, client, client_account, payment.
* Deploy the .\gentleware_test_task\ directory content to your local working folder.
* Run a program starting from console or IDE a class .\gentleware_test_task\src\Main.java.
If you see a text "Enter any command below listed or type 'help' to view this message again or type 'exit' to resume:"
it means the resources are deployed correctly and the Gentleware Test Task project is ready to use.

#4. Common project description.

The Gentleware Test Task project implements a brief model of an electronic financial system. It allows to administrate 
a list of clients, to handle their accounts and to perform accounting entry between the accounts. All of the essential
data are stored in a 'gentleware_test_task' database. There are five classes of value objects at IMS Media Stock model:

* Client;
* Account; 
* Payment;
* ClientToAccountRelation;
* PaymentRelation.

The value objects are divided into instances which implements Instance interface and relations which implements 
Relation interface. All of them implements wrapper interface GentlewareObject.

The instances are Client, Account, Payment. The Client instance contains an information about certain client including 
fields: id, email, first_name, last_name. It's content corresponds to a 'client' table. The Account instance contains 
an information about certain account including fields: id, code, alias, amount, description. It's content corresponds
to an 'account' table. The Payment instance contains an information about payment including fields: id date amount 
from_account_id to_account_id comment. It's content corresponds to a 'payment' table.

The relations are ClientToAccountRelation, PaymentRelation. The ClientToAccountRelation instance contains an information
about certain client's accounts. It contains a 'base' field of Client class and 'related' field of List<Account> class. 
It means that a Client can have several accounts registered. The PaymentRelation instance contains an information 
about a payment transaction and accounts to be processed. It contains a 'base' field of Account class and 'related' 
field of List<Account> class. It means that a payment can be potentially performed with a group of accounts, but now
it is implemented only payment between a one pair of accounts. The payment relation also contains fields id, date,
amount, comment. The PaymentRelation class implements interface PaymentService method pay() to perform accounting 
entry.

This way we have value objects combined into relational model. According to the philosophy above described, the database
have four separate tables corresponding to the value objects classification:

* client;
* account; 
* client_account;
* payment.

To perform database operations each value object corresponds to it's table through own Data Access Object class (DAO):

* ClientInstanceDAO;
* AccountInstanceDAO; 
* PaymentInstanceDAO;
* ClientToAccountRelationDAO;
* PaymentRelationDAO. 

Each DAO implements a common GentlewareDAO interface and one of it's subinterfaces: InstanceDAO and RelationDAO. The 
DAOs allows to create, read, update and delete database data storing the database integrity. Also 
ClientToAccountRelationDAO implements ClientService interface and some additional statistic methods. Therefore it can 
perform several requests using functions: getClientBalance, getClientWithMaxBalance, getClientWithMinBalance,
getClientWithMaxBalanceLastName, getClientWithMinBalanceLastName. 

The database connection is provided by GentleWareTestTaskConnector class implementing Connector interface. The 
connection acess data are stored at GentlewareTestTaskConnectorBundle.properties file.

The Gentleware Test Task Database project is a console application, so it starts from method main() and works in a text
mode. The project' Main class implements main() method providing basic functionality: database connection and
handling, input text requests reading and output data writing. The output depends on a console commands. Each command 
is handled by ManagerFactoy instance which produces different Manager instances depending on the command
content. Any manager implements Manager interface method manage(). The Main class calls to manage() method of the
current Manager instance which performs necessary operations and outputs a response. So, the response can vary depending
on current Manager implementation. Among them are:

* DefaultManager;
* ClientManager;
* AccountManager;
* PaymentManager.

Each manager implements a business-logic for properly data processing and database handling. Finally it outputs a string
representation of value objects instances and their relations.

#5. Working with the application.

Once Main class have been started from JAVA console, you can perform necessary operations by typing in the console
 window one of the commands below listed:

* Client section: createClient, readClient, updateClient, deleteClient, readClientsList, getClientBalance, 
getClientWithMinBalance, getClientWithMaxBalance, getClientWithMinBalanceLastName, getClientWithMaxBalanceLastName;
* Account section: createAccount, readAccount, updateAccount, updateAccountAmount, deleteAccount, readAccountsList; 
readClientAccounts
* Payment section: createPayment, readPayment, readPaymentsList.

After entering a command, please, follow application instructions and enter necessary values if required. At success
you will obtain an output information which depends on the requested command and entered data. At fault an error 
message appears.

The basic application commands are: createClient, createAccount, createPayment. To provide a payment process you need 
to create at least one Client by typing 'createClient' command and entering a client's data. Also it is necessary to 
create at least two accounts for different clients or several accounts for the same client. Once accounts have been 
created, you can create a payment transaction by entering a command 'createPayment'. Then enter an ID numbers of a 
source account and destination account, payment amount and a comment. The program will perform accounting entry and
store the transaction result into the database. As a result you can view the transaction parameters. The program does
not limits pament amounts and allows negetive balances.

To view a statistic data type one of the commands: getClientBalance, getClientWithMinBalance, getClientWithMaxBalance,
getClientWithMinBalanceLastName, getClientWithMaxBalanceLastName.

To view a list of commands type 'help'. Type 'exit' to resume application execution.

#6. Summary

The Gentleware Test Task project describes a brief model of an electronic financial system. It allows to administrate 
a list of clients, to handle their accounts and to perform accounting entry between the accounts. The project implements
a process of data conversion from a database into relational model, the data porcessing and the result storing back into
a MySQL database. And maybe the project helps to it's author to obtain a position of a JAVA programmer at the Gentleware 
company.

 

