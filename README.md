# honeybunsapi
This is the Serverless Backend for HoneyBuns Bakery Service

# INSTALLTION DETAILS

Install NPM and then run the below commands .

1. Install NPM .(Node Package Manager)
2. npm install serverless
3. Run serverless -version

----

# Serverless Configuration

To configure serverless framework. Run the below commands :

1. serverless config credentials --provider aws --key <ACCESSKEYID> --secret <SECRETACCESSKEY>

---
# How to setup project

1. Clone the project : `git clone git@github.com:shardulsrivastava/honeybunsapi.git`
2. Go to home directory of the project. `cd <PROJECT HOME> `
3. To Deploy the Lambda Function. Run  : `serverless deploy -v`
4. Once Lambda Function is deployed . Run : `serverless invoke -f registration -p registratontest-data.json`
5. Once Done . Remove the Lambda Function by running ` serverless remove -v`
