package flowgrammer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SomeBean {
	Logger log = Logger.getLogger(SomeBean.class);
	
	public SomeBean() {
		log.info("SomeBean");
	}
}
