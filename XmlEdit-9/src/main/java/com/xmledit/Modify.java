package com.xmledit;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.upload.dto.Reader;

public class Modify {
	@SuppressWarnings("unchecked")
	public String Entry(File excel) throws UnirestException 
	{
		String message = "Records entered Successfully" ;
		ExcelRead e=new ExcelRead();
		List<Reader> data= new ArrayList<>();
		String	companyName="Rajkot Golden Logistics Pvt Ltd";
		StringBuilder xml=new StringBuilder("");
		String sxml="<ENVELOPE>\n"
				+ "<HEADER>\n"
				+ "<TALLYREQUEST>Import Data</TALLYREQUEST>\n"
				+ "</HEADER>\n"
				+ "<BODY>\n"
				+ "<IMPORTDATA>\n"
				+ "<REQUESTDESC>\n"
				+ "<REPORTNAME>Vouchers</REPORTNAME>\n"
				+ "<STATICVARIABLES>\n"
				+ "<SVCURRENTCOMPANY>"
				+ companyName
				+"</SVCURRENTCOMPANY>\n"
				+ "</STATICVARIABLES>\n"
				+ "</REQUESTDESC>\n"
				+ "<REQUESTDATA>\n"
				+ "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">\n";
		xml.append(sxml);
		String exml = "</TALLYMESSAGE>\n" 
				+"</REQUESTDATA>\n" 
				+"</IMPORTDATA>\n" 
				+"</BODY>\n" 
				+"</ENVELOPE>" 
				;
		String ledxml;

		try {
			Map<String, Object> map = e.getParent(excel);
			if(map.get("Error")==null) {
				data = (List<Reader>) map.get("List");
			}
			else {
				message = (String) map.get("Error");
				return message;
			}
			for (Reader reader : data) {
				String ledger = reader.getName();
				String parent = reader.getParent();
				ledxml = createLedger(ledger,parent);
				xml.append(ledxml);
			}

			xml.append(exml);
			String fxml= xml.toString();

			FileWriter myWriter = new FileWriter("src/main/resources/output.xml");
			myWriter.write(fxml);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
			Unirest.setTimeouts(0, 0);
			HttpResponse<String> response = Unirest.post("http://localhost:9000/")
					.field("file", new File("src/main/resources/output.xml"))
					.asString();
			System.err.println(response.getStatus()+" ** "+response.getStatusText());
		} catch (Exception ae) {
			JOptionPane.showMessageDialog(null, "An Error Occured");
			ae.printStackTrace();
		}

		File myObj = new File("src/main/resources/output.xml"); 

		if (myObj.delete()) { 
			System.out.println("Deleted the file: " + myObj.getName());
		} else {
			System.err.println("Failed to delete the file.");
		}  
		return message;
	}

	public String createLedger(String Ledger,String Parent){
		String ledgerxml= " <LEDGER NAME=\""+Ledger+"\" RESERVEDNAME=\"\">\n"
				+ "<PARENT>"+Parent+"</PARENT>\n"
				+ "<LANGUAGENAME.LIST>\n"
				+ "<NAME.LIST TYPE=\"String\">\n"
				+ "<NAME>"+Ledger+"</NAME>\n"
				+ "</NAME.LIST>\n"
				+ "</LANGUAGENAME.LIST>\n"
				+ "</LEDGER>\r\n"  ;
		return ledgerxml;
	}
	
	

}
