<%@page import="java.util.*"%>
<%@page import="model.reparation.*"%>
<%@page import="model.route.pk.*"%>
<%
    ArrayList<Traitement> listeTraitement=(ArrayList<Traitement>) request.getAttribute("traitements");
    Pk pk=(Pk) request.getAttribute("pk");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h2>
        
    </h2>
    <table border="1">
        <tr>
            <th>
                Traitement
            </th>
            <th>
                Prix
            </th>
            <%
                for(int i=0; i<listeTraitement.size(); i++) {
                    %>
                    <tr>
                        <td>
                            <%
                                out.println(listeTraitement.get(i).getNomTraitement());
                            %>
                        </td>
                        <td>
                            <%
                                out.println(listeTraitement.get(i).getPrix());
                            %>
                        </td>
                    </tr>
                <% }
            %>
        </tr>
    </table>
</body>
</html>