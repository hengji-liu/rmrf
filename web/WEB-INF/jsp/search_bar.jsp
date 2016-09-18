<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- the user search bar -->
<div class="user_search_bar">
    <form action="c" method="post">
        <div class="form-group row">
            <input type="hidden" name="reqtype" value="${param.reqtype}">
            <div class="col-xs-6">
                <label >Search:</label>
                <input type="text" class="form-control" name="username" placeholder="Name/User Name" required>
            </div>
            <div class="col-xs-5">
                <button type="submit" class="form-control btn">Search</button>
            </div>
        </div>
    </form>
</div>



