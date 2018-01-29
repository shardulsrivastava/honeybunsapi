# honeybunsapi
This is the Serverless Backend for HoneyBuns Bakery Service. It is Gradle based Spring Project.
It uses serverless framework for deploying the Lambda Functions.Lambda Function code is written in Java hence this code can be used as a sample for Spring Lambda Project.

# Serverless Installation

Install NPM and then run the below commands .

1. Install NPM .(Node Package Manager)
2. Install Serverless package `npm install serverless`
3. Check the Serverless Installation `serverless -version`

----

# Serverless Configuration

To configure serverless framework. Run the below commands :

1. `serverless config credentials --provider aws --key AKIAJIXKHC43EREDG3BQ --secret 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`

---
# How to setup project

1. Clone the project : `git clone git@github.com:shardulsrivastava/honeybunsapi.git`
2. Go to home directory of the project. `cd <PROJECT HOME> `
3. To Deploy the Lambda Function. Run  : `serverless deploy -v`
4. Once Lambda Function is deployed, to Test the LambdaFunction Run : `serverless invoke -f registration -p registratontest-data.json`
5. Once Done . Remove the Lambda Function by running ` serverless remove -v`
