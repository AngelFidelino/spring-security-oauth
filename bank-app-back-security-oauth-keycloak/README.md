<h1>App secured by spring security using OAuth2</h1>

This app acts as a resource server, and it expects JWT Tokens provided by KeyCloak as the auth server.

## Requirements

* KeyCloak server up and running on 8180
```
bin\kc.bat start-dev --http-port 8180
```
* Create a realm on KeyCloak. If the realm has a name other than **my-bank-dev** then update the following property.
```properties
spring.security.oauth2.resourceserver.jwt.jwk-set-uri = http://localhost:8180/realms/my-bank-dev/protocol/openid-connect/certs
```
This information will be used by spring security to get certificates so that it can validate JWT Tokens by its own without going to the auth server (KeyCloak) for the token verification in each request. This token must be generated by the caller (see *bank-app-u*i) with the help of KeyCloak.

## Run KeyCloak locally
1. Download Keycloack from [Getting started](https://www.keycloak.org/guides "Getting started")#OpenJDK and download de [binary code](https://www.keycloak.org/getting-started/getting-started-zip "binary code")

2. On Windows, run it with or without specifying a port
```
bin\kc.bat start-dev --http-port 8180
```
```
bin\kc.bat start-dev
```
3. Access the console to start configuring the OAuth server http://localhost:8180/admin/master/console/

> **Tip**: use a different realm than the default (in this project we expect a realm called "my-bank-dev" but it can have any name)


## How to test
> Note: the project bank-app-ui is already integrated with KeyCloak, and it uses Authentication Code + PKCE

* Create a KeyCloak client. We need to create a client in KeyCloak within the created realm. This KeyCloak client will be needed by the caller to generate token that needs to send to this app so it can access the resources.
  In KeyCloak client we need to specify the grant type flow we want to follow. Let's suppose we used Authentication Code.
* Create a KeyCloak user. Add required roles to the user. Most of the resources in this projects needs *ADMIN* & *USER*. So the new user must have one or both of them

Now. Let's simulate we're a caller from postman
1. Getting the **authorization_endpoint** and **token_endpoint** (later use)
   Let's get the authorization_endpoint for our realm called my-bank-dev. This is the URL, just update the port and realm name if it differs from it.
```
http://localhost:8180/realms/my-bank-dev/.well-known/openid-configuration
"authorization_endpoint": "http://localhost:8180/realms/my-bank-dev/protocol/openid-connect/auth"
"token_endpoint": "http://localhost:8180/realms/my-bank-dev/protocol/openid-connect/token"
```

2. Using the **authorization_endpoint** endpoint (GET), we can use it to get the authorization Code

Add the following query params
```
client_id=mybank_client
response_type=code
scope=openid
redirect_uri=http://localhost:8033/sample
state=ascsdcsdcxxxxxxxx
```
:fa-question-circle: **client_id** client id in KeyCloak <br />
:fa-question-circle: **redirect_uri** this value must be the same we've configured in the client under the "Valid redirect URIs" option: Valid URI pattern a browser can redirect to after a successful login <br />
:fa-question-circle: **state** (optional) it's a random value used for CSRF(Cross-Site Request Forgery) attack when integrating with a real client. Since this only a test can be omitted <br />

3. Paste the formed URL into the browser where we need to sign in using the created user (neither client nor keycloak admin credentials).
   http://localhost:8180/realms/my-bank-dev/protocol/openid-connect/auth?client_id=mybank_client&response_type=code&scope=openid&redirect_uri=http://localhost:8033/sample&state=ascsdcsdcxxxxxxxx

The response from KeyCloak contains the code value (look for the ```code``` value in the response. (It'll be needed later)
http://localhost:8033/sample?state=ascsdcsdcxxxxxxxx&session_state=38dk91298-561b-492b-a38a-90126bb9cdf5&iss=http%3A%2F%2Flocalhost%3A8180%2Frealms%2Fmy-bank-dev&code=8fd38637-910a-4ffb-9bcc-6ba78d52fa1a.38d51a98-561b-406b-a38a-51126bb9cdf5.9c2aa895-4330-48ba-ba4f-2be99f89600d

Important: the code has a short life

4. Then use the token_endpoint (POST) in postman and add the following params as ```x-www-form-urlencoded```. This will give us the ```ACCESS_TOKEN```. <br />
   "token_endpoint": "http://localhost:8180/realms/my-bank-dev/protocol/openid-connect/token",

```
client_id=mybank_client
client_secret=xxx
scope=openid
grant_type=authorization_code
code=8fd38637-910a-4ffb-9bcc-6ba78d52fa1a.38d51a98-561b-406b-a38a-51126bb9cdf5.9c2aa895-4330-48ba-ba4f-2be99f89600d
redirect_uri=http://localhost:8033/sample
```
:fa-question-circle: **client_id** client id in KeyCloak <br />
:fa-question-circle: **client_secret** client secret can be found in KeyCloak server under the client's configuration <br />
:fa-question-circle: **code** this value is the code we got using the authorization_endpoint earlier <br />
:fa-question-circle: **redirect_uri** this value must be the same we've configured in the client under the "Valid redirect URIs" option: Valid URI pattern a browser can redirect to after a successful login <br />

5. Use the retrieved access_token to access resources in this project <br />
   Let's try calling http://localhost:8080/myCards?email=myemail@gmail.com <br />
   From postman make sure to use Bearer Token as the Auth type under the Authorization tab.
   <br /><br />
   Or you can call the endpoint from a command prompt using the following curl
```bash
curl --location "http://localhost:8080/myCards?email=angel%40hotmail.com" \ 
--header "Authorization: Bearer <JWT_TOKEN>"
```
