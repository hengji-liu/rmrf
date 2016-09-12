<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
</head>
<body>

<form action="BookTrade" method="post">
    <!-- row one -->
    <div class="form-group row">
        <input type="hidden" name="reqtype" value="ADMIN_LOGIN">
        <div class="col-xs-3">
            <label for="publications">Admin Name:</label> <input
                type="text" class="form-control"
                name="admin_name" placeholder="Admin Name">
        </div>

        <div class="col-xs-3">
            <label for="exampleInputName2">Password:</label> <input
                type="text" name="password"
                class="form-control" placeholder="password">
        </div>

        <div class="col-xs-2">
            <button type="submit" class="form-control btn">Search</button>
        </div>

    </div>

</form>

</body>
</html>
