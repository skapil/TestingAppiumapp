package com.theranos.test.theranosios.base;

import java.util.HashMap;

import org.testng.annotations.Test;

public class TestDataID {
  
	public static HashMap<String,String> elementid = new HashMap<String,String>();
	public static HashMap<String,String> monthid = new HashMap<String,String>();
	public static HashMap<String,String> stateid = new HashMap<String,String>();
	    
    //Keeping the element ids with the name in hashmap
    public TestDataID(String os)
    {
        this.elementid = elementid;
        basicInfoData(os);        
        monthData();
        stateData();        
    }
    
    //State Data
    public HashMap<String,String> stateData()
    {
        stateid.put("AL", "Alabama");
		stateid.put("AK", "Alaska");
		stateid.put("AZ","Arizona");
		stateid.put("AR","Arkansas");
		stateid.put("CA","California");
		stateid.put("CO","Colorado");
		stateid.put("CT","Connecticut");
		stateid.put("DE","Delaware");
		stateid.put("FL","Florida");
		stateid.put("GA","Georgia");
		stateid.put("HI","Hawaii");
		stateid.put("ID","Idaho");
		stateid.put("IL","Illinois");
		stateid.put("IN","Indiana");
		stateid.put("IA","Iowa");
		stateid.put("KS","Kansas");
		stateid.put("KY","Kentucky");
		stateid.put("LA","Louisiana");
		stateid.put("ME","Maine");
		stateid.put("MD","Maryland");
		stateid.put("MA","Massachusetts");
		stateid.put("MI","Michigan");
		stateid.put("MN","Minnesota");
		stateid.put("MS","Mississippi");
		stateid.put("MO","Missouri");
		stateid.put("MT","Montana");
		stateid.put("NE","Nebraska");
		stateid.put("NV","Nevada");
		stateid.put("NH","New Hampshire");
		stateid.put("NJ","New Jersey");
		stateid.put("NM","New Mexico");
		stateid.put("NY","New York");
		stateid.put("NC","North Carolina");
		stateid.put("ND","North Dakota");
		stateid.put("OH","Ohio");
		stateid.put("OK","Oklahoma");
		stateid.put("OR","Oregon");
		stateid.put("PA","Pennsylvania");
		stateid.put("RI","Rhode Island");
		stateid.put("SC","South Carolina");
		stateid.put("SD","South Dakota");
		stateid.put("TN","Tennessee");
		stateid.put("TX","Texas");
		stateid.put("UT","Utah");
		stateid.put("VT","Vermont");
		stateid.put("VA","Virginia");
		stateid.put("WA","Washington");
		stateid.put("WV","West Virginia");
		stateid.put("WI","Wisconsin");
		stateid.put("WY","Wyoming");
		return stateid;
    }
    
    //Month Data
    public static HashMap<String,String> monthData()
    {
    	 monthid.put("1", "January");
         monthid.put("2", "February");
         monthid.put("3", "March");
         monthid.put("4", "April");
         monthid.put("5", "May");
         monthid.put("6", "June");
         monthid.put("7", "July");
         monthid.put("8", "August");
         monthid.put("9", "September");
         monthid.put("10", "October");
         monthid.put("11", "November");
         monthid.put("12", "December");
         return monthid;
    }
    
    //Basic Info Data
    public static HashMap<String,String> basicInfoData(String os)
    {
    	if(os.equalsIgnoreCase("ios"))
    	{
	    	elementid.put("FirstName","//window[1]/tableview[2]/cell[2]/textfield[1]");	
	        elementid.put("MiddleName","//window[1]/tableview[2]/cell[3]/textfield[1]");	
	        elementid.put("LastName","//window[1]/tableview[2]/cell[4]/textfield[1]");	
	        elementid.put("DateOfBirth","//window[1]/tableview[2]/cell[5]/textfield[1]");	
	        elementid.put("Gender","//window[1]/tableview[2]/cell[6]/text[1]");	
	        elementid.put("Male","//window[1]/tableview[2]/cell[6]/button[1]");	
	        elementid.put("Female","//window[1]/tableview[2]/cell[6]/button[2]");	
	        elementid.put("Email","//window[1]/tableview[2]/cell[8]/text[1]");	
	        elementid.put("Phone","//window[1]/tableview[2]/cell[9]/textfield[1]");	
	        elementid.put("Mailing-StreetLine1","//window[1]/tableview[2]/cell[11]/textfield[1]");	
	        elementid.put("Mailing-StreetLine2","//window[1]/tableview[2]/cell[12]/textfield[1]");	
	        elementid.put("Mailing-City","//window[1]/tableview[2]/cell[13]/textfield[1]");	
	        elementid.put("Mailing-State","//window[1]/tableview[2]/cell[14]/textfield[1]");	
	        elementid.put("Mailing-Zipcode","//window[1]/tableview[2]/cell[15]/textfield[1]");	
	        elementid.put("Billing-StreetLine1","//window[1]/tableview[2]/cell[18]/textfield[1]");	
	        elementid.put("Billing-StreetLine2","//window[1]/tableview[2]/cell[19]/textfield[1]");	
	        elementid.put("Billing-City","//window[1]/tableview[2]/cell[20]/textfield[1]");	
	        elementid.put("Billing-State","//window[1]/tableview[2]/cell[21]/textfield[1]");	
	        elementid.put("Billing-Zipcode","//window[1]/tableview[2]/cell[22]/textfield[1]");	
	        elementid.put("Same as mailling address","//window[1]/tableview[2]/cell[17]/button[1]");
    	}
    	else {
    		elementid.put("FirstName","com.theranos.me:id/basic_info_edit_first");	
	        elementid.put("MiddleName","com.theranos.me:id/basic_info_edit_middle");	
	        elementid.put("LastName","com.theranos.me:id/basic_info_edit_last");
	        elementid.put("Suffix", "com.theranos.me:id/basic_info_edit_suffix");
	        elementid.put("DateOfBirth","com.theranos.me:id/basic_info_edit_dob");
	        elementid.put("Male","com.theranos.me:id/basic_info_edit_gender_male_btn");	
	        elementid.put("Female","com.theranos.me:id/basic_info_edit_gender_female_btn");	
	        elementid.put("Email","com.theranos.me:id/basic_contact_email");	
	        elementid.put("Mobile","com.theranos.me:id/basic_contact_mobile_phone");
	        elementid.put("Home","com.theranos.me:id/basic_contact_mobile_phone");	
	        elementid.put("Work","com.theranos.me:id/basic_contact_mobile_phone");	
	        elementid.put("Mailing-StreetLine1","com.theranos.me:id/basic_edit_addr1");	
	        elementid.put("Mailing-StreetLine2","com.theranos.me:id/basic_edit_addr2");	
	        elementid.put("Mailing-City","com.theranos.me:id/basic_edit_addr_city");	
	        elementid.put("Mailing-State","//view[1]/window[2]/scroll[1]/linear[1]/linear[2]/linear[1]/spinner[1]");	
	        elementid.put("Mailing-Zipcode","com.theranos.me:id/basic_edit_addr_postal");	
	        elementid.put("Billing-StreetLine1","com.theranos.me:id/basic_edit_bill_addr1");	
	        elementid.put("Billing-StreetLine2","com.theranos.me:id/basic_edit_bill_addr2");	
	        elementid.put("Billing-City","com.theranos.me:id/basic_edit_bill_addr_city");	
	        elementid.put("Billing-State","//view[1]/window[2]/scroll[1]/linear[1]/linear[3]/linear[1]/linear[1]/spinner[1]");	
	        elementid.put("Billing-Zipcode","com.theranos.me:id/basic_edit_bill_addr_postal");	
	        elementid.put("Same as mailling address","com.theranos.me:id/basic_edit_bill_same");
	        elementid.put("ContactMethod","com.theranos.me:id/basic_contact_method");
    	}
    	 return elementid;
      }   
}
