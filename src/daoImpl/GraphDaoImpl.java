package daoImpl;
import bean.*;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GraphDaoImpl {

    public void nodeSearch(ArrayList<GraphNode> nodes,String keyword,String searchType, int maxNode) {
        int count = maxNode/2;
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT subject,object from entity_store WHERE\n" +
                    "subject LIKE ? AND\n" +
                    "entity_store.predicate = ? AND\n" +
                    "entity_store.object LIKE ?";
            psmt = conn.prepareStatement(sql);
            switch (searchType) {
                case "Author":
                    psmt.setString(1, "A%");
                    psmt.setString(2, "Name");
                    break;
                case "Venue":
                    psmt.setString(1, "V%");
                    psmt.setString(2, "Name");
                    break;
                default :
                    psmt.setString(1, "P%");
                    psmt.setString(2, "Title");
                    break;
            }
            psmt.setString(3, "%" + keyword + "%");

            rs = psmt.executeQuery();
            while (rs.next() && count>0) {
                GraphNode newNode = new GraphNode();
                newNode.setId(rs.getString("subject"));
                newNode.setLabel(rs.getString("object"));
                newNode.setType(searchType);
                nodes.add(newNode);
                count--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHelper.realease(rs,psmt);
        }

    }


    public void edgeSearch(GraphNode mainNode, ArrayList<GraphEdge> edges, ArrayList<GraphNode> nodes) {
        String mainId  =  mainNode.getId();
        String mainNodeType = mainNode.getType();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            if(mainNodeType.equals("Paper")){
                String sql = "SELECT predicate,object FROM graph_store WHERE subject=?";
                conn = DBHelper.getConnection();
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, mainId);
                rs = psmt.executeQuery();
                while (rs.next() ) {
                    GraphEdge newEdge = new GraphEdge();
                    newEdge.setFrom(mainNode);
                    newEdge.setId(rs.getString("predicate"));
                    GraphNode newNode = new GraphNode();
                    String newNodeId = rs.getString("object");
                    if(newNodeId.startsWith("P")){
                        newNode.setType("Paper");
                    }else if(newNodeId.startsWith("A")){
                        newNode.setType("Author");
                    }else if(newNodeId.startsWith("V")){
                        newNode.setType("Venue");
                    }
                    newNode.setId(newNodeId);
                    newEdge.setTo(newNode);
                    edges.add(newEdge);
                    nodes.add(newNode);
                }
            }else{
                String sql = "SELECT subject,predicate FROM graph_store WHERE object=?";
                conn = DBHelper.getConnection();
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, mainId);
                rs = psmt.executeQuery();
                while (rs.next() ) {
                    GraphEdge newEdge = new GraphEdge();
                    newEdge.setTo(mainNode);
                    newEdge.setId(rs.getString("predicate"));
                    GraphNode newNode = new GraphNode();
                    String newNodeId = rs.getString("subject");
                    newNode.setType("Paper");
                    newNode.setId(newNodeId);
                    newEdge.setFrom(newNode);
                    edges.add(newEdge);
                    nodes.add(newNode);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHelper.realease(rs,psmt);
        }

    }

    public String getlabel(String id) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT object FROM entity_store WHERE subject=? AND predicate=?";
            psmt = conn.prepareStatement(sql);

            psmt.setString(1, id);
            if(id.startsWith("P")){
                psmt.setString(2, "Title");
            }else if(id.startsWith("E")){
                psmt.setString(2, "Label");
            }else {
                psmt.setString(2, "Name");
            }

            rs = psmt.executeQuery();
            while (rs.next() ) {
                return rs.getString("object");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHelper.realease(rs,psmt);
        }
        return id;
    }
}
