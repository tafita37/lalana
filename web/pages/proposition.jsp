<%@page import="model.route.pk.*"%>
<%@page import="model.reparation.*"%>
<%
    Pk[] listePk = (Pk[]) request.getAttribute("listePk");
    Reparation reparation=(Reparation) request.getAttribute("reparation");
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
                Numero
            </th>
            <th>
                Borne
            </th>
            <th>
                Etat
            </th>
            <th>
                Cout total
            </th>
            <th>
                Details
            </th>
        </tr>
        <%
            for(int i=0; i<listePk.length; i++) {
                if(listePk[i]!=null) {
                    %>
                    <tr>
                        <td>
                            <% 
                                out.println(listePk[i].getIdPk());
                            %>
                        </td>
                        <td>
                            <% 
                                out.println(listePk[i].getBorne());
                            %>
                        </td>
                        <td>
                            <% 
                                out.println(listePk[i].getEtatPk().getNiveauEtatPk());
                            %>
                        </td>
                        <td>
                            <% 
                                out.println(listePk[i].getCoutTotalTraitement(null));
                            %>
                        </td>
                        <td>
                            <a href=<% out.println("detailTraitement.htm?id_reparation="+reparation.getIdReparation()+"&&id_pk="+listePk[i].getIdPk()); %>>
                                Details
                            </a>
                        </td>
                    </tr>
                <% }
            }
        %>
    </table>
</body>
</html>