package mypack;

import org.junit.Test;

import name.luoyong.hibernate.envers.App;

public class AppTest {
	
	@Test
	public void app() {
		System.out.println("*****************  test begin  *********************");
		App.main(null);
		System.out.println("*****************  test end    *********************");
	}

}
