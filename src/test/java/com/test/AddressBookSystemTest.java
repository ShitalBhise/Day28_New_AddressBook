package com.test;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;

import org.junit.Test;

import com.java.AddressBookException;
import com.java.AddressBookService;
import com.java.AddressBookService.IOService;

public class AddressBookSystemTest {
private static AddressBookService addressBookService;
	
	@BeforeClass
	public static void createAddressBookObj() {
		addressBookService = new AddressBookService();
		System.out.println("Welcome to the Address Book System");
	}
	
	@Test
	public void givenAddressBookDetails_WhenRetrieved_ShouldMachPersonsCount() throws AddressBookException {
		List<Contact> list = addressBookService.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void givenAddressBookDetails_WhenUpdated_ShouldSyncWithDB() throws AddressBookException {
		List<Contact> data = addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.updateDBRecord("Sana", "Ether");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Sana");
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void givenAddressBookDetails_WhenRetrieved_ForGivenRange_ShouldMatchPersonsCount() throws AddressBookException {
		List<PeContactrson> list = addressBookService.readAddressBookData(IOService.DB_IO, "2020-11-01", "2020-11-22");
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void givenAddressBookDetails_WhenRetrieved_ShouldReturnTotalNumberOfContacts() throws AddressBookException {
		Assert.assertEquals(1, addressBookService.readAddressBookData("Count", "SVP"));
	}
	
	@Test
	public void givenAddressBookDetails_WhenaddedNewcontact_ShouldSyncWithDB() throws AddressBookException {
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addNewContact("Sahil", "Bhide", "jaipur", "rajasten", "MH", "500039", "9581440658", "Sahil@gmail.com");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Sahil");
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void givenPersons_WhenAddedToDBUsingThread_ShouldMatchNumOfEntries() throws AddressBookException {
		Contact[] arrayOfContacts = {
				new Contact("Savi", "Kumar", "Shish", "KVD", "MH", "500012", "6032807811", "Savi@gmail.com"),
				new Contact("Sanu", "Sham", "satara", "JKV", "MH", "505001", "9234150756", "Sanu@gmail.com")
		};
		addressBookService.readAddressBookData(IOService.DB_IO);
		Instant start = Instant.now();
		addressBookService.addMultipleContacts(Arrays.asList(arrayOfContacts));
		Instant end = Instant.now();
		System.out.println("Duration with thread: "+Duration.between(start, end));
		Assert.assertEquals(3, addressBookService.countEntries(IOService.DB_IO));
	}
}
