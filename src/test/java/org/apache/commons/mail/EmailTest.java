package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.mail.Session;
import java.util.Properties;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {

	private static final String[] TEST_EMAILS = {"cs@gmail.com",
		"csa@um.edu", "abcd@gd.org"	};
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		email = null;
	}
	
	/*
	 * Test method for testing the addBcc() method in Email
	 * It passes an array of string emails to the method
	 * If the amount is equal to 3 then the emails were successfully added
	 */
	@Test
	public void testAddBcc() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3, email.getBccAddresses().size());
	}
	
	/*
	 * Test method for testing the addCc() method in Email
	 * It passes an array of string emails to the method
	 * If the amount is equal to 3 then the emails were successfully added
	 */
	@Test
	public void testAddCc() throws Exception {
		email.addCc(TEST_EMAILS);
		
		assertEquals(3, email.getCcAddresses().size());
	}
	/*
	 * Test method for testing addHeader()
	 * Passed 2 strings
	 * assert true if the key string is found
	 * this means it successfully added the header
	 */
	@Test
	public void testAddHeader() throws Exception {
		email.addHeader("Caitlin", "cda@hgj.com");
		assertTrue(email.getHeaders().containsKey("Caitlin"));
		assertTrue(email.getHeaders().containsValue("cda@hgj.com"));
	}
	/*
	 * Testing addHeader method with invalid data
	 * null key and valid value
	 * the test passes if this exception is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidAddHeader(){
		email.addHeader(null, "csad@m.com");
	}
	/*
	 * Test for the addReplyTo() method
	 * Adding one map to the hashmap
	 * if amount equals 1, it successfully added the reply to address
	 */
	@Test
	public void testAddReplyTo() throws Exception {
		email.addReplyTo("csa@m.com", "Caitlin");
		assertEquals(1, email.getReplyToAddresses().size());
	}
	
	/*
	 * Test getHostName()
	 * first sets host name og random string
	 * return if it is not null
	 */
	@Test
	public void testGetHostname() throws Exception {
		email.setHostName("ahostName");
		String testHost = email.getHostName();
		assertNotNull(testHost);
	}
	
	/*
	 * Test getHostName()
	 * There is no host name since session has not been defined
	 * expected to be null
	 */
	@Test
	public void testNullGetHostname() throws Exception {
		String testHost = email.getHostName();
		assertNull(testHost);
	}
	/*
	 * test getSentDate
	 * there no information for sent date
	 * expected to be null
	 */
	@Test
	public void testNullGetSentDate() throws Exception {
		Date d = email.getSentDate();
		assertNotNull(d.getTime());
		
	}
	/*
	 * Test getSentDate
	 * first adding data to sentDate
	 * returning that value
	 * value should Not equal Null
	 */
	@Test
	public void testGetSentDate() throws Exception {
		email.setSentDate(new Date());
		Date d = email.getSentDate();
		assertNotNull(d.getTime());
		
	}
	/*
	 * testing set from 
	 * the correct email is found
	 */
	@Test
	public void testSetFrom() throws Exception {
		email.setFrom("csa@h.com");
		assertEquals("csa@h.com", email.getFromAddress().getAddress());
	}
	/*
	 * test get socket connection
	 * socket session does not return null int
	 */
	@Test
	public void testGetSocketConnection() throws Exception {
		int i = email.getSocketConnectionTimeout();
		assertNotNull(i);
	}
	/*
	 * testing getMailSession
	 * successfully built
	 * 
	 */
	@Test
	public void testGetMailSession() throws Exception {
		email.setHostName("ahostName");
		email.setAuthentication("username", "password");
		email.getMailSession();
	}
	/*
	 * testing getMailSession
	 * exception expected since host name is null
	 */
	@Test(expected = EmailException.class)
	public void testNullHostNameGetMailSession() throws Exception {
		email.setAuthentication("username", "password");
		email.getMailSession();
	}
	/*
	 * successful build of mime message
	 */
	@Test
	public void testBuildMimeMessage()throws Exception {
        Properties properties = new Properties(System.getProperties());
        properties.setProperty(email.MAIL_HOST, "localhost");
        Session sess = Session.getInstance(properties);
        email.createMimeMessage(sess);
        email.setContent(sess, "text/plain");
        email.addBcc(TEST_EMAILS);
        email.addCc(TEST_EMAILS);
        email.addHeader("Header", "Value");
        email.setFrom("csh@gm.com");
        email.setHostName("localhost");
		email.buildMimeMessage();
	}
/*
 *  test number 2 for build mime message
 * Builds mime message successfully
 * tries again
 * exception expected since can only create mime message once	
 */
	@Test(expected = IllegalStateException.class)
	public void testTwoBuildMimeMessage()throws Exception {
        Properties properties = new Properties(System.getProperties());
        properties.setProperty(email.MAIL_HOST, "localhost");
        Session sess = Session.getInstance(properties);
        email.createMimeMessage(sess);
        email.setSubject("MySubject");
        email.addBcc(TEST_EMAILS);
        email.addCc(TEST_EMAILS);
        email.addHeader("Header", "Value");
        email.setFrom("csh@gm.com");
        email.setHostName("localhost");
		email.buildMimeMessage();
		email.buildMimeMessage();
	}
/*
 *  test number 3 for build mime message
 * missing from email address
 * throws exception	
 */
	@Test(expected = EmailException.class)
	public void testThreeBuildMimeMessage()throws Exception {
        Properties properties = new Properties(System.getProperties());
        properties.setProperty(email.MAIL_HOST, "localhost");
        Session sess = Session.getInstance(properties);
        email.createMimeMessage(sess);
        email.setSubject("MySubject");
        email.setContent(sess, null);
        email.addBcc(TEST_EMAILS);
        email.addCc(TEST_EMAILS);
        email.addHeader("Header", "Value");
        email.setHostName("localhost");
		email.buildMimeMessage();
	}
/*
 * test number 4 for build mime message
 * No bcc or cc list 
 * exception expected due to these missing
 */
	@Test(expected = EmailException.class)
	public void testFourBuildMimeMessage()throws Exception {
        Properties properties = new Properties(System.getProperties());
        properties.setProperty(email.MAIL_HOST, "localhost");
        Session sess = Session.getInstance(properties);
        email.createMimeMessage(sess);
        email.setSubject("MySubject");
        email.setContent(sess, null);
        email.setFrom("cs@mco.com");
        email.addHeader("Header", "Value");
        email.setHostName("localhost");
		email.buildMimeMessage();
	}
	
}
