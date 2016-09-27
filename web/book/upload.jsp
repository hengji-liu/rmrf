<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/logistics/pre.jsp"%>
<title>Upload</title>
</head>

<body class="container-fluid">
	<form class="col-md-4 col-md-offset-4" action="c" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="reqtype" value="upload">
		<div class="form-group">
			<label for="type">Type</label> <select class="form-control" id="type"
				name="type">
				<option>article</option>
				<option>inproceedings</option>
				<option>proceedings</option>
				<option>bookincollection</option>
				<option>phdthesis</option>
				<option>mastersthesis</option>
				<option>www</option>
			</select>
		</div>
		<div class="form-group">
			<label for="title">Title</label> <input type="text"
				class="form-control" id="title" name="title" placeholder="Title"
				required="required">
		</div>
		<div class="form-group">
			<label for="price">Price</label> <input type="number"
				class="form-control" id="price" name="price" placeholder="100"
				required="required">
		</div>
		<div class="form-group">
			<label for="bookImg">Book Image</label> <input type="file"
				id="bookImg" name="bookImg">
		</div>
		<div class="form-group">
			<label for="authors">Authors</label> <input type="text"
				class="form-control" id="authors" name="authors"
				placeholder="Authors">
		</div>
		<div class="form-group">
			<label for="editors">Editors</label> <input type="text"
				class="form-control" id="editors" name="editors"
				placeholder="Editors">
		</div>

		<div class="form-group">
			<label for="year">Year</label> <input type="text"
				class="form-control" id="year" name="year" placeholder="Year">
		</div>
		<div class="form-group">
			<label for="venue">Venue</label> <input type="text"
				class="form-control" id="venue" name="venue" placeholder="Venue">
		</div>
		<div class="form-group">
			<label for="publisher">Publisher</label> <input type="text"
				class="form-control" id="publisher" name="publisher"
				placeholder="Publisher">
		</div>
		<div class="form-group">
			<label for="isbn">ISBN</label> <input type="text"
				class="form-control" id="isbn" name="isbn" placeholder="ISBN">
		</div>



		<button type="submit" class="btn btn-default">Submit</button>
	</form>
</body>
</html>
