package flowgrammer;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/flowgrammer/conf/ContextConfiguration.xml")
public class SomeBeanTest {

	DataSource dataSource;
	
	@Autowired
	private ApplicationContext context;
	@Before
	public void setUp() {
		dataSource = context.getBean("dataSource", DataSource.class);
	}
	
	@Test
	public void connectionTest() throws Exception {
		java.sql.Connection connection = dataSource.getConnection();
//		org.junit.Assert.assertThat(connection, org.hamcrest.CoreMatchers.is(1));
		connection.close();
		assertThat(connection, equalTo(connection));
	}
	
	
	
}
