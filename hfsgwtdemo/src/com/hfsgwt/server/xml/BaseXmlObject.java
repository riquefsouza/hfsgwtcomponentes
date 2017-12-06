package com.hfsgwt.server.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class BaseXmlObject extends BaseRespostaVazio {

	private String[] apelidos;

	@SuppressWarnings("rawtypes")
	private Class[] classes;

	@SuppressWarnings("rawtypes")
	private Class[] classesAtributo;

	private String[] camposAtributo;

	private String[] apelidosAtributo;

	public BaseXmlObject() {
		this.apelidos = new String[] { "root" };
		this.classes = null;
		this.classesAtributo = null;
		this.camposAtributo = null;
		this.apelidosAtributo = null;
	}

	public String getXML(Converter conv, Object objeto) {
		boolean bSaidaFormatada = true;
		String retorno = "<?xml version='1.0' encoding='UTF-8'?>";
		// XStream xstream = new XStream();
		XStream xstream = new XStream(new DomDriver());

		if (conv != null) {
			xstream.registerConverter(conv);
		}
		for (int i = 0; i < this.classes.length; i++) {
			xstream.alias(this.apelidos[i], this.classes[i]);
		}
		if (this.classesAtributo != null) {
			for (int j = 0; j < this.classesAtributo.length; j++) {
				xstream.aliasAttribute(this.classesAtributo[j],
						this.camposAtributo[j], this.apelidosAtributo[j]);
			}
		}

		if (objeto instanceof BaseXmlObject) {
			BaseXmlObject obj = (BaseXmlObject) objeto;
			obj.setApelidos(null);
			obj.setClasses(null);
			obj.setClassesAtributo(null);
			obj.setCamposAtributo(null);
			obj.setApelidosAtributo(null);
		}

		if (!bSaidaFormatada) {
			retorno += xstream.toXML(objeto).replaceAll("\n", "");
			retorno = retorno.replaceAll(">  <", "><");
		} else {
			retorno += "\n" + xstream.toXML(objeto);
		}
		return retorno;
	}

	public String getXML() {
		return this.getXML(null, this);
	}

	public Object fromXML(Converter conv, Object objeto, String xml) {
		// XStream xstream = new XStream();
		XStream xstream = new XStream(new DomDriver());

		if (conv != null) {
			xstream.registerConverter(conv);
		}
		for (int i = 0; i < this.classes.length; i++) {
			xstream.alias(this.apelidos[i], this.classes[i]);
		}
		if (this.classesAtributo != null) {
			for (int j = 0; j < this.classesAtributo.length; j++) {
				xstream.aliasAttribute(this.classesAtributo[j],
						this.camposAtributo[j], this.apelidosAtributo[j]);
			}
		}

		if (objeto instanceof BaseXmlObject) {
			BaseXmlObject obj = (BaseXmlObject) objeto;
			obj.setApelidos(null);
			obj.setClasses(null);
			obj.setClassesAtributo(null);
			obj.setCamposAtributo(null);
			obj.setApelidosAtributo(null);
		}
		
		return xstream.fromXML(xml);
	}
	
	public Object fromXML(String xml) {
		return this.fromXML(null, this, xml);
	}	
	
	/**
	 * @return Returns the apelidos.
	 */
	public String[] getApelidos() {
		return apelidos;
	}

	/**
	 * @param apelidos
	 *            The apelidos to set.
	 */
	public void setApelidos(String[] apelidos) {
		this.apelidos = apelidos;
	}

	/**
	 * @return Returns the apelidosAtributo.
	 */
	public String[] getApelidosAtributo() {
		return apelidosAtributo;
	}

	/**
	 * @param apelidosAtributo
	 *            The apelidosAtributo to set.
	 */
	public void setApelidosAtributo(String[] apelidosAtributo) {
		this.apelidosAtributo = apelidosAtributo;
	}

	/**
	 * @return Returns the camposAtributo.
	 */
	public String[] getCamposAtributo() {
		return camposAtributo;
	}

	/**
	 * @param camposAtributo
	 *            The camposAtributo to set.
	 */
	public void setCamposAtributo(String[] camposAtributo) {
		this.camposAtributo = camposAtributo;
	}

	/**
	 * @return Returns the classes.
	 */
	@SuppressWarnings("rawtypes")
	public Class[] getClasses() {
		return classes;
	}

	/**
	 * @param classes
	 *            The classes to set.
	 */
	@SuppressWarnings("rawtypes")
	public void setClasses(Class[] classes) {
		this.classes = classes;
	}

	/**
	 * @return Returns the classesAtributo.
	 */
	@SuppressWarnings("rawtypes")
	public Class[] getClassesAtributo() {
		return classesAtributo;
	}

	/**
	 * @param classesAtributo
	 *            The classesAtributo to set.
	 */
	@SuppressWarnings("rawtypes")
	public void setClassesAtributo(Class[] classesAtributo) {
		this.classesAtributo = classesAtributo;
	}
}