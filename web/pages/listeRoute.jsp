<%@page import="model.route.*"%>
<%
    Route[] listeRoute=(Route[]) request.getAttribute("allRoute");
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
    <table border="1">
        <tr>
            <th>
                Nom de la route
            </th>
            <th>
                Etat
            </th>
        </tr>
        <%
            for(int i=0; i<listeRoute.length; i++) {
                %>
                <tr>
                    <td>
                        <%
                            out.println(listeRoute[i].getNomRoute());
                        %>
                    </td>
                    <td>
                        <a href=<% out.println("formReparation.htm?id_route="+listeRoute[i].getIdRoute()); %>>
                            Reparer
                        </a>
                    </td>
                </tr>
            <% }
        %>
    </table>
</body>
</html>