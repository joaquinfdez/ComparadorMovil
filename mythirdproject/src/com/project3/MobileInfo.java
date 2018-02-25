package com.project3;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="mobileInfo")
@XmlType(propOrder={
		"asin",
		"marca",
		"modelo",
		"precio",
		"image",
		"enlace"
	})

public class MobileInfo implements Serializable{
	  private String asin		 = "";
	  private String marca		 = "";
	  private String modelo 	 = "";
	  private String precio 	 = "";
	  private String image = "";
	  private String enlace="";
	  public MobileInfo(){};
	  
	  MobileInfo(String asin_p, String nombre_p, String precio_p, String marca_p, String image_p,String enlace_p){
		    asin=asin_p;
		    modelo=nombre_p;
		    precio=precio_p;
		    image=image_p;
		    marca = marca_p;
		    enlace=enlace_p;
		  }
	  @XmlElement(required=true)
	  public String getEnlace(){
	    return enlace;
	  }
	  @XmlElement(required=true)
	  public String getAsin(){
	    return asin;
	  }
	  
	  @XmlElement(required=true)
	  public String getModelo(){
	    return modelo;
	  }
	  
	  @XmlElement(required=true)
	  public String getMarca(){
	    return marca;
	  }
	  
	  @XmlElement(required=true)
	  public String getPrecio(){
	    return precio;
	  }
	  
	  @XmlElement(required=true)
	  public String getImage(){
	    return image;
	  }
	  
	  public void setAsin(String asin_p){
	    this.asin=asin_p;
	  }
	  
	  public void setModelo(String modelo_p){
		    this.modelo=modelo_p;
		  }
	  
	  public void setMarca(String marca_p){
		    this.marca=marca_p;
		  }
	  public void setPrecio(String precio_p){
		    this.precio=precio_p;
		  }
	  
	  public void setImage(String image_p){
		    this.image=image_p;
		  }
	  public void setEnlace(String enlace_p){
		    this.enlace=enlace_p;
		  }
	  
	  
	  
	  
}
