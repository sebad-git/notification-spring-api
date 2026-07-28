const express = require("express");
const http = require("http");
const app = express();
const server = http.createServer(app);

//Constants.
const DEFAULT_PORT = 3001;
const DEFAULT_SERVER = "0.0.0.0";
const SERVICE_NAME = "notification-reciever";

//Config
app.set("port", DEFAULT_PORT);
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Test route.
app.get("/", (request, response) => {
  response.json(`Service [${SERVICE_NAME}] is working.`);
});

app.post("/api/notify", (request, response) => {
  const apiKey = request.headers['x-api-key'];
  const apiResponse = { apiKey:apiKey, notification:request.body };
  console.log(JSON.stringify(apiResponse));
  response.json(apiResponse);
});

// Start Server.
server.listen(app.get("port"), DEFAULT_SERVER, () => {
  console.log(
    `Server [${SERVICE_NAME}] started at http://localhost:${app.get("port")}`,
  );
});
