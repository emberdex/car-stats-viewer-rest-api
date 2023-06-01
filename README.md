# Car Stats Viewer REST API

A REST API to store and query data submitted by the [Car Stats Viewer Android Automotive app](https://www.polestar-forum.com/threads/car-stats-viewer-a-better-range-assistant.10261/).

## Features

### Implemented

- Data ingress from the AAOS app via webhook.
- Swagger UI documentation.
- Fetch and delete all data points.
- Multi-tenant support 
  - Multiple users can use the same instance of the API, with data segregation to ensure that only data submitted by the authenticated tenant can be retrieved.
  - Separate configurable account to manage tenants (create, delete, update access key)

### TODO

- Ability to query and sort data by arbitrary criteria
  - e.g. "fetch all data points where the vehicle was charging, sorted by date"

## Dependencies

- Java >= 8
- MongoDB

## Deployment

### Docker Compose (easiest method)

This repository comes with a Docker Compose stack, in the `compose` directory.

To use it, rename the `example.env.api` and `example.env.mongodb` files to `.env.api` and `.env.mongodb` respectively, and fill in the fields according to these tables:

#### .env.api

| Variable name           | Purpose                                                                                                                                   |
|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| `MONGODB_USERNAME`      | The username used by the API to authenticate against the MongoDB database.                                                                |
| `MONGODB_PASSWORD`      | The password used by the API to authenticate against the MongoDB database.                                                                |
| `MONGODB_DATABASE`      | The database in which all data will be stored.                                                                                            |
| `MONGODB_HOST`          | The MongoDB host to connect to.<br/>The default value can be used if the container name in the Docker Compose definition was not changed. |
| `MONGODB_PORT`          | The port on which MongoDB is running.<br/>Leave this as default unless you have changed the `mongod` listen port.                         |
| `TENANT_ADMIN_USERNAME` | The username for the tenant admin user, used to manage tenants.                                                                           |
| `TENANT_ADMIN_PASSWORD` | The password for the tenant admin user, used to manage tenants.                                                                           |

#### .env.mongodb

| Variable name                | Purpose                                                                                                                                            |
|------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| `MONGO_USERNAME`             | The username used by the API to authenticate against the MongoDB database. These credentials are used on first startup to initialise the database. |
| `MONGO_PASSWORD`             | The password used by the API to authenticate against the MongoDB database. These credentials are used on first startup to initialise the database. |
| `MONGO_INITDB_DATABASE`      | The name of the database which will be initialised on first startup.                                                                               |
| `MONGO_INITDB_ROOT_USERNAME` | The username of the MongoDB root user which will be created on first startup.                                                                      |
| `MONGO_INITDB_ROOT_PASSWORD` | The password of the MongoDB root user which will be created on first startup.                                                                      |

Once the variables have been populated, simply use `docker-compose up -d` to start the stack. Once running, the API will listen for HTTP requests on port `8080`.

### Manual deployment

Use the `spring-boot:run` goal in Maven to run the application, ensuring that the `.env.api` variables mentioned in the table above are set according to the MongoDB configuration.

## Usage

To have the application send data to the API, there are a couple of requirements:

- The connection to the API must be secured via HTTPS.
  - This is due to a restriction imposed by Android preventing applications without specific manifest configuration from sending unencrypted network traffic.
  - You can achieve this by using a service like Cloudflare, `ngrok`, or a reverse proxy such as `nginx`.
- You must create a tenant to log data points against.

To create a tenant, you can use a PUT request to the `/tenant` endpoint, with the following request body:

```json
{
  "tenantName": "example",
  "accessKey": "example"
}
```

Use HTTP Basic authentication, with the username and password set to the same values previously specified in the `TENANT_ADMIN_USERNAME` and `TENANT_ADMIN_PASSWORD` environment variables.

The `tenantName` and `accessKey` fields in the request body are used as the username and password respectively when setting up the HTTP webhook in the application.

`// TODO: add screenshots` Finally, in the Car Stats Viewer application, configure the HTTP webhook with the following parameters:

- URL: the `/ingress` endpoint on the API.
  - For example, if the API was deployed at `https://csv.api`, the final URL would be `https://csv.api/ingress`.
- Username: the tenant name you specified when creating the tenant.
- Password: the access key you specified when creating the tenant.

You can optionally enable location in the application to send location data to the API.

More information and documentation on the available endpoints can be found at `/swagger-ui/index.html`.

## Contributing

Thanks for your contributions in advance. I am open to pull requests and issues on this project, as well as feature suggestions.

When reporting a bug, please provide steps taken to reproduce the issue, and any example configuration, code or HTTP requests where applicable. I will action these requests as soon as possible, however please take into consideration that this is simply a passion project and there are only so many hours in the day 😅
