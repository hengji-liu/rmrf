package service;

import bean.*;
import daoImpl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class GraphService {


    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = (String) request.getParameter("keyword");
        keyword = keyword.toLowerCase();
        String searchType = (String) request.getParameter("searchType");
        String size  =  (String) request.getParameter("size");
        int maxNode;
        maxNode = Integer.parseInt((String)request.getParameter("maxNumberOfNodes"));
        ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
        ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
        GraphDaoImpl graphDao = new GraphDaoImpl();
        graphDao.nodeSearch(nodes,keyword,searchType,maxNode);
        ArrayList<GraphNode> add_nodes = new ArrayList<GraphNode>();
        Iterator<GraphNode> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()){
            graphDao.edgeSearch(nodeIterator.next(),edges,add_nodes);
        }
        nodes.addAll(add_nodes);
        ArrayList<GraphNode> showNodes = cutNode(nodes,maxNode);
        ArrayList<GraphEdge> showEdges = cutEdge(edges,showNodes);
        String nodesString = dataToString(showNodes);
        String edgesString = dataToString(showEdges);
        request.setAttribute("nodeData",nodesString);
        request.setAttribute("edgeData",edgesString);
        request.getRequestDispatcher("/graph/graphSandR.jsp").forward(request, response);
    }

    private String dataToString(ArrayList showData) {
        String dataString = "[";
        for (int i = 0;i<showData.size()-1;i++){
            dataString += showData.get(i).toString();
            dataString += ",";
        }
        if(showData.size()!=0){
            dataString += showData.get(showData.size()-1).toString();
        }
        dataString +="]";
        return dataString;
    }

    private ArrayList<GraphNode> cutNode(ArrayList<GraphNode> nodes,int maxNode) {
        GraphDaoImpl graphDao = new GraphDaoImpl();
        ArrayList<GraphNode> showNodes = new ArrayList<GraphNode>();
        Iterator<GraphNode> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext() && maxNode>0){
            GraphNode newNode = nodeIterator.next();
            if(showNodes.contains(newNode)){
                continue;
            }
            if(newNode.getLabel()==null){
                String label = graphDao.getlabel(newNode.getId());
                newNode.setLabel(label);
            }
            showNodes.add(newNode);
            maxNode--;
        }
        return showNodes;
    }

    private ArrayList<GraphEdge> cutEdge(ArrayList<GraphEdge> edges, ArrayList<GraphNode> showNodes) {
        GraphDaoImpl graphDao = new GraphDaoImpl();
        ArrayList<GraphEdge> showEdges = new ArrayList<GraphEdge>();
        Iterator<GraphEdge> edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()){
            GraphEdge newEdge = edgeIterator.next();
            if(showNodes.contains(newEdge.getFrom()) && showNodes.contains(newEdge.getTo())){
                String label = graphDao.getlabel(newEdge.getId());
                newEdge.setLabel(label);
                showEdges.add(newEdge);
            }



        }
        return showEdges;
    }



}
