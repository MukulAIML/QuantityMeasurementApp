# cli-service

An interactive command-line client for the Quantity Measurement App.
It replaces the React `frontend-service` with a Spring Shell–based CLI that
talks to the same `api-gateway` over HTTP — no browser required.

## Tech Stack

| Item            | Detail                          |
|-----------------|---------------------------------|
| Language        | Java 21                         |
| Framework       | Spring Boot 3.2.0               |
| Shell           | Spring Shell 3.2.0              |
| HTTP client     | RestTemplate (spring-web)       |
| Build           | Maven                           |

## Quick Start

```bash
cd cli-service
mvn spring-boot:run
```

The interactive shell launches. Type `help` to see all available commands.

## Environment Variables

| Variable          | Default                   | Description                  |
|-------------------|---------------------------|------------------------------|
| API_GATEWAY_URL   | http://localhost:8080     | Base URL of the api-gateway  |

Override for deployment:

```bash
API_GATEWAY_URL=https://my-gateway.onrender.com mvn spring-boot:run
```

## Available Commands

### Auth

```
register --username <u> --password <p>   Register a new account
login    --username <u> --password <p>   Login (stores JWT in session)
logout                                   Clear session
whoami                                   Show current user
```

### Quantity Operations

```
compare  --v1 <n> --u1 <unit> --t1 <type> --v2 <n> --u2 <unit> --t2 <type>
convert  --value <n> --from <unit> --type <type> --to <unit>
add      --v1 <n> --u1 <unit> --t1 <type> --v2 <n> --u2 <unit> --t2 <type>
subtract --v1 <n> --u1 <unit> --t1 <type> --v2 <n> --u2 <unit> --t2 <type>
divide   --v1 <n> --u1 <unit> --t1 <type> --v2 <n> --u2 <unit> --t2 <type>
```

### History

```
history                            All operation history
history-by-op --operation <op>    History filtered by type
op-count      --operation <op>    Count of successful ops by type
```

## Supported Types & Units

| Type        | Units                                                      |
|-------------|------------------------------------------------------------|
| LENGTH      | FEET, INCH, YARD, CENTIMETER, METER, KILOMETER, MILE       |
| WEIGHT      | GRAM, KILOGRAM, POUND, OUNCE, TONNE                        |
| TEMPERATURE | CELSIUS, FAHRENHEIT, KELVIN                                |
| VOLUME      | LITER, MILLILITER, GALLON, CUP, PINT                       |

## Example Session

```
shell:> register --username alice --password secret123
✅ Registered successfully.

shell:> login --username alice --password secret123
✅ Logged in as: alice

shell:> compare --v1 100 --u1 CENTIMETER --t1 LENGTH --v2 1 --u2 METER --t2 LENGTH
✅ Result:
{
  "result" : "EQUAL",
  ...
}

shell:> convert --value 100 --from FAHRENHEIT --type TEMPERATURE --to CELSIUS
✅ Result:
{
  "convertedValue" : 37.78,
  ...
}

shell:> history
✅ Result:
[ { "operation": "COMPARE", ... }, { "operation": "CONVERT", ... } ]

shell:> logout
👋 Logged out from: alice

shell:> exit
```
