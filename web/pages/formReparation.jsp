<%@page import="model.priorite.*"%>
<%@page import="model.route.*"%>
<%@page import="model.route.pk.*"%>
<%
    Object[] listePriorite=(Object[]) request.getAttribute("allPriorite"); 
    Route route=(Route) request.getAttribute("route"); 
    Object[] listeEtat=(Object[]) request.getAttribute("allEtat"); 
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
        Formulaire de reparation
    </h2>
    <form action="newReparation.htm" method="post">
        <select name="choix" id="">
            <option value="">
                Choix
            </option>
            <%
                for(int i=0; i<listePriorite.length; i++) {
                    Priorite priorite=(Priorite) listePriorite[i];
                    %>
                    <option value=<% out.println(priorite.getIdPriorite()); %>>
                        <%
                            out.println(priorite.getNomPriorite());
                        %>
                    </option>
                <% }
            %>
        </select>
        <br>
        <input type="date" name="date" id="" placeholder="Date">
        <br>
        <input type="time" name="heure" id="" placeholder="Heure">
        <br>
        <input type="number" name="budget" id="" placeholder="Budget">
        <br>
        <%
            for(int i=0; i<route.getListePk().length; i++) {
                out.println(route.getListePk()[i].getBorne());
                %>
                <select name="etatPk" id="">
                    <option value="">
                        Etat
                    </option>
                    <%
                        for(int j=0; j<listeEtat.length; j++) {
                            EtatPk etatPk=(EtatPk) listeEtat[j];
                            %>
                            <option value=<% out.println(etatPk.getNiveauEtatPk()); %>>
                                <%
                                    out.println(etatPk.getNomEtatPk());
                                %>
                            </option>
                        <% }
                    %>
                </select>
                <br>
                <input type="hidden" name="id_pk" value=<% out.println(route.getListePk()[i].getIdPk()); %>>
            <% }
        %>
        <input type="hidden" name="id_route" value=<% out.println(route.getIdRoute()); %>>
        <input type="submit" value="Valider">
    </form>
</body>
</html>