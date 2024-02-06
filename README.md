# Group Study Planner - Backend

## Table of Contents

- [Introduction](#introduction)
- [Installation](#installation)
- [DB Schema](#db-schema)
- [Usage](#usage)
- [Api Doc](#api-doc)

## Introduction

The backend of Group Study Planner is built with Spring Boot, Maven, and Java. It handles the server-side logic, manages data storage, and provides an API for the client to interact with.

## Installation

Follow these steps to install and set up the backend:

1. Ensure you have Java and Maven installed on your machine.
2. Clone the repository:

   ```bash
   git clone --recursive https://github.com/breathecode6365/Group-Study-Planner-Hackathon
   ```

## Installation

Follow these steps to install and set up the backend:

1. Ensure you have Java and Maven installed on your machine.
2. Clone the repository:

   ```bash
   git clone --recursive https://github.com/breathecode6365/Group-Study-Planner-Hackathon
   ```

3. Configure the Database:

   - Group Study Planner backend uses PostgreSQL. If you prefer using Docker for the database, you can run the following Docker command:

     ```bash
     docker run -d -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password -e POSTGRES_DB=gsp -v gsp_postgresql_data:/var/lib/postgresql/data -p 5433:5432 --name gsp postgres:latest
     ```

     This command will start a PostgreSQL container named "gsp" with the specified user, password, and database name. Adjust the credentials as needed.

     **Note:** If you already have a PostgreSQL server running, skip this step and update the database configuration in `src/main/resources/application.properties` accordingly.

4. Update the Database Configuration:

   - Update the database configuration in the `src/main/resources/application.properties` file:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5433/gsp
     spring.datasource.username=username
     spring.datasource.password=password
     ```

     Ensure that the URL, username, and password match the Docker container configuration.

5. Start the Backend Server:

   - Download all the packages from `maven repository` :

     ```bash
      mvn clean install
     ```

   - Start the backend server by running:

     ```bash
     java -jar target/server.jar
     ```

   The server will be accessible at `http://localhost:8080/api`.

6. Verify Installation:

   - Open a web browser and navigate to `http://localhost:8080/auth/hello`. You should see a welcome message.
## DB Schema

The Group Study Planner backend uses a PostgreSQL database with the following schema:
![DB Schema](./doc%20resources/Group%20Study%20Planner.png)

## Usage

The Group Study Planner backend empowers users with a range of collaborative study features. Explore the following functionalities:

1. **Forming Study Groups:**

   - Create study groups to collaborate with peers, fostering a shared learning environment.

2. **Inviting Friends to Groups:**

   - Group leaders can invite friends who are already users of the platform to join study groups.

3. **Task Creation:**

   - Create tasks within study groups for assignments, study sessions, or collaborative projects.

4. **Task Submission:**

   - Submit completed tasks or assignments to the group for collaborative review and discussion.

5. **Resource Sharing:**

   - Share educational resources, links, and files with group members to enhance the study experience.

6. **Future Enhancements:**
   - **Integration with Video Conferencing:**
     - Future updates aim to seamlessly integrate with video conferencing tools like Zoom or Google Meet for virtual study sessions.
   - **Calendar for Reminders:**
     - An upcoming calendar feature will enable users to set reminders for study sessions, assignment deadlines, and group meetings.

## API Doc

<details>
<summary><code>Auth:<code>Login</code></summary>

##### Parameters

> | name     | type   | data type      | description                            |
> | -------- | ------ | -------------- | -------------------------------------- |
> | emailId  | String | object (JSON ) | Use Authorised admin EmailId for login |
> | password | String | object (JSON ) | Password for authentication            |

##### Responses

> | http code | content-type               | response                                                     |
> | --------- | -------------------------- | ------------------------------------------------------------ |
> | `201`     | `text/plain;charset=UTF-8` | 'http Status: Ok', 'message: Login successful'               |
> | `401`     | `application/json`         | 'http Status: Bad Request', 'message:Unauthorized operation' |

##### Example cURL

> ```bash
> curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:8080/auth/login
> ```

##### Request Body

```json
{
  "emailId": "user@example.com",
  "password": "userpassword"
}
```

##### Response Body

```json
{
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoicGRhbmRsdXJAZ2l0YW0uaW4iLCJleHAiOjE3MDcxNjYwMTAsImlhdCI6MTcwNzE2MjQxMCwic2NvcGUiOiJVU0VSIn0.dw233DQ-9K6wBMRHVbrQqSKNoyTGv8x8OK-gKWoijYu1o9njXk0RMlHDpLHI0rpbBNUyWLn5D-DSv48s3q7mig",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoicGRhbmRsdXJAZ2l0YW0uaW4iLCJleHAiOjE3MDk3NTQ0MTEsImlhdCI6MTcwNzE2MjQxMCwic2NvcGUiOiJVU0VSIn0.NDkw28W_-KwOvf_hc1RAsE0kkwT3bW67q-iTfYLxogHKWUEvJNif68tTLG-IFLf4m5GhW_DEtFnFMBNlcA-5Ag"
  },
  "basicResDTO": { "message": "Login Successful!", "status": "OK" }
}
```

</details>
<details>
<summary><code>Auth:</code><code>Signup</code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "emailId": "vbandaru2@domain.in",
    "password": "password",
    "name": "mahesh"
}'
```

#### Request Body

```json
{
  "name": "John Doe",
  "emailId": "admin@example.com",
  "password": "adminpassword"
}
```

#### Response Body

```json
{
  "message": "Welcome New User",
  "status": "CREATED"
}
```

</details>
<details>
<summary><code>User:</code><code> getProfile</code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoicGRhbmRsdXJAZ2l0YW0uaW4iLCJleHAiOjE3MDcyMTkxNTcsImlhdCI6MTcwNzIxNTU1Nywic2NvcGUiOiJVU0VSIn0.PCAw_whqUQekBi-pHGJW6M102gWhoSXodUYgKAIPV087BA4P98P5gR_jCEbQT_AQLiaXNHdGMBOvu7tMPcdNRw'
```

##### Response Body

```json
{
  "data": {
    "name": "pdandlur",
    "emailId": "email@domain.in",
    "profilePic": "https://www.pngitem.com/pimgs/m/22-223968_default-profile-picture-circle-hd-png-download.png"
  },
  "basicResDTO": {
    "message": "Profile Fetched Successfully!",
    "status": "OK"
  }
}
```

</details>
<details>
<summary><code>User:</code><code> UpdateProfile</code></summary>

##### Request cURL

```bash
curl --location --request PUT 'localhost:8080/api/user' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoicGRhbmRsdXJAZ2l0YW0uaW4iLCJleHAiOjE3MDcyMTkxNTcsImlhdCI6MTcwNzIxNTU1Nywic2NvcGUiOiJVU0VSIn0.PCAw_whqUQekBi-pHGJW6M102gWhoSXodUYgKAIPV087BA4P98P5gR_jCEbQT_AQLiaXNHdGMBOvu7tMPcdNRw' \
--data-raw '{
    "name": "vikas",
    "emailId": "emailId@gitam.in",
    "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg"
}'
```

##### Request Body

```json
{
  "name": "vikas",
  "emailId": "emailId@gitam.in",
  "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg"
}
```

##### Response Body

```json
{
  "message": "Profile Updated Successfully!",
  "status": "OK"
}
```

</details>
<details>
<summary><code>User:</code><code> updatePassword</code></summary>

##### Request cURL

```bash
curl --location --request PATCH 'localhost:8080/api/user/password' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ' \
--data '{
    "oldPassword": "password",
    "newPassword": "password"
}'
```

##### Request Body

```json
{
  "oldPassword": "password",
  "newPassword": "password"
}
```

##### Response Body

```json
{
  "message": "Password Updated Successfully!",
  "status": "OK"
}
```

</details>
<details>
<summary><code>User:</code> <code>getAllgroups</code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/user/groups' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ'
```

##### Response Body

```json
{
  "data": [
    {
      "id": "d6f71095-9ae5-4e5b-9961-064600d947aa",
      "name": "lets learn react",
      "description": "desc",
      "owner": {
        "name": "vikas",
        "emailId": "emailId@gitam.in",
        "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg"
      }
    }
  ],
  "basicResDTO": {
    "message": "Group Fetched Successfully!",
    "status": "OK"
  }
}
```

</details>
<details>
<summary><code>User:</code><code>getGroup</code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/group/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ'
```

##### Response Body

```json
{
  "data": {
    "id": "d6f71095-9ae5-4e5b-9961-064600d947aa",
    "name": "lets learn react",
    "description": "desc",
    "owner": {
      "name": "vikas",
      "emailId": "emailId@gitam.in",
      "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg"
    }
  },
  "basicResDTO": {
    "message": "Group fetched successfully!",
    "status": "ACCEPTED"
  }
}
```

</details>
<details>
<summary><code>User:</code> getMembers<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/group/members/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ'
```

##### Response Body

```json
{
  "data": [
    {
      "name": "vikas",
      "emailId": "emailId@gitam.in",
      "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg"
    }
  ],
  "basicResDTO": {
    "message": "Members fetched successfully!",
    "status": "ACCEPTED"
  }
}
```

</details>
<details>
<summary><code>User:</code> GetImportantTasks<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/user/tasks' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ'
```

##### Response Body

```json
{
{
    "data": [
        {
            "taskId": "c59069f5-0179-477b-b011-4c4eb06bc1fb",
            "name": "learn state",
            "description": "asdfasdfasdf",
            "createdOn": "2024-02-04T16:24:09.252081",
            "deadline": 7,
            "progress": 100.0,
            "status": "COMPLETED"
        },
        {
            "taskId": "70ab41db-f610-4eb7-ad50-094bff10bba1",
            "name": "asdfasdf",
            "description": "asdf",
            "createdOn": "2024-02-04T16:24:24.289524",
            "deadline": 1,
            "progress": 100.0,
            "status": "COMPLETED"
        }
    ],
    "basicResDTO": {
        "message": "Task Fetched Successfully!",
        "status": "OK"
    }
}
```

</details>
<details>
<summary><code>Group:</code> createGroup<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/group' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ' \
--data '{
    "name": "My Group 2",
    "description": "ABdbre hSBD  ahu h H DHIHH CJbrbfl "
}'
```

##### Request Body

```json
{
  "name": "My Group 2",
  "description": "ABdbre hSBD  ahu h H DHIHH CJbrbfl "
}
```

##### Response Body

```json
{
  "message": "Group Created Successfully!",
  "status": "CREATED"
}
```

</details>
<details>
<summary><code>Group:</code> updateGroup<code></code></summary>

##### Request cURL

```bash
curl --location --request PUT 'localhost:8080/api/group/5abc4359-cfb6-4cc9-9f3e-fcc6f64ebd36' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ' \
--data '{
    "name": "ChangedName",
    "description": "Lol I changed"
}'
```

##### Request Body

```json
{
  "name": "ChangedName",
  "description": "Lol I changed"
}
```

##### Response Body

```json
{
  "message": "Group Updated Successfully!",
  "status": "OK"
}
```

</details>
<details>
<summary><code>Group:</code> getAllTasks<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/group/tasks/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ'
```

##### Response Body

```json
{
  "data": [
    {
      "taskId": "c59069f5-0179-477b-b011-4c4eb06bc1fb",
      "name": "learn state",
      "description": "asdfasdfasdf",
      "createdOn": "2024-02-04T16:24:09.252081",
      "deadline": 7,
      "progress": 100.0,
      "status": "COMPLETED"
    },
    {
      "taskId": "70ab41db-f610-4eb7-ad50-094bff10bba1",
      "name": "asdfasdf",
      "description": "asdf",
      "createdOn": "2024-02-04T16:24:24.289524",
      "deadline": 1,
      "progress": 100.0,
      "status": "COMPLETED"
    }
  ],
  "basicResDTO": {
    "message": "Task Fetched Successfully!",
    "status": "OK"
  }
}
```

</details>
<details>
<summary><code>Group:</code> addResource<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/group/resource/5abc4359-cfb6-4cc9-9f3e-fcc6f64ebd36' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ' \
--data '{
    "name": "hello",
    "url": "someresource.com/file"
}'
```

##### Request Body

```json
{
  "name": "hello",
  "url": "someresource.com/file"
}
```

##### Response Body

```json
{
  "message": "Resource Added Successfully!",
  "status": "CREATED"
}
```

</details>
<details>
<summary><code>Group:</code> addForum<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/group/forum/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ' \
--data '{
    "discussion": "Hey some one can effect what is tyndall effect?"
}'
```

##### Request Body

```json
{
  "discussion": "Hey some one can effect what is tyndall effect?"
}
```

##### Response Body

```json
{
  "message": "Forum Added Successfully!",
  "status": "CREATED"
}
```

</details>
<details>
<summary><code>Group:</code> getForum<code></code></summary>

##### Request cURL

```bash
curl --location --request GET 'localhost:8080/api/group/forum/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIxOTc0NSwiaWF0IjoxNzA3MjE2MTQ1LCJzY29wZSI6IlVTRVIifQ.t27P_qRnZzag6_9dY--2TY4P8pYANYZO_n1J4xlIqq21XpYLeSIpJp59IWbSWMBWxvASUCTxMDiD03-dpMIPWQ'
```

##### Response Body

```json
{
  "data": [
    {
      "did": "80bf2091-7c46-4416-ab9a-ed0a5b608976",
      "text": "wats state",
      "userId": {
        "uid": "5950a9c2-cd86-4f81-b61c-33e6a9247c8b",
        "name": "vikas",
        "emailId": "emailId@gitam.in",
        "password": "$2a$09$Rmd6TpYviYHeymlHFfVwmuFqSb6TYiBYg53S3x2pSYeBHIba7Z1Tq",
        "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg",
        "verified": false
      },
      "groupId": {
        "gid": "d6f71095-9ae5-4e5b-9961-064600d947aa",
        "name": "lets learn react",
        "description": "desc",
        "overallProgress": 10000.0,
        "createdBy": {
          "uid": "5950a9c2-cd86-4f81-b61c-33e6a9247c8b",
          "name": "vikas",
          "emailId": "emailId@gitam.in",
          "password": "$2a$09$Rmd6TpYviYHeymlHFfVwmuFqSb6TYiBYg53S3x2pSYeBHIba7Z1Tq",
          "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg",
          "verified": false
        },
        "createdOn": "2024-02-04T16:21:31.002577"
      },
      "time": "2024-02-04T16:24:44.589894"
    },
    {
      "did": "5048d6a1-6466-4d32-acc8-96b12b795d38",
      "text": "Hey some one can effect what is tyndall effect?",
      "userId": {
        "uid": "5950a9c2-cd86-4f81-b61c-33e6a9247c8b",
        "name": "vikas",
        "emailId": "emailId@gitam.in",
        "password": "$2a$09$Rmd6TpYviYHeymlHFfVwmuFqSb6TYiBYg53S3x2pSYeBHIba7Z1Tq",
        "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg",
        "verified": false
      },
      "groupId": {
        "gid": "d6f71095-9ae5-4e5b-9961-064600d947aa",
        "name": "lets learn react",
        "description": "desc",
        "overallProgress": 10000.0,
        "createdBy": {
          "uid": "5950a9c2-cd86-4f81-b61c-33e6a9247c8b",
          "name": "vikas",
          "emailId": "emailId@gitam.in",
          "password": "$2a$09$Rmd6TpYviYHeymlHFfVwmuFqSb6TYiBYg53S3x2pSYeBHIba7Z1Tq",
          "profilePic": "https://png.pngtree.com/png-vector/20191110/ourmid/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg",
          "verified": false
        },
        "createdOn": "2024-02-04T16:21:31.002577"
      },
      "time": "2024-02-06T16:50:09.207835"
    }
  ],
  "basicResDTO": {
    "message": "Forum Fetched!",
    "status": "ACCEPTED"
  }
}
```

</details>
<details>
<summary><code>Invitation:</code> inviteToGroup<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/invitation/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxJZEBnaXRhbS5pbiIsImV4cCI6MTcwNzIyMjIyOSwiaWF0IjoxNzA3MjE4NjI5LCJzY29wZSI6IlVTRVIifQ.fgYwFw6099O5WazCFGEdojIr7q8z3Oc7_u1GJlyD3P-yFAic3Clul6SyA-OZsKfX617HiuFzBc9Rwtq63qn7pQ' \
--data-raw '{
    "emailId":"ckuppani@gitam.in"
}'
```

##### Request Body

```json
{
  "emailId": "samplemail@gmail.com"
}
```

##### Response Body

```json
{
  "message": "Invitation Sent Successfully!",
  "status": "CREATED"
}
```

![email screenshot](./doc%20resources/mailscreenshot.jpg)

</details>
<details>
<summary><code>Invitation:</code> verifyInvite<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/invitation/verify/c2fd239c-62b2-4d89-860d-2b03894bfdbf' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiY2t1cHBhbmlAZ2l0YW0uaW4iLCJleHAiOjE3MDcyMjI3NjQsImlhdCI6MTcwNzIxOTE2NCwic2NvcGUiOiJVU0VSIn0._NKUc2DmuPeqtMqvr8B4fqBbDfr7Tck4l-FOStGyoLlybjcdimVC256P7iScPAkjIvHi4ZBQSWOuSEDT2DdQUQ'
```

##### Response Body

```json
{
  "message": "Member Added!",
  "status": "OK"
}
```

</details>
<details>
<summary><code>Task:</code> createNewTask<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/task/d6f71095-9ae5-4e5b-9961-064600d947aa' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiY2t1cHBhbmlAZ2l0YW0uaW4iLCJleHAiOjE3MDcyMjI3NjQsImlhdCI6MTcwNzIxOTE2NCwic2NvcGUiOiJVU0VSIn0._NKUc2DmuPeqtMqvr8B4fqBbDfr7Tck4l-FOStGyoLlybjcdimVC256P7iScPAkjIvHi4ZBQSWOuSEDT2DdQUQ' \
--data '{
    "taskName":"complete homework 6969",
    "taskDescription":"complete homework insjb Dnd Dd ",
    "taskDeadline":3
}'
```
##### Request Body

```json
{
  "taskName": "complete homework 6969",
  "taskDescription": "complete homework insjb Dnd Dd ",
  "taskDeadline": 3
}
```
##### Response Body

```json
{
  "message": "Task Created Successfully!",
  "status": "CREATED"
}
```
</details>
<details>
<summary><code>Task:</code> checkTask<code></code></summary>

##### Request cURL

```bash
curl --location 'localhost:8080/api/task/5aac292a-f939-4a7e-8eac-4609ab690a5e' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiY2t1cHBhbmlAZ2l0YW0uaW4iLCJleHAiOjE3MDcyMjI3NjQsImlhdCI6MTcwNzIxOTE2NCwic2NvcGUiOiJVU0VSIn0._NKUc2DmuPeqtMqvr8B4fqBbDfr7Tck4l-FOStGyoLlybjcdimVC256P7iScPAkjIvHi4ZBQSWOuSEDT2DdQUQ'
```
##### Response Body

```json
{
  "message": "Task Fetched Successfully!",
  "status": "OK"
}
```
</details>