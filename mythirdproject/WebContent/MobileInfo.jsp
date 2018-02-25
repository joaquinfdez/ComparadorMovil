<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.project3.MobileInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Comparador de móviles </title>
<link rel="icon" type="image/png" href="http://directorio.ugr.es/img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/estilo.css">
</head>
<body>

<%
  	ArrayList<MobileInfo> moviles = (ArrayList<MobileInfo>) session.getAttribute("moviles");
	MobileInfo movil;
	%>
	<h1>Tu comprarador de precios de móviles</h1>
	<br/>
	
	<h3>El resultado de su búsqueda es el siguiente:</h3>
	<br/>
	<% 
	for(int i=0; i<moviles.size(); i++){
		movil = moviles.get(i);
  
		if(movil != null && !movil.equals(null)){
	
	%>
	
	
			
	
			
			
			<form>
				<table>
					<tr>
					<img src="<%out.println(movil.getImage());%>" alt="No se puede mostrar la imagen"  height="300" width="220" style="float:left; margin:10px;" >
					</tr>
			  		<tr>
			  	  		<td><input type="text" value="ASIN: <%out.println(movil.getAsin());%>" readonly="readonly"></td>
			  		</tr>
			  		<tr>
						<td><input type="text" value="Marca: <%out.println(movil.getMarca());%>" readonly="readonly" id="marca"></td>
					</tr>
					<tr>
			  			<td><input type="text" value="Modelo: <%out.println(movil.getModelo());%>" readonly="readonly"></td>
			  		</tr>
			  		<tr>
			  			<td><input type="text" value="Precio: <%out.println(movil.getPrecio());%>" readonly="readonly"></td>
			  		</tr>
			  		<tr>
			  			<td><a href="<%out.println(movil.getEnlace());%>"><p>Ver oferta</p></a></td>
			  		</tr>
			  		
					
				</table>
				<br>
			</form>
		<%} %>
	<%} %>	


</body>
</html>