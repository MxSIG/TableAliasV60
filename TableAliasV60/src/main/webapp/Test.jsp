<%-- 
    Document   : Test
    Created on : 24/05/2010, 01:15:54 PM
    Author     : YAN.LUEVANO
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="consulta.do" method="post">
            <input type="text" name="tabla" value="c103"/>
            <input type="text" name="pagina" value="1"/>
            <!--<input type="text" name="poligono" value=""/>-->
            <textarea name="poligono" rows="5" cols="20"></textarea>
            <input type="text" name="proyName" value="mdm5"/>
            <!--<input type="text" name="x1" value="-99.1791493599866"/>
            <input type="text" name="y1" value="19.526685604207"/>
            <input type="text" name="width" value="0.0000000002"/>-->
            <input type="submit" value="envia" name="enviar">
        </form>
    </body>
</html>
