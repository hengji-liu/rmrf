<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--Search user-->
<form action="c" method="post">
    <!-- row one -->
    <div class="form-group row user_search_bar">
        <input type="hidden" name="reqtype" value="USER_SEARCH">
        <div class="col-xs-8">
            <label>User Search:</label> <input
                type_="text" class="form-control"
                name="keyword" placeholder="Username/Real Name">
        </div>

        <div class="col-xs-4">
            <button type_="submit" class="form-control btn">Search</button>
        </div>
    </div>
</form>
