package com.anthavio.disquo.simulator;


//@Configuration
public class SpringConfiguration {
	/*
		@Value("${dqsim.h2db.port:9002}")
		private int h2port = 9002;

		@Value("${dqsim.h2db.dir}")
		private String h2dir = "work/h2db";

		@Bean
		public static PropertyPlaceholderConfigurer configurer() {
			PropertyPlaceholderConfigurer pph = new PropertyPlaceholderConfigurer();
			pph.setIgnoreResourceNotFound(true);
			pph.setLocation(new ClassPathResource("dqsim.properties"));
			return pph;
		}

		@Bean
		public Server H2Server() throws SQLException {
			return Server.createTcpServer("-tcp,-tcpAllowOthers,-tcpPort," + h2port + ",-baseDir," + h2dir);
		}

		@Bean
		public DataSource dataSource() {
			BoneCPDataSource ds = new BoneCPDataSource();
			ds.setJdbcUrl("jdbc:h2:tcp://localhost:" + h2port + "/dqsim");
			ds.setUsername("sa");
			ds.setPassword("");
			return ds;
		}
	*/
}
