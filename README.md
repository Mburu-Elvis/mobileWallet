# mobileWallet


## Table of Contents

- Introduction
- Features
- Installation
- Configuration
- Contributing
- Deployment
- License
- Contributors
- Contact

### Introduction

A wallet system designed to facilitate financial transactions efficiently and transparently. This platform allows users to perform various operations such as depositing money, making payments to different entities, transferring funds to other users, and withdrawing money to various channels. This project was developed as part of the Natujenge 2024 cohort challenge, embracing the entire Software Engineering journey from ideation to deployment. It is targeted at users seeking a mobile wallet that simplifies financial transactions with transparency and ease.

### Features

This section highligts the key features of the wallet system, providing a brief description of each.

#### 1. Deposit Money

- Allows users to deposit money into their wallet from various channel.

#### 2. Payment Processing

- Enables users to make payments to different entities, including merchants, service providers and other users.
- Provides transaction history for tracking all payment made.

#### 3. Money Transfer

- Facilitates the transfer of funds between users within the wallet system.

#### 4. Withdrawal Options

- Allows users to withdraw money from their wallet to various channels.

#### 5. Transaction Statements Generation

- Allows users to download their transactional statements within a custom time range

#### 6. Security Features

- Authenticates users to ensure account safety

#### 7. Real-Time Notifications

- Sends real time notifications for all transactions via SMS, ensuring users are immediately informed of any activity on their account.

### Installation

- **Prerequisites:** Java 17, Maven, AngularJs, NodeJS
- **Installation Steps:** 
	```bash
	git clone https://github.com/Mburu-Elvis/mobileWallet.git
	
	cd mobileWallet
	
	cd frontend

	npm install

	npm start
	```

### Configuration

- Open the export_variable.sh script in `configs` and set the required environment variables.  
> For the API_KEY you can register in [TIARACONNECT](https://api.tiaraconnect.io) and create a project and copy the API key generated.
> Save the changes
- run the executable file
```bash
# example
./export_variable.sh
```
- if you have a different java version from 17 you can change it temporaly
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```
- run the backend service
```bash
mvn springboot:run
```
### Contributing

We welcome contributions! To contribute, please follow the following steps
- Fork the repository
- Create a new branch ('git checkout -b <new-branch-name>
- Make your changes and commit them
- Push to the new branch
- Create a Pull Request

### Deployment

You can deploy the project to any place of your liking but [Docker](hub.docker.com) is a great place to think about

### License

This project is licensed under the MIT License - see the [LICENSE](license) file for details.

### Contributors

- Martin Kagua
- Feiz Mbai
- Elvis Mburu

### Contact

- Maintained by:  
> - [Elvis Mburu](https://linktr.ee/mburuelvis)
> - [Martin Kagua]()
> - [Feiz Mbai]()  
For any questions or suggestions, feel free to reach out at [mburuelvis310@gmail.com](mailto:mburuelvis310@gmail.com)
