ReadME.MD

Firstly I would like to thank Conichi team for providing opportunity on this coding challenge. I was glad to work on this project which covered many aspects of microservices development principles.

Solution and the explaination about approach to the problem statement.

I have analyzed and found that there are 2 different tasks involved a. Currency exchange service b. Vat validation service.

With my experience and understanding of microservices. I have followed seperation of concern at the base level to address the given problem statement.

I have developed 5 base services which are

currency-exchange-service -- responsible for fetching the currency conversion multiple, if tomorrrow we want to switch to a different service this is has generic code and can be switched easily with new service where as conversion with quantity logic stays with currency-conversion-service which is the front end api.

vat-validation-service - seperate service for vat validation as its entirely a different data. No front end service for this, if needed we can plug one later

currency-conversion-service -- final end point for currency conversion api which connects to currency-exchange-service over zuul, api gateway. also have written a method which talks to currency-exchange-service over rest template for easy testing. Also Provided the details below for testing

netflix-eureka-naming-server -- naming server

netflix-zuul-api-gateway-server -- for Zuul api gateway for proxy communication with load balancing using inbuilt ribbon.

Features handled on all the projects are as below -

Running on port 8082 as currency exchange service is running on 8081
Content negotiation for both json and xml,
HATEOS enabled (cannot verify as we have only 1 request so no mapping to other related urls).
Custom exception handling
Internationalization
Spring boot web url caching is enabled as there is no db involved, -
Docker file added ( tested with build on local and pushing to personal private docker hub account)
Added controller level integration tests for both success and exception testing.
Run the currency-exchange-service project
Note- configure the mysql database and create the database as per application.properties file and run the project, data will be autopopulated form data.sql, else enable h2 config for inmemory testing.

I could not connect to 3rd party api's as many of them are paid service for fetching currency exchange information so created a set of valid data with exchange value as of yesterday.

Swagger -

http://localhost:8081/v2/api-docs

Get - http://localhost:8081/currency-exchange/from/USD/to/INR

Sample response - { "id": 10001, "from": "USD", "to": "INR", "conversionMultiple": 65 }

GET - http://localhost:8081/currency-exchange/from/ABC/to/INR -- Invalid "From" currency name

Response -

{ "timestamp": "2019-09-15T23:54:35.529+0000", "message": "Currency exchange information not found for exchange from: ABC to :INR", "details": "uri=/currency-exchange/from/ABC/to/INR" }

http://localhost:8081/v2/api-docs -- swagger document for currency-exchange-service

Testing the currency conversion over rest template call.
GET - just a restTemplate call without feign, not through zull gateway no load balancing done

http://localhost:8100/currency-converter-test/from/USD/to/INR/quantity/100

{ "id": 10001, "from": "USD", "to": "INR", "conversionMultiple": 65, "quantity": 100, "totalCalculatedAmount": 6500 }

Testing the microservices communication over, Rammit Mq, Zull, eureka naming server
Installing Rabbit MQ Windows https://www.rabbitmq.com/install-windows.html https://www.rabbitmq.com/which-erlang.html http://www.erlang.org/downloads Video - https://www.youtube.com/watch?v=gKzKUmtOwR4 Mac https://www.rabbitmq.com/install-homebrew.html

set RABBIT_URI=amqp://localhost

https://zipkin.io/pages/quickstart

curl -sSL https://zipkin.io/quickstart.sh | bash -s

java -jar zipkin.jar or just open https://zipkin.io/quickstart.sh to download installer and further downloading jar through installer file on windows

once jar is downloaded run java -jar zipkin.jar by navigating into download folder

http://localhost:9411/zipkin/ - default for zipkin

Order of service execution

Have RabbitMq running in background (you can start and stop it form windows start button) or through command line in max or linux
Naming server -- netflix-eureka-naming-server
Zipkin DistributerTracingServer -- jar on local machine as explained above
CurrencyExchangeService -- currency-exchange-service
CurrencyConversionService --currency-conversion-service
NetflixZullAPi -- netflix-zuul-api-gateway-server
http://localhost:8761/ Eureka dashboard - please verify if all 3 services registered and wait for few mins for zull to pick up services as it cause some delay on local machine due to load. APi call through Zull gateway -
http://localhost:8765/currency-convertion-service/currency-converter/from/USD/to/INR/quantity/100

{ "id": 10001, "from": "USD", "to": "INR", "conversionMultiple": 65, "quantity": 100, "totalCalculatedAmount": 6500 }

Db Cahcheing Web Caching testing steps - currency-exchange-service

step 1 call http://localhost:8081/currency-exchange/from/USD/to/INR step 2 verify logs for record fetched from database step 3 call again - http://localhost:8081/currency-exchange/from/USD/to/INR step 4 should not see the call happening step 5 call - http://localhost:8081/currency-exchange/evictcachce Response - Currency exchange data cache is cleared step 6 call http://localhost:8081/currency-exchange/from/USD/to/INR

Run the vat-validation-service project
Swagger documentation -

http://localhost:8082/v2/api-docs

Vat Validation API -

GET - http://localhost:8082/api/vat/validation/?vatNumber=DE257486969&checkValidity=true -- checkValidity is used to verify the vat validity from api response. http://localhost:8082/api/vat/validation/?vatNumber=DE257486969&checkValidity=false - both the cases data will be returned as its valid VAT

Some of valid vat inputs

DE257486969 DE186775212 DE2011813748

Sample Response

{ "countryCode": "DE", "vatNumber": 257486969 }

GET - http://localhost:8082/api/vat/validation/?vatNumber=AB257486969&checkValidity=true

Response - { "timestamp": "2019-09-15T23:36:55.801+0000", "message": "Input vat number : AB257486969 is not valid", "details": "uri=/api/vat/validation/" }

GET - http://localhost:8082/api/vat/validation/?vatNumber=AB257486969&checkValidity=false without validation check,

Response - it just returns the country code as generic without validity check. { "countryCode": "AB", "vatNumber": 257486969 }

Web Caching testing steps - step 1 call http://localhost:8082/api/vat/validation/?vatNumber=DE257486969&checkValidity=true step 2 verify logs for record fetched from rest api to 3rd party data fetch step 3 call again - http://localhost:8082/api/vat/validation/?vatNumber=DE257486969&checkValidity=true step 4 should not see the call happening step 5 call - http://localhost:8082/api/vat/validation/evictcachce Response - Vat data cache is cleared step 6 call http://localhost:8082/api/vat/validation/?vatNumber=DE257486969&checkValidity=true should hit service layer again.

The cahching can be enhanced further with query level and clearing cache over certain interval to avoid performance issues.

