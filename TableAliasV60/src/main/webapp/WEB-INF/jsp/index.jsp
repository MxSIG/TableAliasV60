<%-- 
    Document   : index.jsp
    Created on : 30/03/2010, 11:32:19 AM
    Author     : YAN.LUEVANO
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>TableAlias Web Service</title>
        <jsp:forward page="SearchWebService"/>
    </head>
    <body>
        <form action="busqueda.do" method="post">
            <input type="text" name="searchCriteria" id="searchCriteria" >
            <input type="submit" value="envia" >
            <input type="hidden" value="geolocator" name="tabla">
            <input type="hidden" value="1" name="pagina">
        </form>
    </body>
</html>
