<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>graphResult</title>
    <script type="text/javascript" src="http://visjs.org/dist/vis.js"></script>
    <link href="http://visjs.org/dist/vis.css" rel="stylesheet" type="text/css" />
    <script src="http://visjs.org/examples/googleAnalytics.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <style type="text/css">
        #mynetwork {
            width: 600px;
            height: 400px;
            border: 1px solid lightgray;
        }
    </style>
</head>
<body>
<div id="mynetwork"></div>
<script type="text/javascript">
    var nodes = new vis.DataSet(<%out.print((String) request.getAttribute("nodeData")); %>);
    var edges = new vis.DataSet(<%out.print((String)request.getAttribute("edgeData"));%>);
    var container = document.getElementById('mynetwork');
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
