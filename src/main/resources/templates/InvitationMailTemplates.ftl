<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Invitation to Group</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
        }

        .header {
            background-color: forestgreen;
            color: white;
            padding: 10px;
            text-align: center;
        }

        .content {
            padding: 20px;
        }

        .footer {
            background-color: forestgreen
            color: white;
            padding: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class='container'>
    <div class='header'>
        <h1>Invitation to a Group</h1>
    </div>
    <div class='content'>
        <p>Hello <b>${name}</b>,</p>
        <p>
            I am <b>${senderName}</b>!
            You have been invited to a group <b>${group}</b>.
            Please log in to your account and accept the invitation.
        </p>
        <p>
            <a href='http://localhost:5173/invitation/verify/${token}'>http://localhost:5173/invitation/verify/${token}</b></a>
        </p>
    </div>
    <div class='footer'>
        <p>Group Studies Planning</p>
    </div>
</div>
</body>
</html>