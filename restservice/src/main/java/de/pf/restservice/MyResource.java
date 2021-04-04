package de.pf.restservice;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	
	
	public static void main(String[] args) {
		String hh = makeXML();
		
		System.out.println(hh);

	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@Path("/sum")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getSum(@QueryParam("n1") String num1, @QueryParam("n2") String num2) {
		List<String> retList = new ArrayList<String>();

		retList.add("Franz");
		retList.add("Hugo");
		retList.add("Heinz");
		retList.add("Judas");

		// return Integer.parseInt(num1) + Integer.parseInt(num2) + "";
		return retList;
	}

	@Path("/geo")
	@GET
	@Produces({ MediaType.APPLICATION_XML })
	public String getGeo(@QueryParam("n1") String Stadt) {

		return makeXML();

	}

	public static String makeXML() {
		String strResult = "";
		try {
			String namespaceURI = "http://www.w3.org/2000/xmlns/";
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element response = doc.createElementNS(
					   "http://abc.de/x/y/z", // namespace
					   "xls:Response" // node name including prefix
					);
			Element geocodeAdress = doc.createElementNS(
					   "http://abc.de/x/y/z", // namespace
					   "xls:GeocodeAdress" // node name including prefix
					);
			
			
			Element point = doc.createElementNS(
					   "http://abc.de/x/y/z", // namespace
					   "gml:Point" // node name including prefix
					);
			
			Element pos = doc.createElementNS(
					   "http://abc.de/x/y/z", // namespace
					   "gml:pos" // node name including prefix
					);
			pos.setAttribute("srsName", "EPSG:4326");
			pos.setTextContent("7.1006599 50.7358511");
			
			response.appendChild(geocodeAdress);
			geocodeAdress.appendChild(point);
			point.appendChild(pos);

			//<?xml version="1.0" encoding="UTF-8" standalone="no"?><tns:cmds xmlns:tns="http://abc.de/x/y/z"><tns:cmds/></tns:cmds>		
		
			


			doc.appendChild(response);

			// write the content into String

			DOMSource source = new DOMSource(doc);   
		    StringWriter writer = new StringWriter();
		    StreamResult resultWr = new StreamResult(writer);
		    TransformerFactory tFactory = TransformerFactory.newInstance();
		    Transformer transformerWr = tFactory.newTransformer();
		    transformerWr.transform(source,resultWr);
		    strResult = writer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strResult;
	}

}
