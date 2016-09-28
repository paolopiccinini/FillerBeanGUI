package org.mvc.filler;

import com.thoughtworks.xstream.XStream;
import from.credito.RecuperaInfoSCIPAFIRequest;

public class Tester {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		RecuperaInfoSCIPAFIRequest request = new RecuperaInfoSCIPAFIRequest();
		TheFiller filler = new TheFiller(request);
		filler.fill();
		System.out.println(new XStream().toXML(request));
	}
}
