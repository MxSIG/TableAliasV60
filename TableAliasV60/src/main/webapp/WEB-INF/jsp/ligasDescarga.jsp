<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : ligasDescarga
    Created on : 8/04/2010, 01:27:37 PM
    Author     : joseluis
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Descarga de la Red Geod&eacute;sica Nacional Activa</title>
        <link rel="stylesheet" type="text/css" href="${servidorReferer}/estilos/Navegacion.css">
    </head>
    <body style="margin: 0">
        <%@include file="encabezado.jsp"%>
        <table border="0" cellspacing="2">
            <tr>
                <td colspan="3" bgcolor="${colores.oscuro}">
                    <strong>
                        <font color="#FFFFFF" size="+1" face="Arial, Helvetica, sans-serif">
                            Estaci&oacute;n fija y rango de descarga seleccionado
                        </font>
                    </strong>
                </td>
            </tr>
            <tr bgcolor="${colores.medio}">
                <td><strong><font face="Arial, Helvetica, sans-serif">Estaci&oacute;n fija</font></strong></td>
                <td><strong><font face="Arial, Helvetica, sans-serif">Fecha y hora inicial</font></strong></td>
                <td><strong><font face="Arial, Helvetica, sans-serif">Fecha y hora final</font></strong></td>
            </tr>
            <tr bgcolor="${colores.claro}">
                <td align="center">
                    <%--Estacion--%>
                    ${administradorEstaciones.estacionActual.codigo}
                </td>
                <td>
                    <%--Fecha hora inicio--%>
                    Fecha
                    ${administradorEstaciones.parametros.fechaInicio}
                    <br>
                    Hora
                    ${administradorEstaciones.parametros.horaInicio}
                </td>
                <td>
                    <%--Fecha hora termino--%>
                    Fecha
                    ${administradorEstaciones.parametros.fechaFin}
                    <br>
                    Hora
                    ${administradorEstaciones.parametros.horaFin}
                </td>
            </tr>
        </table>
        <a href="inicio.do">Realizar otra selecci&oacute;n</a>

        <hr>
        <p>Descargar todos los archivos:</p>
        <form action="archivo/${administradorEstaciones.rangoArchivos}" method="post" name="forma1">
            <input name="tipo" type="hidden" value="multi">
            <c:forEach var="archivo" items="${administradorEstaciones.listaArchivos}">
                <input name="archivo" type="hidden" value="${archivo.nombreCompleto}">    
            </c:forEach>
            <input type="submit" name="boton" value="${administradorEstaciones.rangoArchivos}">
        </form>

        <c:if test="${administradorEstaciones.diasDescarga>1}">
            <hr>
            <p>Descarga por d&iacute;a completo:</p>
            <c:set var="columna" value="${0}"/>
            <table border="0">
                <tr>
                    <c:forEach var="archivosDia" items="${administradorEstaciones.listaArchivosDia}">
                        <td>
                            <form action="archivo/${archivosDia.rangoArchivos}" method="post" name="forma1">
                                <input name="tipo" type="hidden" value="multi">
                                <c:forEach var="archivo" items="${archivosDia.listaArchivos}">
                                    <input name="archivo" type="hidden" value="${archivo.nombreCompleto}">
                                </c:forEach>
                                <input style="width: 190px" type="submit" name="boton" value="${archivosDia.rangoArchivos}">
                            </form>
                        </td>
                        <c:set var="columna" value="${columna+1}"/>
                        <c:if test="${columna>3}">
                            <c:set var="columna" value="${0}"/>
                        </tr>
                        <tr>
                        </c:if>
                    </c:forEach>
                    <td></td>
                </tr>
            </table>
        </c:if>

        <hr>
        <p>Descarga por archivo individual:</p>
        <c:set var="columna" value="${0}"/>
        <table border="0">
            <tr>
                <c:forEach var="archivo" items="${administradorEstaciones.listaArchivos}">
                    <td>
                        <form action="archivo/${archivo.soloArchivo}" method="post" name="forma1">
                            <input name="tipo" type="hidden" value="simple">
                            <input name="archivo" type="hidden" value="${archivo.nombreCompleto}">
                            <input style="width: 120px" type="submit" name="boton" value="${archivo.soloArchivo}">
                        </form>
                    </td>
                    <c:set var="columna" value="${columna+1}"/>
                    <c:if test="${columna>5}">
                        <c:set var="columna" value="${0}"/>
                    </tr>
                    <tr>
                    </c:if>
                </c:forEach>
                <td></td>
            </tr>
        </table>

    </body>
</html>
