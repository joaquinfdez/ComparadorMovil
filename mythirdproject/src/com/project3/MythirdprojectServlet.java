
package com.project3;
import java.io.IOException;
import javax.servlet.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

@SuppressWarnings("serial")
public class MythirdprojectServlet extends HttpServlet {
	private String[] resultados=null;
	private static final String AWS_ACCESS_KEY_ID = "AKIAIP2IVABSTLAK2F4Q";
    private static final String AWS_SECRET_KEY = "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";
    private static final String ENDPOINT = "ecs.amazonaws.com";// ecs.amazonaws.com webservices.amazon.es
    private static final String ENDPOINTSP="webservices.amazon.es";
    String ITEM_ID = "FG492B/A";
    ArrayList<MobileInfo> mobiles=new ArrayList();
    private static final String AMAZON_ASSOCIATE_TAG="myfirstprojec-20"; //EEUU
    private static final String AMAZON_ASSOCIATE_TAG_ES="myfirstprojec-21"; //españa
    String enlacePC, caracteristicasPC = new String();
    
    
    public String buscaEnPcComponentes(String codigoProducto){
		LectorHTML lector = new LectorHTML();
		String enlaceParte1, enlaceParte2, resultado= new String();
		enlaceParte2 = "?p="+ parteCadena(codigoProducto);
		enlaceParte1 = "http://www.pccomponentes.com/buscar.php";
		resultado = lector.leeHtml(enlaceParte1 + enlaceParte2);
		
		int claveBusqueda = resultado.indexOf("data-href");
		if (claveBusqueda < 0){
			return "Error";
		}
		claveBusqueda+=11; // Saltamos la cadena data-href="
		String provisional, datosProducto = new String();
		provisional="";
		char letra = 'a';
		while(letra != '"'){
			letra = resultado.charAt(claveBusqueda);
			if(letra!='"'){
				provisional+=letra;
			}
			claveBusqueda++;
		}
		
		
		
		enlacePC = provisional;
		resultado = lector.leeHtml(provisional);
		
		claveBusqueda = resultado.indexOf("itemprop=\"name");
		claveBusqueda +=16; //saltamos la cadena itemprop="name">
		provisional="";
		letra='a';
		while(letra != '<'){
			letra = resultado.charAt(claveBusqueda);
			if(letra!='<'){
				provisional+=letra;
			}
			claveBusqueda++;
		}
		caracteristicasPC = provisional;
		
		
		Float precio;
		claveBusqueda = resultado.indexOf("data-price");
		claveBusqueda+=12; // Saltamos la cadena data-price="
		provisional="";
		letra = 'a';
		while(letra != '"'){
			letra = resultado.charAt(claveBusqueda);
			if(letra!='"'){
				provisional+=letra;
			}
			claveBusqueda++;
		}
		String precioFinal = new String();
		precioFinal = "";
		for(int i=0; i<provisional.length(); i++){
			if (provisional.charAt(i)==','){
				precioFinal+='.';
			}else
				precioFinal+=provisional.charAt(i);
				
		}
		return precioFinal;
	}
    
	 private NodeList getElements (Document doc, String tag)
	    {
	      return doc.getElementsByTagName(tag);
	    }
	    private Element getElement (Document doc, String tag)
	    {
	      NodeList nodelist = doc.getElementsByTagName(tag);
	      return ( (nodelist.getLength()>0) ? (Element) nodelist.item(0) : null);
	    }
	    
	    private String getElementValue (Document doc, String tag)
	    {
	      NodeList nodelist = doc.getElementsByTagName(tag);
	      return ( (nodelist.getLength()>0) ? nodelist.item(0).getTextContent() : null );
	    }
	    private String getElementValue (Element root, String tag)
	    {
	      NodeList nodelist = root.getElementsByTagName(tag);
	      return ( (nodelist.getLength()>0) ? nodelist.item(0).getTextContent() : null );
	    }
	    public String parteCadena(String cadena){
		    String resultado = new String();
		    for(int i=0; i<cadena.length(); i++){
		      if(cadena.charAt(i) == ' '){
		        resultado+='+';
		      }else{
		        resultado+=cadena.charAt(i);
		      }
		      
		    }
		    return resultado;
		  }
public String buscarEnAmazon(HttpServletRequest req, HttpServletResponse resp, String marca, String modelo) throws IOException {
			String asin="";
			
			try {
				SignedRequestsHelper helper1,helper2;
		        try {
		            helper1 = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		            helper2 = SignedRequestsHelper.getInstance(ENDPOINTSP, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return "Error en el establecimiento de helpers";
		        }
		        
		        
		        Map<String, String> params = new HashMap<String, String>();
		        params.put("Service", "AWSECommerceService");
		        params.put("Version", "2011-08-01");
		        params.put("AssociateTag", AMAZON_ASSOCIATE_TAG);
		        params.put("Operation", "ItemSearch");
		        params.put("SearchIndex","Electronics");
		        params.put("ResponseGroup", "Small");
		        params.put("Sort", "price");
		        params.put("Manufacturer", marca);
		        params.put("Keywords", modelo );
		        params.put("Brand", marca);
		        params.put("MinimumPrice","5000");
		        
		        String requestUrl1 = helper1.sign(params);
		        String requestUrl2 = helper2.sign(params);
		        String queryString="&Version=2011-08-01" + "&AssociateTag=" + AMAZON_ASSOCIATE_TAG + "&Operation=ItemSearch" +"&SearchIndex="+"Electronics"+ "&Keywords="+modelo+
		        		"&ResponseGroup="+"Small"+"&Sort="+"price" + "&Manufacturer=" +marca+ "&Brand=" + marca +"&MinimumPrice="+"5000";
		      
		        System.out.println(queryString);
		        requestUrl1 = helper1.sign(queryString);
		        requestUrl2 = helper2.sign(queryString);
		        System.out.println(requestUrl1);
		        System.out.println(requestUrl2);
				DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		        DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				Document doc1 = db.parse(requestUrl1);
				DocumentBuilderFactory dbf2 =DocumentBuilderFactory.newInstance();
		        DocumentBuilder db2;
		        db2 = dbf2.newDocumentBuilder();
			
				asin =getElementValue(doc1, "ASIN");

				
		        
		        NodeList asinNodes = getElements(doc1,"ASIN");
		        String asinCodes[]=new String[asinNodes.getLength()];
		        for (int i=0; i<asinCodes.length; i++)
		        	asinCodes[i] = asinNodes.item(i).getTextContent();
		        

			       String url =getElementValue(doc1,"DetailPageURL");
			       resultados=asinCodes;
			     
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (SAXException e) {
				// TODO Auto-generated catch block
			}
			return asin;
		}
		public void extraerInformacion(String asin){
			try {
				SignedRequestsHelper helper1,helper2;
		        try {
		            helper1 = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return;
		        }
		        
		        Map<String, String> params = new HashMap<String, String>();
		        params.put("Service", "AWSECommerceService");
		        params.put("Version", "2011-08-01");
		        params.put("AssociateTag", AMAZON_ASSOCIATE_TAG);
		        params.put("Operation", "ItemLookup");
		        params.put("Sort", "price");
		        params.put("ItemId", asin);
		        params.put("ResponseGroup", "OfferSummary");
		        String requestUrl1 = helper1.sign(params);
		        String queryString="&Version=2011-08-01" + "&AssociateTag=" + AMAZON_ASSOCIATE_TAG + "&Operation=ItemLookup" +"&Sort="+"price"+ "&ItemId=" + asin +
		        		"&ResponseGroup="+"Medium,OfferSummary";
		        requestUrl1 = helper1.sign(queryString);
		        System.out.println(requestUrl1);
		       				DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		        DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				Document doc1=null;
				try {
					doc1 = db.parse(requestUrl1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				asin =getElementValue(doc1, "ASIN");
				
				//busqueda Precios
				NodeList LowestNodes = getElements(doc1,"LowestNewPrice");
		        String LowestPrices[]=new String[LowestNodes.getLength()];
		        String prices[]=new String [LowestPrices.length];
		        
		        for (int i=0; i<LowestPrices.length; i++){
		        	LowestPrices[i] = LowestNodes.item(i).getTextContent();
		        	prices[i]="";
		        	
		        	
		        	for(int j=LowestPrices[i].indexOf("$");j<LowestPrices[i].length();j++){
		        		prices[i]+=LowestPrices[i].charAt(j);
		        	}
		        
		        }
		        //Busqueda imagenes
		        NodeList ImageNodes = getElements(doc1,"SmallImage");
		        String Images[]=new String[ImageNodes.getLength()];
		        String images_final[]=new String [Images.length];
		        
		        for (int i=0; i<Images.length; i++){
		        	Images[i] = ImageNodes.item(i).getTextContent();
		        	images_final[i]="";
		        	
		        	boolean acabado=false;
		        	for(int j=Images[i].indexOf("h");j<Images[i].length() &&!acabado;j++){
		        		if(j>3){
			        		if(Images[i].charAt(j-3)=='.'&&Images[i].charAt(j-2)=='j'&&Images[i].charAt(j-1)=='p'&&Images[i].charAt(j)=='g'){
			        			acabado=true;
			        		}
		        		}
		        		images_final[i]+=Images[i].charAt(j);
		        	}
		        	
		        	System.out.println("Esta es la imagen: "+images_final[i]);
		        	
		        
		        }
		        //busca modelos
		      NodeList titleNodes =getElements(doc1, "Title");
		      String titles[]=new String[titleNodes.getLength()];
		       for(int i=0;i<titles.length;i++){
		    	   titles[i]=getElementValue(doc1, "Title");
		       }
		       NodeList brandNodes =getElements(doc1, "Brand");
			      String brand[]=new String[brandNodes.getLength()];
			       for(int i=0;i<brand.length;i++){
			    	   brand[i]=getElementValue(doc1, "Brand");
			       }
			    NodeList linkNodes =getElements(doc1, "DetailPageURL");
				      String link[]=new String[linkNodes.getLength()];
				       for(int i=0;i<link.length;i++){
				    	   link[i]=getElementValue(doc1, "DetailPageURL");
				   }
				       
				      
				       for(int i=0;i<prices.length;i++){
				    	   MobileInfo nuevo= new MobileInfo(resultados[i], titles[i], prices[i], brand[i],images_final[i],link[i]);
				    	   mobiles.add(nuevo);
				       }
				      
		        
		        

			       String lowestNewPrice1=getElementValue(doc1,"FormattedPrice");
			       String lowestOldPrice1=getElementValue(doc1,"FormattedPrice");
			       
			        //URL
			       String url =getElementValue(doc1,"DetailPageURL");
			       for(int k=0;k<prices.length;k++)
			    	   System.out.println("ASIN:"+asin+" "+"Precio movil nuevo:"+prices[k]);
			     
			       
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			
	        

	        
			}
			catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	

		
    
 /*
 	http://webservices.amazon.com/onca/xml?Service=AWSECommerceService
&Version=2011-08-01
&AssociateTag=myfirstprojec-20
&Operation=ItemSearch
&SearchIndex=Electronics
&ResponseGroup=Small
&Sort=price
&Brand=Apple
&MinimumPrice=5000
&Keywords=Iphone+6s+64gb
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String marca = req.getParameter("marca");
		String modelo = req.getParameter("modelo");
		StringBuilder formatModelo=new StringBuilder();
		String modeloFormateado;
		modeloFormateado=parteCadena(modelo);
		int i=0;
		resp.getWriter().println("El precio del producto en PCComponentes es de: " + buscaEnPcComponentes(modeloFormateado));
		resp.getWriter().println("enlace de la oferta: " + enlacePC);
		resp.getWriter().println("Para el producto: " + caracteristicasPC);
		System.out.println(modeloFormateado);
		
		
		buscarEnAmazon(req, resp, marca, modeloFormateado);
		for(int j=0; j<resultados.length;j++)
			extraerInformacion(resultados[j]);
			MobileInfo nuevo=new MobileInfo(null, caracteristicasPC,buscaEnPcComponentes(modeloFormateado),marca,null,enlacePC);
			mobiles.add(nuevo);
		 req.getSession().setAttribute("moviles", mobiles);
		 resp.sendRedirect("http://localhost:8888/MobileInfo.jsp");

	}
	
	
}

