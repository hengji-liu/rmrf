<%--
  Created by IntelliJ IDEA.
  User: liquan
  Date: 26/09/2016
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Graph Search</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://visjs.org/dist/vis.js"></script>
    <link href="http://visjs.org/dist/vis.css" rel="stylesheet" type="text/css" />
    <script src="http://visjs.org/examples/googleAnalytics.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <style>
        /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
        .row.content {height: 600px}

        /* Set gray background color and 100% height */
        .sidenav {
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height: auto;}
        }
        #mynetwork {
            width: 600px;
            height: 400px;
            border: 1px solid lightgray;
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4>Graph Search</h4>
            <ul class="nav nav-pills nav-stacked">
                <li class="active"><a href=/welcome.jsp>Home</a></li>

            </ul><br>
            <div class="input-group">
                <form action="/c" method="post">
                    graph search:&nbsp&nbsp&nbsp
                    <input type="hidden" name="reqtype" value="GRAPH_SEARCH">
                    <select name="searchType" >
                        <option value="Paper">Paper</option>
                        <option value="Author">Author</option>
                        <option value="Venue">Venue</option>
                    </select><br>
                    keyword: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="text" name="keyword" placeholder="keyword" "><br>
                    number of node:<input type="text" name="maxNumberOfNodes" placeholder="input integer"><br>
                    <input type="submit" name="search" value="search">
                </form>
            </div>
        </div>
        <div class = "col-sm-9" id="mynetwork"></div>
    </div>

</div>

<footer class="container-fluid">
    <h7>UNSW</h7>
    <h6>High St</h6>
    <h6>Kensington, NSW 2052</h6>
    <h6>Australia</h6>
</footer>
<script type="text/javascript">

    var nodes = new vis.DataSet(<%out.print((String) request.getAttribute("nodeData")); %>);
    var edges = new vis.DataSet(<%out.print((String)request.getAttribute("edgeData"));%>);
    var container = document.getElementById("mynetwork");
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {
        edges:{
            arrows:{to:true},
            shadow:true
        },
        groups:{
            Paper:{
                shape:'box'
            },
            Venue:{
                shape:'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf1ad',
                    size: 50,
                    color: '#f0a30a'
                }
            },
            Author:{
                shape:'icon',
                icon: {
                    face: 'Ionicons',
                    code: '\uf47e',
                    size: 50,
                    color: 'green'
                }
            }
        }
    };


    var network = new vis.Network(container, data, options);


</script>

</body>

</html>
