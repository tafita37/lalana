<%@page import="model.route.*"%>
<%@page import="model.reparation.*"%>
<%
    Object[] listeRoute=(Object[]) request.getAttribute("allRoute");
    Reparation[] listeReparation = (Reparation[]) request.getAttribute("listeReparations");
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
    <form action="allReparation.htm" method="post">
        <select name="id_route" id="">
            <option value="">
                Route
            </option>
            <%
                for(int i=0; i<listeRoute.length; i++) {
                    Route route=(Route) listeRoute[i];
                    %>
                    <option value=<% out.println(route.getIdRoute()); %>><% out.println(route.getNomRoute()); %></option>
                <% }
            %>
        </select>
        <input type="submit" value="Rechercher">
    </form>
    <table border="1">
        <tr>
            <th>
                Numero de reparation
            </th>
            <th>
                Etat
            </th>
            <th>
                Details
            </th>
        </tr>
        <%
            for(int i=0; i<listeReparation.length; i++) {
                %>
                <tr>
                    <td>
                        <%
                            out.println(listeReparation[i].getIdReparation());
                        %>
                    </td>
                    <td>
                        <%
                            out.println(listeReparation[i].getChoix().getNomPriorite());
                        %>
                    </td>
                    <td>
                        <a href=<% out.println("showProposition.htm?id_reparation="+listeReparation[i].getIdReparation()); %>>
                            Proposition
                        </a>
                    </td>
                </tr>
            <%}
        %>
    </table>
</body>
</html>