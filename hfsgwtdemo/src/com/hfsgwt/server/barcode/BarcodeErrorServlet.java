package com.hfsgwt.server.barcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import org.apache.avalon.framework.CascadingException;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;

public class BarcodeErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 762462064053290408L;
	private Logger log = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);

    /**
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

        Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
        try {
            SAXTransformerFactory factory = (SAXTransformerFactory)TransformerFactory.newInstance();
            java.net.URL xslt = getServletContext().getResource("/WEB-INF/exception2svg.xslt");
            TransformerHandler thandler;
            if (xslt != null) {
                log.debug(xslt.toExternalForm());
                Source xsltSource = new StreamSource(xslt.toExternalForm());
                thandler = factory.newTransformerHandler(xsltSource);
                response.setContentType("image/svg+xml");
            } else {
                log.error("Exception stylesheet not found, sending back raw XML");
                thandler = factory.newTransformerHandler();
                response.setContentType("application/xml");
            }

            ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
            try {
                Result res = new javax.xml.transform.stream.StreamResult(bout);
                thandler.setResult(res);
                generateSAX(t, thandler);
            } finally {
                bout.close();
            }
    
            response.setContentLength(bout.size());
            response.getOutputStream().write(bout.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("Error in error servlet", e);
            throw new ServletException(e);
        }
    }

    private void generateSAX(Throwable t, ContentHandler handler) throws SAXException {
        if (t == null) {
            throw new NullPointerException("Throwable must not be null");
        }
        if (handler == null) {
            throw new NullPointerException("ContentHandler not set");
        }
    
        handler.startDocument();
        generateSAXForException(t, handler, "exception");
        handler.endDocument();
    }
    
    private void generateSAXForException(Throwable t, 
                ContentHandler handler, String elName) throws SAXException {
        AttributesImpl attr = new AttributesImpl();
        attr.addAttribute(null, "classname", "classname", "CDATA", t.getClass().getName());
        handler.startElement(null, elName, elName, attr);
        attr.clear();
        handler.startElement(null, "msg", "msg", attr);
        char[] chars = t.getMessage().toCharArray();
        handler.characters(chars, 0, chars.length);
        handler.endElement(null, "msg", "msg");
        
        if (t instanceof CascadingException) {
            Throwable nested = ((CascadingException)t).getCause();
            generateSAXForException(nested, handler, "nested");
        }
        
        handler.endElement(null, elName, elName);
    }
}
