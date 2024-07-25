+ What is pending in terms of learning:

    + Achievements:

        + Web app dev work:
            + HTML
            + CSS 
            + JS 

            + Frameworks: Angular, React, etc... 

        + Appreciate what an API is:
            + POSTMAN 
            + GET 
            + POST
            + PUT 
            + DELETE 
            + etc.... 

            + You have able to connect to external APIs that you did not built


        + Appreciate creation of your own APIs:
            + Use any language 
            + nodejs/java - Springboot... 
    
        + Introduction to databases:
            + Overview of databases - MySQL 
            + Connect to database using your code...

            + Services e.g. DBMS run on a port; 3306 MySQL, Oracle 1521 port, etc... 


        + Wrapping all technologies to create an app:
            + front end - web app 
            + backend - apis, dbs at the back...  





    + Pending lessons:
        + Learn to deploy - servers or cloud... 
            + Docker - containerization.... 
            + Challenge: we need to accomplish
        + Learn to use VCS - git/github, git/bitbucket, etc... 
            + Challenge: we need to accomplish this...
        + USSD apps development:
            + dial *710*12345# <-- menu and back and forth... 
        + WhatsApp integration
        + M-PESA integration.... 


- Challenge for the next few weeks:

- Dummy Banking System/Wallet System:

- You will create a wallet system that can be used to carry out financial transactions and the following journeys need to be supported:


    + Account/Wallet Management: == Accounts Table

        + Create customer wallet - capture KYC details and create a wallet with zero balance.   == CRUD
        	+ KYC details
        	   - account/customer name = non-unique
        	   - phoneNumber - unique
        	   - ID - unique
        	   - email - unique
        	   - KRA Pin - unique
        	   - Account balance
        	   - password/pin
            + Wallet needs to be identified by a mobile number
            	- Identifier/primary key = mobile number
        + Update customer wallet - you can update KYC information except balance.
        	- 
        + Delete customer wallet - you must move all the money before deleting the wallet. 
        + View customer wallet - view wallet details and view the statement last transactions from the statement.
        	- READ - read everything
        	
    + Transactions Management:

        + Account Adjust:
            + Enter the amount and the reason for adjustment 
            + Submit:
                + The balance should be updated and sms sent to the account owner with the new balance 

            + Lessons:
                + Update on the wallet record 
                + Trigger send SMS api
                + write a statement - a record of transaction done in your account...
                	- timestamp


        + Transfer money from one wallet to another:
            + Send money from one number to another
            + Experience:
                + Your wallet
                + Initiate transfer and enter the recipient number, amount and then submit 
                + if successful:
                    + adjust balances for both wallets
                    + send sms to recipient 
                    + send sms to sender 
                    + write statement for boths 

            + lessons:
                + update wallets 
                + double entry logic 
                + send sms api - twice for the two numbers... 

        + BONUS: Move money into the wallet - requires integration to M-PESA to deposit money into account:
            + Safaricom daraja integration 
            + Alternative: our team can help in movement of money

            + lessons:
                + integrate to daraja 


        + We will offer the transfer on USSD banking:
            + dial *710*10*1234# - Tedd 
            + Welcome to Tedd Bank Mobile banking services. Select option:
                1. Check my balance 
                    + get the balance and display it on screen 
                    + send an sms 

                2. Transfer money 
                    + will invoke transfer 
                    + enter:
                        + mobile number 
                        + amount 
                    + submit:
                        + transfer will happen 
                        + sms messages 
                3. Mini statement 
                    + get the statement... 

        + Whatsapp banking:
            + same as USSD but the channel is whatsapp... 



    + Statement Managemeht:
        Every transaction should generate a statement for every transaction - check your bank statement.
        View statement for a given wallet - it should be possible to view statement for a given wallet. 
