package com.eshore.upsweb.util;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.mchange.v2.c3p0.ComboPooledDataSource;

	/**
	 * <pre>
	 * @date 2008-06-19
	 * @author guolp
	 * 
	 * @功能说明：
	 * CommDao 类是个独立类，用户只需为其提供 jdbc 驱动类包和 log4j 类包（通常项目本身就需要包含这些包，所以本类并不会给项目添加额外负担）
	 * 本类唯一需要的外部资源是 '/comm-dao-config.xml' 配置文件，并且该配置文件必须有形式如下的内容 下面的内容也不是完整需要，remark
	 * 部分仅为注释，可以省略，另外只需提供分支所指定的部分就可，比如 root.comm-dao-dbconn.obtain-mode =
	 * "container" 时只要提供 container 元素及其下内容，application 分支可剪除
	 * root.comm-dao-dbconn.container.container-product = "resin" 时只要提供 resin
	 * 元素及其下内容，tomcat/weblogic 分支可剪除 以此类推，CommDao只解析分支所指部分的配置内容 
	 * 
	 *	   &lt;?xml version="1.0" encoding="UTF-8"?> 
	 *	   &lt;root>
	 *		&lt;comm-dao-dbconn obtain-mode="application" remark="obtain-mode can only 'application', 'container', any other equals to 'application'">
	 *			&lt;container container-product="" remark="product can only 'tomcat', 'resin', 'weblogic', any other equals to 'weblogic'">
	 *				&lt;tomcat>
	 *					&lt;jndi-name>UP_DATASOURCE&lt;/jndi-name>
	 *					&lt;auto-commit>false&lt;/auto-commit>
	 *				&lt;/tomcat>
	 *				&lt;resin>
	 *					&lt;jndi-name>UP_DATASOURCE&lt;/jndi-name>
	 *					&lt;auto-commit>false&lt;/auto-commit>
	 *				&lt;/resin>
	 *				&lt;weblogic>
	 *					&lt;jndi-name>UP_DATASOURCE&lt;/jndi-name>			
	 *					&lt;factory>weblogic.jndi.WLInitialContextFactory&lt;/factory>
	 *					&lt;url>t3://127.0.0.1:7001&lt;/url>
	 *					&lt;user>weblogic&lt;/user>
	 *					&lt;password>weblogic&lt;/password>
	 *					&lt;auto-commit>false&lt;/auto-commit>
	 *				&lt;/weblogic>
	 *			&lt;/container>
	 *			&lt;application app-conn-mode="pool" remark="conn-mode can only 'jdbc', 'pool', any other equals to 'jdbc'">
	 *				&lt;jdbc>
	 *					&lt;driver>oracle.jdbc.driver.OracleDriver&lt;/driver>
	 *					&lt;url>jdbc:oracle:thin:@192.168.24.41:1521:hnjf&lt;/url>		
	 *					&lt;user>commweb&lt;/user>
	 *					&lt;password>commweb&lt;/password>
	 *					&lt;auto-commit>false&lt;/auto-commit>	
	 *				&lt;/jdbc>
	 *				&lt;pool>
	 *					&lt;driver>oracle.jdbc.driver.OracleDriver&lt;/driver>
	 *					&lt;url>jdbc:oracle:thin:@192.168.24.41:1521:hnjf&lt;/url>
	 *					&lt;user>commweb&lt;/user>
	 *					&lt;password>commweb&lt;/password>
	 *					&lt;auto-commit>false&lt;/auto-commit>
	 *					&lt;transaction-isolation>2&lt;/transaction-isolation>	
	 *					&lt;max-active>10&lt;/max-active>
	 *					&lt;min-idle>0&lt;/min-idle>
	 *					&lt;max-idle>10&lt;/max-idle>
	 *					&lt;pool-prepared-statements>true&lt;/pool-prepared-statements>
	 *					&lt;max-open-prepared-statements>100&lt;/max-open-prepared-statements>
	 *				&lt;/pool>
	 *			&lt;/application>
	 *		&lt;/comm-dao-dbconn>
	 *	  &lt;/root>
	 * 
	 * @版本更新列表
	 * 修改版本: 1.0.3
	 * 修改日期：2009-02-01
	 * 修改人 : guolp
	 * 修改说明：对来自 comm-dao-config.xml 中的数据库链接参数按需提供解密能力
	 * 复审人：
	 * 
	 * 修改版本: 1.0.2
	 * 修改日期：2008-12-30
	 * 修改人 : haimin
	 * 修改说明：增加addPageRulePreparedStatement分页的PreparedStatement方法，自动构造预编译语句，提高sql执行效率
	 * 复审人：
	 * 
	 * 修改版本: 1.0.1
	 * 修改日期：2008-11-13
	 * 修改人 : haimin
	 * 修改说明：增加getNextval方法，根据序列名得到序列值
	 * 复审人：
	 * 
	 * 修改版本: 1.0.0
	 * 修改日期：2008-06-19
	 * 修改人 : guolp
	 * 修改说明：形成初始版本
	 * 复审人：
	 * </pre>
	 */

	public class CommDao {
		private static final Log logger = LogFactory.getLog(CommDao.class);
		private static final String _xmlFilePath = "/comm-dao-config.xml";
		private static boolean init_result = false;

		private static CommDao instance = null; //new CommDao();

		private CommDao() {
			try {
				init();
				if (logger.isInfoEnabled()) {
					logger.info("init database connection parameters ok!");
					if (isContainerMode) {
						logger.info(String.format(
								"connection mode is: %s Container",
								containerProduct));
					} else {
						if (isAppConnPoolMode) {
							logger.info("connection mode is: pool application");
						} else if(isC3p0ConnPoolMode){
							logger.info("connection mode is: c3p0Pool application");
						} else {
							logger.info("connection mode is: jdbc application");
						}
					}
				}
				init_result = true;
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("init database connection parameters fail!", e);
				}
				init_result = false;
			}
		}

		public static CommDao getInstance() {
			if(instance == null){
				instance = new CommDao();
			}
			
			if(init_result){
				return instance;
			}

			if (logger.isErrorEnabled()) {
				logger.error("init database connection parameters fail!");
				logger.error("please correct database connection parameters and restart application !");
			}
			return null;
		}

		/**
		 * 函数功能: 网路中断或管理数据源的程序重启，数据库重启等会导致数据源失效或连接失效， 本函数负责消除这种意外，重新获取有效的数据库连接
		 * 
		 * @param interval -
		 *            尝试间隔，每次尝试失败后会当前线程会睡眠 interval 秒
		 * @param times -
		 *            尝试次数
		 * @return 超过指定次数尝试获取连接失败后返回 null ，否则返回得到的 Connection 连接对象
		 */
		public Connection restoreConnection(long interval, long times) {
			while (times-- > 0) {
				try {
					Connection conn = getConnection();
					if (conn != null) {
						if (logger.isInfoEnabled()) {
							logger.info("redo get database connection ok!");
						}
						return conn;
					}
					if (isContainerMode || (!isContainerMode && isAppConnPoolMode) || (!isContainerMode && isC3p0ConnPoolMode)) {
						datasource = null;
						context = null;
					}
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("get database connection fail!", e);
					}
				}
				Sleep(interval);
			}
			return null;
		}

		/**
		 * 函数功能: 获取一个 Connection 对象
		 * 
		 * @return Connection连接对象
		 */
		public Connection getConnection() {
			Connection conn = null;
			try {

				if (isContainerMode) {
					synchronized (instance) {
						if (datasource == null) datasource = lookupDataSource();
						conn = datasource.getConnection();
					}
				} else if (isAppConnPoolMode) {
					synchronized (instance) {
						if (datasource == null) datasource = getApplicationDatasource();
						conn = datasource.getConnection();
					}
				} else if (isC3p0ConnPoolMode){
					synchronized (instance) {
						if (datasource == null) datasource = getC3p0ApplicationDatasource();
						conn = datasource.getConnection();
					}
				}else {
					Class.forName(jdbc_driver).newInstance();
					conn = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password);
				}
				conn.setAutoCommit(isAutoCommit);
				if (logger.isInfoEnabled()) {
					if (isContainerMode) {
						if(logger.isDebugEnabled()){
							logger.debug("get connection from container ok!");
						}
					} else if (isAppConnPoolMode) {
						if(logger.isDebugEnabled()){
							logger.debug("get application connection from pool ok!");
						}	
					} else if (isC3p0ConnPoolMode){
						if(logger.isDebugEnabled()){
							logger.debug("get application connection from c3p0Pool ok!");
						}
					}else {
						if(logger.isDebugEnabled()){
							logger.debug("get application connection ok!");
						}
					}
				}
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					if (isContainerMode) {
						if(logger.isErrorEnabled()){
							logger.error("get connection from container fail!", e);
						}
					} else if (isAppConnPoolMode) {
						if(logger.isErrorEnabled()){
							logger.error("get application connection from pool fail!", e);
						}
					} else {
						if(logger.isErrorEnabled()){
							logger.error("get application connection fail!", e);
						}
					}
				}
			}
			return conn;
		}
		
		public static boolean commit(Connection conn) {
			try {
				if (conn != null)
					conn.commit();
				return true;
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("commit database fail!", e);
				}
				return false;
			}
		}

		public static boolean rollback(Connection conn) {
			try {
				if (conn != null)
					conn.rollback();
				return true;
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("rollback database fail!", e);
				}
				return false;
			}
		}

		/**
		 * 函数功能: 关闭 Connection 对象
		 * 
		 * @param conn -
		 *            要被关闭的数据库连接对象
		 */
		public static boolean closeConnection(Connection conn) {
			try {
				if (conn != null)
					conn.close();
				return true;
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("close database connection fail!", e);
				}
				return false;
			}
		}


		/**
		 * 函数功能: 判断一个 Connection 对象是否有效（由网路中断、DBMS关闭或其它原因导致）
		 * 
		 * @param conn -
		 *            Connection 对象
		 * @return 能完整正确执行常量查询语句返回 true， 否则返回 false
		 */
		public static boolean isValidConnection(Connection conn) {
			Statement stmt = null;
			ResultSet rlst = null;
			try {
				stmt = conn.createStatement();
				rlst = stmt.executeQuery("SELECT 1 FROM DUAL");
				return true;
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("test valid database connection fail!", e);
				}
				return false;
			} finally {
				closeResultSet(rlst);
				closeStatement(stmt);
			}
		}

		/**
		 * 函数功能: 构造分页 select 语句
		 * 
		 * @param sql -
		 *            select 查询语句
		 * @param pageNo -
		 *            页号
		 * @param pageSize -
		 *            页大小
		 * @return 返回能获取指定页号数据的 select 语句
		 */
		public static String addPageRule(String sql, int pageNo, int pageSize) {
			StringBuffer buf = new StringBuffer();
			buf.append("select * from (select rownum rownum_, row_.* from (");
			buf.append(sql);
			buf.append(") row_ where rownum <= ");
			buf.append(pageNo * pageSize);
			buf.append(") where rownum_ > ");
			buf.append((pageNo - 1) * pageSize);
			return buf.toString();
		}

		/**
		 * 函数功能: 构造分页 select 语句（注意：第一个pstmt设置的参数为结束记录数endNumber，第二个参数为起始记录数startNumber）<br>
		 * 分页语句形如：select * from (select rownum rownum_, row_.* from * (sql) row_
		 * where rownum <= ？) where rownum_ > * ？<br>
		 * 使用时需要设置起记录数和结束记录数，并使用PreparedStatement进行设置<br>
		 * PreparedStatement pstmt;<br>
		 * 其中paqeNo为页码，pageSize为每页记录数<br>
		 * startNumber=pageNo * pageSize;<br>
		 * endNumber=(pageNo - 1) * pageSize;<br>
		 * pstmt.setInt(1, endNumber);<br>
		 * pstmt.setInt(2, startNumber);<br>
		 * 
		 * @param sql -
		 *            select 查询语句
		 * @return 返回能获取指定页号数据的 select 的PreparedStatement语句
		 */
		public static String addPageRulePreparedStatement(String sql) {
			StringBuffer buf = new StringBuffer();
			buf.append("select * from (select rownum rownum_, row_.* from (");
			buf.append(sql);
			buf.append(") row_ where rownum <= ? ");
			buf.append(") where rownum_ > ? ");
			String pageSql = buf.toString();
			return pageSql;
		}

		/**
		 * 获取指定查询语句包含的结果数
		 * 
		 * @param select
		 * @param conn
		 * @return
		 * @throws Exception
		 */
		public static int getRecorderNumForSelect(String select, Connection conn)
				throws Exception {
			select = String.format(" SELECT count(1) FROM ( %s ) ", select);
			return CommDao.queryForInteger(conn, select);
		}

		// /////////////////////////////////////////////////////////////////////////////////
		//  
		// 下面的函数 closeConnection、closeStatement、closeResultSet 帮助关闭数据库资源；
		// 这4个函数做了非 null 判断，并且进行错误日志，可以帮助程序员省却很多代码。

		/**
		 * 函数功能: 关闭 Statement 对象
		 * 
		 * @param stmt -
		 *            要被关闭的语句对象
		 */
		public static void closeStatement(Statement stmt) {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("close database statement fail!", e);
				}
			}
		}

		/**
		 * 函数功能: 关闭 ResultSet 对象
		 * 
		 * @param rlst -
		 *            要被关闭的游标对象
		 */
		public static void closeResultSet(ResultSet rlst) {
			try {
				if (rlst != null)
					rlst.close();
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("close database result set fail!", e);
				}
			}
		}

		/**
		 * 函数功能: 关闭 CallableStatement 对象
		 * 
		 * @param proc -
		 *            要被关闭的存储过程对象
		 */
		public static void closeCallableStatement(CallableStatement proc) {
			try {
				if (proc != null)
					proc.close();
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("close database result set fail!", e);
				}
			}
		}

		/***************************************************************************
		 * 
		 * 数据库连接获取方式参数初始化
		 * 
		 **************************************************************************/
		// 通用设置
		private boolean isAutoCommit = false;

		// 是否容器模式,
		// true - 容器模式
		// false - 应用程序模式
		private boolean isContainerMode = false;
		private String containerProduct = null;
		private String jndi_name = null;

		// 应用程序模式下，是否连接池模式
		// true - 连接池模式
		// false - jdbc模式
		private boolean isAppConnPoolMode = false;
		private boolean isC3p0ConnPoolMode = false;

		// 应用程序模式下，jdbc模式所需要的参数
		private String jdbc_driver = null;
		private String jdbc_url = null;
		private String jdbc_user = null;
		private String jdbc_password = null;

		// 应用程序模式下，pool模式所需要的参数
		private String pool_driver = null;
		private String pool_url = null;
		private String pool_user = null;
		private String pool_password = null;
		private int pool_transaction_isolation = -1;
		private int pool_max_active = -1;
		private int pool_min_idle = -1;
		private int pool_max_idle = -1;
		private boolean pool_prepared_statements = false;
		private int pool_max_open_prepared_statements = -1;
		private boolean pool_removeAbandoned = false;
		private int pool_removeAbandonedTimeout = -1;
		private int pool_maxWait = -1;
		
		// C3P0连接池
		private String  c3p0_driverClass = null;
		private String  c3p0_jdbcUrl = null;
		private String  c3p0_user = null;
		private String  c3p0_password = null;
		private int     c3p0_minPoolSize = -1;
		private int     c3p0_maxPoolSize = -1;
		private int     c3p0_initialPoolSize = -1;
		private int     c3p0_maxIdleTime = -1;
		private int     c3p0_acquireIncrement = -1;
		private int     c3p0_acquireRetryAttempts = -1;
		private int     c3p0_acquireRetryDelay = -1;
		private boolean c3p0_testConnectionOnCheckin = false;
		private String  c3p0_automaticTestTable = null;
		private int     c3p0_idleConnectionTestPeriod = -1;
		private int     c3p0_checkoutTimeout = -1;
		

		// 容器模式下，weblogic模式所需要的参数
		private String weblogic_factory = null;
		private String weblogic_url = null;
		private String weblogic_user = null;
		private String weblogic_password = null;

		// 容器模式下，tomcat模式所需要的参数

		// 容器模式下，resin模式所需要的参数

		private void init() throws Exception {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = null;
			InputStream in = null;
			try {
				in = getResourceAsStream(_xmlFilePath);
				document = builder.parse(in);
				Element root = document.getDocumentElement();
				Element comm_dao_dbconn = (Element) root.getElementsByTagName("comm-dao-dbconn").item(0);

				String obtain_mode = comm_dao_dbconn.getAttribute("obtain-mode");
				obtain_mode = obtain_mode == null ? "" : obtain_mode.trim().toLowerCase();
				isContainerMode = "container".equalsIgnoreCase(obtain_mode);
				if (isContainerMode) {
					Element container = (Element) comm_dao_dbconn.getElementsByTagName("container").item(0);
					containerProduct = container.getAttribute("container-product").trim().toLowerCase();
					if ("tomcat".equals(containerProduct)) {
						initContainerTomcatJndi(container);
					} else if ("resin".equals(containerProduct)) {
						initContainerResinJndi(container);
					} else {
						initContainerWeblogicJndi(container);
					}
				} else {
					Element application = (Element) comm_dao_dbconn.getElementsByTagName("application").item(0);
					String app_conn_mode = application.getAttribute("app-conn-mode");
					app_conn_mode = app_conn_mode == null ? "" : app_conn_mode.trim();
					isAppConnPoolMode = "pool".equalsIgnoreCase(app_conn_mode);
					if (isAppConnPoolMode) {
						initAppPoolParameter(application);
					} else {
						isC3p0ConnPoolMode = "c3p0".equalsIgnoreCase(app_conn_mode);
						if(isC3p0ConnPoolMode){
							initC3p0Parameter(application);
						}else{
							initAppJdbcParameter(application);
						}
					}
				}
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
		
		
		public void initContainerTomcatJndi(Element container) throws Exception{
			Element tomcat = (Element) container.getElementsByTagName("tomcat").item(0);
			
			Element elm = (Element) tomcat.getElementsByTagName("jndi-name").item(0);
			String str = elm.getFirstChild().getNodeValue().trim();
			jndi_name = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) tomcat.getElementsByTagName("auto-commit").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			isAutoCommit = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
		}
		
		public void initContainerResinJndi(Element container) throws Exception{
			Element resin = (Element) container.getElementsByTagName("resin").item(0);
			
			Element elm = (Element) resin.getElementsByTagName("jndi-name").item(0);
			String str = elm.getFirstChild().getNodeValue().trim();
			jndi_name = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) resin.getElementsByTagName("auto-commit").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			isAutoCommit = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
		}
		
		public void initContainerWeblogicJndi(Element container) throws Exception{
			Element weblogic = (Element) container.getElementsByTagName("weblogic").item(0);
			
			Element elm = (Element) weblogic.getElementsByTagName("jndi-name").item(0);
			String str = elm.getFirstChild().getNodeValue().trim();
			jndi_name = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) weblogic.getElementsByTagName("factory").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			weblogic_factory = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) weblogic.getElementsByTagName("url").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			weblogic_url = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) weblogic.getElementsByTagName("user").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			weblogic_user = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) weblogic.getElementsByTagName("password").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			weblogic_password = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) weblogic.getElementsByTagName("auto-commit").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			isAutoCommit = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
		}
		
		public void initAppPoolParameter(Element application) throws Exception{
			Element pool = (Element) application.getElementsByTagName("pool").item(0);

			Element elm = (Element) pool.getElementsByTagName("driver").item(0);
			String str = elm.getFirstChild().getNodeValue().trim();
			pool_driver = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("url").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_url = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("user").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_user = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("password").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_password = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("auto-commit").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			isAutoCommit = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("transaction-isolation").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_transaction_isolation = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("max-active").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_max_active = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("min-idle").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_min_idle = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("max-idle").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_max_idle = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("pool-prepared-statements").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_prepared_statements = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("max-open-prepared-statements").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_max_open_prepared_statements = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("removeAbandoned").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_removeAbandoned = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("removeAbandonedTimeout").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_removeAbandonedTimeout = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("maxWait").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			pool_maxWait = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
		}
		
		public void initC3p0Parameter(Element application) throws Exception{
			Element pool = (Element) application.getElementsByTagName("c3p0").item(0);

			Element elm = (Element) pool.getElementsByTagName("driver").item(0);
			String str = elm.getFirstChild().getNodeValue().trim();
			c3p0_driverClass = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("url").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_jdbcUrl = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("user").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_user = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("password").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_password = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("minPoolSize").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_minPoolSize = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("maxPoolSize").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_maxPoolSize = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("initialPoolSize").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_initialPoolSize = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("maxIdleTime").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_maxIdleTime = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("acquireIncrement").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_acquireIncrement = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("acquireRetryAttempts").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_acquireRetryAttempts = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("acquireRetryDelay").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_acquireRetryDelay = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("testConnectionOnCheckin").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_testConnectionOnCheckin = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("automaticTestTable").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_automaticTestTable = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) pool.getElementsByTagName("idleConnectionTestPeriod").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_idleConnectionTestPeriod = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("checkoutTimeout").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			c3p0_checkoutTimeout = Integer.parseInt(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
			elm = (Element) pool.getElementsByTagName("auto-commit").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			isAutoCommit = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
			
		}
		
		public void initAppJdbcParameter(Element application) throws Exception{
			Element jdbc = (Element) application.getElementsByTagName("jdbc").item(0);
			
			Element elm = (Element) jdbc.getElementsByTagName("driver").item(0);
			String str = elm.getFirstChild().getNodeValue().trim();
			jdbc_driver = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) jdbc.getElementsByTagName("url").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			jdbc_url = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) jdbc.getElementsByTagName("user").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			jdbc_user = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) jdbc.getElementsByTagName("password").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			jdbc_password = isElmValEncrypted(elm) ?  decrypt(str) : str;
			
			elm = (Element) jdbc.getElementsByTagName("auto-commit").item(0);
			str = elm.getFirstChild().getNodeValue().trim();
			isAutoCommit = "true".equalsIgnoreCase(isElmValEncrypted(elm) ?  decrypt(str) : str);
		}

		/***************************************************************************
		 * 
		 * 上下文对象和数据源
		 * 
		 **************************************************************************/
		private static Context context = null;
		private static DataSource datasource = null;

		private DataSource lookupDataSource() {
			try {
				if (context == null) {
					if (containerProduct == null) {
						return null;
					}
					if ("weblogic".equals(containerProduct)) {
						Properties properties = new Properties();
						properties.put(Context.INITIAL_CONTEXT_FACTORY, weblogic_factory);
						properties.put(Context.PROVIDER_URL, weblogic_url);
						if (weblogic_user != null && !"".equals(weblogic_user)) {
							properties.put(Context.SECURITY_PRINCIPAL, weblogic_user);
							properties.put(Context.SECURITY_CREDENTIALS, weblogic_password == null ? "" : weblogic_password);
						}
					} else {
						context = new InitialContext();
					}
				}
				return (DataSource) context.lookup(jndi_name);
			} catch (NamingException e) {
				if (logger.isErrorEnabled()) {
					logger.error("JNDI[" + jndi_name + "] search fail！", e);
				}
				return null;
			}
		}

		/**
		 * 初始化一个DBCP的DataSource
		 * 
		 * @return
		 */
		private DataSource getApplicationDatasource() {
			BasicDataSource dbcp = new BasicDataSource();
			dbcp.setDriverClassName(pool_driver);
			dbcp.setUrl(pool_url);
			dbcp.setUsername(pool_user);
			dbcp.setPassword(pool_password);
			dbcp.setDefaultAutoCommit(isAutoCommit);
			dbcp.setDefaultTransactionIsolation(pool_transaction_isolation);
			dbcp.setMaxActive(pool_max_active);
			dbcp.setMinIdle(pool_min_idle);
			dbcp.setMaxIdle(pool_max_idle);
			dbcp.setMaxOpenPreparedStatements(pool_max_open_prepared_statements);
			dbcp.setPoolPreparedStatements(pool_prepared_statements);
			dbcp.setRemoveAbandoned(pool_removeAbandoned);
			dbcp.setRemoveAbandonedTimeout(pool_removeAbandonedTimeout);
			dbcp.setMaxWait(pool_maxWait);
			return dbcp;
		}
		
		/**
		 * 初始化一个C3P0的DataSource
		 * @throws PropertyVetoException 
		 */
		private DataSource getC3p0ApplicationDatasource() throws PropertyVetoException {
			ComboPooledDataSource c3p0 = new ComboPooledDataSource("UPSWEBC3P0");
			c3p0.setDriverClass(c3p0_driverClass);
			c3p0.setJdbcUrl(c3p0_jdbcUrl);
			c3p0.setUser(c3p0_user);
			c3p0.setPassword(c3p0_password);
			c3p0.setMinPoolSize(c3p0_minPoolSize);
			c3p0.setMaxPoolSize(c3p0_maxPoolSize);
			c3p0.setInitialPoolSize(c3p0_initialPoolSize);
			c3p0.setMaxIdleTime(c3p0_maxIdleTime);
			c3p0.setAcquireIncrement(c3p0_acquireIncrement);
			c3p0.setAcquireRetryAttempts(c3p0_acquireRetryAttempts);
			c3p0.setAcquireRetryDelay(c3p0_acquireRetryDelay);
			c3p0.setTestConnectionOnCheckin(c3p0_testConnectionOnCheckin);
			c3p0.setAutomaticTestTable(c3p0_automaticTestTable);
			c3p0.setIdleConnectionTestPeriod(c3p0_idleConnectionTestPeriod);
			c3p0.setCheckoutTimeout(c3p0_checkoutTimeout);
			c3p0.setAutoCommitOnClose(isAutoCommit);
			return c3p0;
		}

		/***************************************************************************
		 * 
		 * 查询助手函数
		 * 
		 **************************************************************************/
		/**
		 * 根据指定的sql语句和sql参数列表，执行sql语句
		 * 
		 * @param sql
		 *            sql语句
		 * @param args
		 *            sql参数列表
		 * @return 执行成功，返回true，否则返回false
		 */
		public static boolean execute(Connection conn, final String sql,
				final Object... args) {
			if (logger.isDebugEnabled()) {
				logger.debug("execute " + formatSql(sql, args));
			}

			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(sql);
				prepareStatement(ps, args);
				return ps.execute();
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("execute error, " + formatSql(sql, args), e);
				}
				throw new RuntimeException(e);
			} finally {
				CommDao.closeStatement(ps);
			}
		}

		/**
		 * 根据指定的sql语句和参数列表，执行查询后，第一行第一列的整数值
		 */
		public static Integer queryForInteger(Connection conn, final String sql,
				final Object... args) {
			return (Integer) queryScalar(conn, sql, Integer.class, args);
		}

		/**
		 * 根据指定的sql语句和参数列表，执行查询后，第一行第一列的长整数值
		 */
		public static Long queryForLong(Connection conn, final String sql,
				final Object... args) {
			return (Long) queryScalar(conn, sql, Long.class, args);
		}

		/**
		 * 根据指定的sql语句和参数列表，执行查询后，第一行第一列的字符串
		 */
		public static String queryForString(Connection conn, final String sql,
				final Object... args) {
			return (String) queryScalar(conn, sql, String.class, args);
		}

		/**
		 * 根据指定的sql语句和参数列表，执行查询后，获取第一行第一列的标量值
		 * 
		 * @param sql
		 *            sql语句
		 * @param clazz
		 *            返回值的类型
		 * @param args
		 *            参数列表
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Object queryScalar(Connection conn, final String sql,
				final Class clazz, final Object... args) {
			if (logger.isDebugEnabled()) {
				System.out.println("query>>> " + formatSql(sql, args));
				logger.debug("query " + formatSql(sql, args));
			}

			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				prepareStatement(ps, args);
				rs = ps.executeQuery();
				if (rs.next()) {
					if (clazz == int.class || clazz == Integer.class) {
						return Integer.valueOf(rs.getInt(1));
					}
					if (clazz == String.class || clazz == char.class
							|| clazz == Character.class) {
						return rs.getString(1);
					}
					if (clazz == Long.class || clazz == long.class) {
						return Long.valueOf(rs.getLong(1));
					}
					int sqlType = CommDao.javaTypeToSqlParameterType(clazz);
					return CommDao.getResultSetValue(rs, 1, sqlType);
				}
				return null;
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("query error, " + formatSql(sql, args), e);
				}
				throw new RuntimeException(e);
			} finally {
				CommDao.closeResultSet(rs);
				CommDao.closeStatement(ps);
			}
		}

		/**
		 * 根据指定的sql语句和sql参数列表，执行查询后，返回Map key为columnName, value为值
		 * 
		 * @param sql
		 * @param args
		 * @return
		 */
		public static Map<String, Object> queryForMap(Connection conn,
				final String sql, final Object... args) {
			return queryForMap(conn, sql, false, args);
		}

		/**
		 * 根据指定的sql语句和sql参数列表，执行查询后，返回Map key
		 * 如果transColumnName为true，key为column名去掉下划线后的首字母小写，大小写混排的字符串,相当于bean的property
		 * name; 否则key为列名 value为其值
		 * 
		 * @param sql
		 * @param transColumnName
		 *            是否转换列名
		 * @param args
		 * @return 如果查询结果行数>=1，返回第一行的数据；否则返回 null
		 * @throws SQLException
		 */
		public static Map<String, Object> queryForMap(Connection conn,
				final String sql, final boolean transColumnName,
				final Object... args) {
			if (logger.isDebugEnabled()) {
				logger.debug("query  " + formatSql(sql, args));
			}
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				prepareStatement(ps, args);
				rs = ps.executeQuery();
				if (rs.next()) {
					ResultSetMetaData meta = rs.getMetaData();
					return generateMap(rs, meta, transColumnName);
				}
				return null;
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("query error, " + formatSql(sql, args), e);
				}
				throw new RuntimeException(e);
			} finally {
				CommDao.closeResultSet(rs);
				CommDao.closeStatement(ps);
			}
		}

		/**
		 * 根据指定的sql语句和sql参数列表，执行查询后，返回List 返回的list的元素为一个Map, key为columnName, value为值
		 * 
		 * @param sql
		 * @param args
		 * @return
		 */
		public static List<Map<String, Object>> queryForList(Connection conn,
				final String sql, final Object... args) {
			return queryForList(conn, sql, false, args);
		}

		/**
		 * 根据指定的sql语句和sql参数列表，执行查询后，返回List 返回的list的元素为map key
		 * 如果transColumnName为true，key为column名去掉下划线后的首字母小写，大小写混排的字符串,相当于bean的property
		 * name; 否则key为列名 value为其值
		 * 
		 * @param sql
		 * @param transColumnName
		 *            是否转换列名
		 * @param args
		 * @return list
		 * @throws SQLException
		 */
		public static List<Map<String, Object>> queryForList(Connection conn,
				final String sql, boolean transColumnName, final Object... args) {
			if (logger.isDebugEnabled()) {
				logger.debug("query " + formatSql(sql, args));
			}

			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				prepareStatement(ps, args);
				rs = ps.executeQuery();
				ResultSetMetaData meta = rs.getMetaData();
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				while (rs.next()) {
					Map<String, Object> map = generateMap(rs, meta, transColumnName);
					result.add(map);
				}
				return result;
			} catch (SQLException e) {
				if (logger.isErrorEnabled()) {
					logger.error("query error, " + formatSql(sql, args), e);
				}
				throw new RuntimeException(e);
			} finally {
				CommDao.closeResultSet(rs);
				CommDao.closeStatement(ps);
			}
		}

		/**
		 * 将sql及参数列表格式化
		 * 
		 * @param sql
		 * @param args
		 * @return
		 */
		private static String formatSql(String sql, Object... args) {
			StringBuilder sb = new StringBuilder();
			sb.append("sql : ").append(sql);
			if (args != null) {
				int index = 0;
				for (int len = args.length; index < len; index++) {
					Object value = args[index];
					if (index != 0) {
						sb.append(", ");
					} else {
						sb.append("   parameters: [");
					}
					sb.append(value);
				}
				if (index != 0) {
					sb.append("]");
				}
			}
			return sb.toString();
		}

		/**
		 * 将参数
		 * 
		 * @param ps
		 * @param args
		 * @throws SQLException
		 */
		private static void prepareStatement(PreparedStatement ps,
				final Object... args) throws SQLException {
			if (args == null) {
				return;
			}
			for (int index = 0, len = args.length; index < len; index++) {
				Object value = args[index];
				CommDao.setParameterValue(ps, index + 1, value);
			}
		}

		/**
		 * 从ResultSet中取值，将当前行的数据转为一个map 返回的map key为column名去掉下划线后的首字母小写，大小写混排的字符串
		 * value为其值
		 */
		private static Map<String, Object> generateMap(ResultSet rs,
				ResultSetMetaData meta, boolean transColumnName)
				throws SQLException {
			int columnCount = meta.getColumnCount();
			Map<String, Object> map = new LinkedHashMap<String, Object>(columnCount);
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				String columnName = CommDao.lookupColumnName(meta, columnIndex);
				int sqlType = meta.getColumnType(columnIndex);
				String key = transColumnName ? CommDao
						.convertColumnNameToPropertyName(columnName) : columnName;
				Object value = CommDao.getResultSetValue(rs, columnIndex, sqlType);
				map.put(key, value);
			}
			return map;
		}

		/**
		 * 取得序列的序列的nextval值
		 * 
		 * @param sequenceName
		 *            序列名
		 * @param conn
		 *            数据库连接
		 * @return 序列值
		 * @throws Exception
		 */
		public static long getNextval(String sequenceName, Connection conn)
				throws SQLException {
			String sql = " select " + sequenceName + ".nextval from dual ";
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					return rs.getLong(1);
				}
				return 0;
			} catch (SQLException ex) {
				if (logger.isErrorEnabled()) {
					logger.error("数据库存取异常， sql:" + sql);
				}
				throw ex;
			} finally {
				CommDao.closeResultSet(rs);
				CommDao.closeStatement(stmt);
			}
		}
		
		/**
		 * 取得系统时间
		 * 
		 * @param conn
		 *            数据库连接
		 * @return 系统时间
		 * @throws Exception
		 */
		public static Date getSysDate(Connection conn)
				throws SQLException {
			String sql = " select  sysdate from dual ";
			Statement stmt = null;
			ResultSet rs = null;
			Timestamp timestamp = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					timestamp = rs.getTimestamp(1);
				}
			} catch (SQLException ex) {
				if (logger.isErrorEnabled()) {
					logger.error("数据库存取异常， sql:" + sql);
				}
				throw ex;
			} finally {
				CommDao.closeResultSet(rs);
				CommDao.closeStatement(stmt);
			}
			return timestamp;
		}

		/***************************************************************************
		 * 
		 * JDBC数据库工具
		 * 
		 **************************************************************************/
		public static final int TYPE_UNKNOWN = Integer.MIN_VALUE;

		@SuppressWarnings("unchecked")
		private static Map<Class, Integer> javaTypeToSqlTypeMap = new HashMap<Class, Integer>();

		static {
			javaTypeToSqlTypeMap.put(boolean.class, Integer.valueOf(Types.BOOLEAN));
			javaTypeToSqlTypeMap.put(Boolean.class, Integer.valueOf(Types.BOOLEAN));
			javaTypeToSqlTypeMap.put(byte.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(Byte.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(short.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(Short.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(int.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(Integer.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(long.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(Long.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(BigInteger.class, Integer.valueOf(Types.INTEGER));
			javaTypeToSqlTypeMap.put(float.class, Integer.valueOf(Types.DECIMAL));
			javaTypeToSqlTypeMap.put(Float.class, Integer.valueOf(Types.DECIMAL));
			javaTypeToSqlTypeMap.put(double.class, Integer.valueOf(Types.DECIMAL));
			javaTypeToSqlTypeMap.put(Double.class, Integer.valueOf(Types.DECIMAL));
			javaTypeToSqlTypeMap.put(BigDecimal.class, Integer.valueOf(Types.DECIMAL));
			javaTypeToSqlTypeMap.put(java.sql.Date.class, Integer.valueOf(Types.DATE));
			javaTypeToSqlTypeMap.put(java.sql.Time.class, Integer.valueOf(Types.TIME));
			javaTypeToSqlTypeMap.put(java.sql.Timestamp.class, Integer.valueOf(Types.TIMESTAMP));
			javaTypeToSqlTypeMap.put(Blob.class, Integer.valueOf(Types.BLOB));
			javaTypeToSqlTypeMap.put(Clob.class, Integer.valueOf(Types.CLOB));
		}

		/**
		 * 根据参数值和索引，将参数设置到PreparedStatement
		 * 
		 * @param ps
		 * @param paramIndex
		 * @param inValue
		 * @throws SQLException
		 */
		@SuppressWarnings("unchecked")
		public static void setParameterValue(PreparedStatement ps, int paramIndex,
				final Object inValue) throws SQLException {
			Class javaType = (inValue != null) ? inValue.getClass() : null;
			int sqlType = (inValue != null) ? javaTypeToSqlParameterType(javaType) : Types.NULL;
			if (logger.isTraceEnabled()) {
				String message = String.format(
					"Setting SQL statement parameter value: column index %d, parameter value [%s], value class [%s], sql type [%s]",
					paramIndex, inValue, (javaType != null) ? javaType.getName() : "null",
					sqlType == CommDao.TYPE_UNKNOWN ? "unknown" : Integer.toString(sqlType));
				logger.trace(message);
			}
			if (inValue == null) {
				boolean useSetObject = false;
				try {
					DatabaseMetaData dbmd = ps.getConnection().getMetaData();
					String databaseProductName = dbmd.getDatabaseProductName();
					String jdbcDriverName = dbmd.getDriverName();
					if (databaseProductName.startsWith("Informix") || jdbcDriverName.startsWith("Apache Derby Embedded")) {
						useSetObject = true;
					} else if (databaseProductName.startsWith("DB2")) {
						sqlType = Types.VARCHAR;
					}
				} catch (Throwable ex) {
					if (logger.isDebugEnabled()) {
						logger.debug("Could not check database or driver name", ex);
					}
				}
				if (useSetObject) {
					ps.setObject(paramIndex, null);
				} else {
					ps.setNull(paramIndex, sqlType);
				}
			} else { // inValue != null
				if (sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR || (sqlType == Types.CLOB && isStringValue(javaType))) {
					if (inValue instanceof String) {
						ps.setString(paramIndex, (String) inValue);
					} else {
						ps.setString(paramIndex, inValue.toString());
					}
				} else if (sqlType == Types.INTEGER) {
					ps.setLong(paramIndex, ((Number) inValue).longValue());
				} else if (sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) {
					if (inValue instanceof BigDecimal) {
						ps.setBigDecimal(paramIndex, (BigDecimal) inValue);
					} else {
						ps.setObject(paramIndex, inValue, sqlType);
					}
				} else if (sqlType == Types.DATE) {
					if (inValue instanceof java.util.Date) {
						if (inValue instanceof java.sql.Date) {
							ps.setDate(paramIndex, (java.sql.Date) inValue);
						} else {
							ps.setDate(paramIndex, new java.sql.Date(((java.util.Date) inValue).getTime()));
						}
					} else if (inValue instanceof Calendar) {
						Calendar cal = (Calendar) inValue;
						ps.setDate(paramIndex, new java.sql.Date(cal.getTime().getTime()), cal);
					} else {
						ps.setObject(paramIndex, inValue, sqlType);
					}
				} else if (sqlType == Types.TIME) {
					if (inValue instanceof java.util.Date) {
						if (inValue instanceof java.sql.Time) {
							ps.setTime(paramIndex, (java.sql.Time) inValue);
						} else {
							ps.setTime(paramIndex, new java.sql.Time(((java.util.Date) inValue).getTime()));
						}
					} else if (inValue instanceof Calendar) {
						Calendar cal = (Calendar) inValue;
						ps.setTime(paramIndex, new java.sql.Time(cal.getTime().getTime()), cal);
					} else {
						ps.setObject(paramIndex, inValue, sqlType);
					}
				} else if (sqlType == Types.TIMESTAMP) {
					if (inValue instanceof java.util.Date) {
						if (inValue instanceof java.sql.Timestamp) {
							ps.setTimestamp(paramIndex, (java.sql.Timestamp) inValue);
						} else {
							ps.setTimestamp(paramIndex, new java.sql.Timestamp(((java.util.Date) inValue).getTime()));
						}
					} else if (inValue instanceof Calendar) {
						Calendar cal = (Calendar) inValue;
						ps.setTimestamp(paramIndex, new java.sql.Timestamp(cal.getTime().getTime()), cal);
					} else {
						ps.setObject(paramIndex, inValue, Types.TIMESTAMP);
					}
				} else if (sqlType == CommDao.TYPE_UNKNOWN) {
					if (isStringValue(javaType)) {
						ps.setString(paramIndex, inValue.toString());
					} else if (isDateValue(javaType)) {
						ps.setTimestamp(paramIndex, new java.sql.Timestamp(((java.util.Date) inValue).getTime()));
					} else if (inValue instanceof Calendar) {
						Calendar cal = (Calendar) inValue;
						ps.setTimestamp(paramIndex, new java.sql.Timestamp(cal.getTime().getTime()));
					} else {
						// Fall back to generic setObject call without SQL type specified.
						ps.setObject(paramIndex, inValue);
					}
				} else {
					// Fall back to generic setObject call with SQL type specified.
					ps.setObject(paramIndex, inValue, sqlType);
				}
			}
		}

		/**
		 * 由Java Type得到sql Type
		 * 
		 * @param javaType
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static int javaTypeToSqlParameterType(Class javaType) {
			Integer sqlType = javaTypeToSqlTypeMap.get(javaType);
			if (sqlType != null) {
				return sqlType.intValue();
			}
			if (Number.class.isAssignableFrom(javaType)) {
				return Types.NUMERIC;
			}
			if (isStringValue(javaType)) {
				return Types.VARCHAR;
			}
			if (isDateValue(javaType) || Calendar.class.isAssignableFrom(javaType)) {
				return Types.TIMESTAMP;
			}
			return TYPE_UNKNOWN;
		}

		/**
		 * Check whether the given value can be treated as a String value.
		 */
		@SuppressWarnings("unchecked")
		private static boolean isStringValue(Class inValueType) {
			// Consider any CharSequence (including JDK 1.5's StringBuilder) as
			// String.
			return (CharSequence.class.isAssignableFrom(inValueType) || StringWriter.class
					.isAssignableFrom(inValueType));
		}

		/**
		 * Check whether the given value is a <code>java.util.Date</code> (but not
		 * one of the JDBC-specific subclasses).
		 */
		@SuppressWarnings("unchecked")
		private static boolean isDateValue(Class inValueType) {
			return (java.util.Date.class.isAssignableFrom(inValueType) && !(java.sql.Date.class
					.isAssignableFrom(inValueType)
					|| java.sql.Time.class.isAssignableFrom(inValueType) || java.sql.Timestamp.class
					.isAssignableFrom(inValueType)));
		}

		/**
		 * 在ResultSetMetaData中，查找指定列的列名
		 * 
		 * @param resultSetMetaData
		 * @param columnIndex
		 * @return
		 * @throws SQLException
		 */
		public static String lookupColumnName(ResultSetMetaData resultSetMetaData,
				int columnIndex) throws SQLException {
			String name = resultSetMetaData.getColumnLabel(columnIndex);
			if (name == null || name.length() < 1) {
				name = resultSetMetaData.getColumnName(columnIndex);
			}
			return name;
		}

		/**
		 * 将含有下划线的列名，使用camel命名法将其转换成相应的Java bean的属性名 例如，logon_name将被转换成logonName
		 * 
		 * @param name
		 *            要转换的列名
		 * @return 转换后的属性名
		 */
		public static String convertColumnNameToPropertyName(String name) {
			if (name == null || name.length() < 1) {
				return "";
			}
			StringBuilder result = new StringBuilder();
			boolean nextIsUpper = false;
			if (name.length() > 1 && name.substring(1, 2).equals("_")) {
				result.append(name.substring(0, 1).toUpperCase());
			} else {
				result.append(name.substring(0, 1).toLowerCase());
			}
			for (int i = 1, len = name.length(); i < len; i++) {
				char c = name.charAt(i);
				if (c == '_') {
					nextIsUpper = true;
					continue;
				}
				if (nextIsUpper) {
					result.append(Character.toUpperCase(c));
					nextIsUpper = false;
				} else {
					result.append(Character.toLowerCase(c));
				}

			}
			return result.toString();
		}

		/**
		 * 根据sqlType和索引，从ResultSet中取得值
		 * 
		 * @param rs
		 * @param columnIndex
		 * @param sqlType
		 * @return
		 * @throws SQLException
		 */
		public static Object getResultSetValue(ResultSet rs, int columnIndex,
				int sqlType) throws SQLException {
			if (sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR) {
				return rs.getString(columnIndex);
			}
			Object obj = rs.getObject(columnIndex);
			if (obj == null) {
				return null;
			}
			if (obj instanceof Blob) {
				return rs.getBytes(columnIndex);
			}
			if (obj instanceof Clob) {
				return rs.getString(columnIndex);
			}
			if (obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
				return rs.getTimestamp(columnIndex);
			}
			if (obj.getClass().getName().startsWith("oracle.sql.DATE")) {
				String metaDataClassName = rs.getMetaData().getColumnClassName(
						columnIndex);
				if ("java.sql.Timestamp".equals(metaDataClassName)
						|| "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
					return rs.getTimestamp(columnIndex);
				}
				return rs.getDate(columnIndex);
			}
			if (obj instanceof java.sql.Date) {
				if ("java.sql.Timestamp".equals(rs.getMetaData()
						.getColumnClassName(columnIndex))) {
					return rs.getTimestamp(columnIndex);
				}
			}
			return obj;
		}

		/***************************************************************************
		 * 
		 * SQL脚本导入器, 构建测试环境时使用
		 * 
		 * @throws SQLException
		 * 
		 **************************************************************************/
		public static void execute(Connection conn, String fileEncoding,
				List<String> scripts) throws Exception {
			if (scripts == null || scripts.size() == 0) {
				return;
			}
			Statement stmt = null;
			try {
				fileEncoding = fileEncoding == null ? "UTF-8" : fileEncoding;
				stmt = conn.createStatement();
				Iterator<String> iter = scripts.iterator();
				while (iter.hasNext()) {
					String scriptFile = iter.next();
					importScript(scriptFile, stmt, fileEncoding);
				}
			} finally {
				CommDao.closeStatement(stmt);
			}
		}

		private static void importScript(String importFile, Statement statement,
				String fileEncoding) throws IOException {
			if (logger.isInfoEnabled()) {
				logger.info("Executing import script: " + importFile);
			}

			BufferedReader reader = null;
			try {
				fileEncoding = fileEncoding == null ? "UTF-8" : fileEncoding;
				reader = new BufferedReader(new InputStreamReader(
						getResourceAsStream(importFile), fileEncoding));
				StringBuilder buff = new StringBuilder();
				String line = null;
				String sql = null;
				int pos = 0;
				int commentStart = 0;
				int commentEnd = 0;
				while ((line = reader.readLine()) != null) {
					try {
						if (line.length() == 0 || line.startsWith("--")
								|| line.startsWith("//")) {
							continue;
						}
						buff.append(line).append('\n');
						commentStart = buff.indexOf("/*");
						commentEnd = buff.indexOf("*/");
						if (commentStart >= 0) {
							if (commentEnd >= 0) { // 注释结束, 删除注释
								buff.delete(commentStart, commentEnd + 2);
							} else { // 多行注释未结束,继续读取下一行
								continue;
							}
						}
						pos = buff.indexOf(";");
						if (pos == -1) {
							continue;
						}
						sql = buff.substring(0, pos).trim();
						buff.delete(0, pos + 1);
						if (logger.isTraceEnabled()) {
							logger.trace("excute sql : \n" + sql);
						}
						statement.execute(sql);
					} catch (SQLException e) {
						if (logger.isErrorEnabled()) {
							logger.error(
									"import script execution error! sql is :\n"
											+ sql, e);
						}
					}
				}
			} finally {
				if (reader != null) {
					reader.close();
				}

			}
		}

		/***************************************************************************
		 * 
		 * 助手函数区
		 * 
		 **************************************************************************/

		/**
		 * 函数功能: 让运行本函数的线程睡眠指定时间
		 * 
		 * @param seconds -
		 *            秒数
		 */
		@SuppressWarnings("static-access")
		private void Sleep(long seconds) {
			try {
				logger
						.info("current thread start sleep " + seconds
								+ " seconds...");
				Thread.currentThread().sleep(seconds * 1000);
				logger.info("current thread end sleep.");
			} catch (Exception e) {
				logger.error(e, e);
			}
		}

		/**
		 * 获取指定资源的输入流对象
		 * 
		 * @param resource
		 * @return
		 */
		private static InputStream getResourceAsStream(String resource) {
			String stripped = resource.startsWith("/") ? resource.substring(1)
					: resource;
			InputStream stream = null;
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader != null) {
				stream = classLoader.getResourceAsStream(stripped);
			}
			if (stream == null) {
				stream = CommDao.class.getResourceAsStream(resource);
			}
			if (stream == null) {
				stream = CommDao.class.getClassLoader().getResourceAsStream(
						stripped);
			}
			if (stream == null) {
				throw new RuntimeException(resource + " not found");
			}
			return stream;
		}
		
		/**
		 * 元素节点的值是否被加密
		 * @param node - 元素节点对象
		 * @return - true - 其属性 encrypt 的值等于 "true" 或 "yes"， false - 其它值或encrypt属性不存在
		 */
		private static boolean isElmValEncrypted(Node node){
			try{
				NamedNodeMap nodeMap = node.getAttributes();
				if(nodeMap==null) return false;
				Node attrNode = nodeMap.getNamedItem("encrypt");
				if(attrNode==null) return false;
				String encrypt = attrNode.getNodeValue();
				encrypt = encrypt==null ? "" : encrypt.trim().toLowerCase();
				return "true".equals(encrypt) || "yes".equals(encrypt);
			}catch(Exception e){
				return false;
			}
		}

		/***************************************************************************
		 * 
		 * main 函数测试区
		 * 
		 **************************************************************************/

		public static void main1(String[] args) {
			try {
				CommDao commDao = CommDao.getInstance();
				Connection conn = commDao.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rlst = stmt.executeQuery("SELECT 'susscess' FROM DUAL");
				while (rlst.next()) {
					String str1 = rlst.getString(1);
					logger.debug("result=" + str1);
					// String str2 = rlst.getString(2);
					// String str3 = rlst.getString(3);
					// logger.info(String.format("value is >> %s, %s, %s", str1,
					// str2, str3));
				}
				CommDao.closeResultSet(rlst);
				CommDao.closeStatement(stmt);
				CommDao.closeConnection(conn);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
		
		public static void main2(String[] args) {
			Connection conn = null;
			Statement stmt = null;
			try {
				CommDao commDao = CommDao.getInstance();
				conn = commDao.getConnection();
				stmt = conn.createStatement();
				String sql = "CREATE TABLE TEST_TABLE_1 ( ID  NUMBER(6)   NOT NULL,  NAME VARCHAR2(128 BYTE),ADDRESS VARCHAR2(128 BYTE), PHONE_NUMBER  NUMBER(11))";
				stmt.addBatch(sql);
				sql = "CREATE TABLE TEST_TABLE_2 ( ID  NUMBER(6)   NOT NULL,  NAME VARCHAR2(128 BYTE),ADDRESS VARCHAR2(128 BYTE), PHONE_NUMBER  NUMBER(11))";
				stmt.addBatch(sql);
				stmt.executeBatch();
				conn.commit();
			} catch (Exception e) {
				logger.error(e, e);
				CommDao.rollback(conn);
			}finally{
				CommDao.closeStatement(stmt);
				CommDao.closeConnection(conn);
			}
		}
		
		/*************************************************************
		 * 
		 * 任意字符串到 16 进制字符串间的转换，为加密解密用
		 * 
		 *************************************************************/
		
	    /**
	     * converts a byte array to hex string 
	     * (suitable for dumps and ASCII packaging of Binary fields
	     * @param b - byte array
	     * @return String representation
	     */
	    public static String hexString(byte[] b) {
	        StringBuffer d = new StringBuffer(b.length * 2);
	        for (int i=0; i<b.length; i++) {
	            char hi = Character.forDigit ((b[i] >> 4) & 0x0F, 16);
	            char lo = Character.forDigit (b[i] & 0x0F, 16);
	            d.append(Character.toUpperCase(hi));
	            d.append(Character.toUpperCase(lo));
	        }
	        return d.toString();
	    }
	    
	    /**
	     * @param s source string (with Hex representation)
	     * @return byte array
	     */
	    public static byte[] hex2byte (String s) {
	        return hex2byte (s.getBytes(), 0, s.length() >> 1);
	    }
	    
	    /**
	     * @param   b       source byte array
	     * @param   offset  starting offset
	     * @param   len     number of bytes in destination (processes len*2)
	     * @return  byte[len]
	     */
	    public static byte[] hex2byte (byte[] b, int offset, int len) {
	        byte[] d = new byte[len];
	        for (int i=0; i<len*2; i++) {
	            int shift = i%2 == 1 ? 0 : 4;
	            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
	        }
	        return d;
	    }
	    

		/*************************************************************
		 * 
		 * 加密、解密函数、变量等
		 * 
		 *************************************************************/
		
		// 程序内嵌的密钥，用一维数组表示的一张置换表
		private static final char m_KeyAsc[] = {
			0x48, 0xEE, 0x76, 0x1D, 0x67, 0x69, 0xA1, 0x1B,
			0x7A, 0x86, 0x47, 0xF8, 0x54, 0x95, 0x97, 0x5F,
		 	0x78, 0xD9, 0xDA, 0x6C, 0x59, 0xD7, 0x6B, 0x35,
		 	0xC5, 0x77, 0x85, 0x18, 0x2A, 0x0E, 0x52, 0xFF,
			0x00, 0xE3, 0x1B, 0x71, 0x8D, 0x34, 0x63, 0xEB,
		  	0x91, 0xC3, 0x24, 0x0F, 0xB7, 0xC2, 0xF8, 0xE3,
		  	0xB6, 0x54, 0x4C, 0x35, 0x54, 0xE7, 0xC9, 0x49,
		  	0x28, 0xA3, 0x85, 0x11, 0x0B, 0x2C, 0x68, 0xFB,
		  	0xEE, 0x7D, 0xF6, 0x6C, 0xE3, 0x9C, 0x2D, 0xE4,
		  	0x72, 0xC3, 0xBB, 0x85, 0x1A, 0x12, 0x3C, 0x32,
		  	0xE3, 0x6B, 0x4F, 0x4D, 0xF4, 0xA9, 0x24, 0xC8,
		  	0xFA, 0x78, 0xAD, 0x23, 0xA1, 0xE4, 0x6D, 0x9A,
		  	0x04, 0xCE, 0x2B, 0xC5, 0xB6, 0xC5, 0xEF, 0x93,
		  	0x5C, 0xA8, 0x85, 0x2B, 0x41, 0x37, 0x72, 0xFA,
		  	0x57, 0x45, 0x41, 0xA1, 0x20, 0x4F, 0x80, 0xB3,
		  	0xD5, 0x23, 0x02, 0x64, 0x3F, 0x6C, 0xF1, 0x0F
		  };
		
		
	    // 十六进制的数字到字符的映射数组，如数值13的字符为hex[13]
		private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	    
		/**
		 * 加密一个 ascii 字符串
		 * @param   input 需要加密的<code>ascii</code>字符串
		 * @return  成功时候，返回加密的字符串；失败返回null
		 */
		private static String encrypt(String input) throws Exception {
			if (null==input) {
				throw new Exception("提供的明文字符串不能为 null");
			}
			input = hexString(input.getBytes()); // 任意字符串的16进制字符串化
			
			if (!isAscString(input)){
				throw new Exception("提供的明文字符串必须全部由 ascii 字符组成！");
			}

			char[] iary = input.toCharArray();

			for(int i=0; i<iary.length; i++){
				iary[i] = (char)(m_KeyAsc[i] ^ iary[i]);
			}

			int inx = 0;
			char[] ohex = new char[2 * iary.length];
			for(int i=0; i<iary.length; i++){
				int low = iary[i] & 0x000F;
				int hight = (iary[i] & 0x00F0)>>4;
				ohex[inx++] = hex[hight];
				ohex[inx++] = hex[low];
			}

			return new String(ohex);
		}

		/**
		 * 解密一个由本类加密的字符串
		 * @param   input 需要解密的<code>由本类加密的</code>字符串
		 * @return  成功时候，返回解密的字符串；失败返回null
		 * @throws Exception 
		 */
		private static String decrypt(String input) throws Exception {
			System.out.println(" input = " + input);
			if (null==input || 0 != (input.length()%2) || !isAllHexChar(input)) {
				throw new Exception("密文有误，不合规格！");
			}

			int ilen = input.length();
			char[] oary = new char[ilen/2];

			for(int i=0; i<ilen; i+=2){
				String substr = input.substring(i, i+2);
				int val = Integer.parseInt(substr, 16);
				oary[i/2] = (char)(m_KeyAsc[i/2] ^ val);
			}

			byte[] bytes = hex2byte(new String(oary)); // 16进制表示的字符串到字节数组的转换
			return new String(bytes);
		}


		/**
		 * 辅助函数，用来判定一个给定的串是否<code>只包含ascii字符</code>
		 * @param   str 需要检测的字符串
		 * @return  当str只包含ascii字符时候为true，否则为false
		 */
		private static boolean isAscString(String str){
			char[] iary = str.toCharArray();

			for(int i=0; i<iary.length; i++){

				if ((iary[i]& 0xFF80) != 0) return false;
			}

			return true;
		}


		/**
		 * 辅助函数，用来判定一个给定的串是否<code>只包含十六进制字符</code>
		 * @param   str 需要检测的字符串
		 * @return  当str只包含十六进制字符时候为true，否则为false
		 */
		private static boolean isAllHexChar(String str){
			String hexstr = "0123456789abcdefABCDEF";
			char[] iary = str.toCharArray();

			for(int i=0; i<iary.length; i++){
				if (hexstr.indexOf(iary[i])==-1) return false;
			}

			return true;
		}
		
		
		/**
		 * 本类提供的加密串获取函数
		 */
		@SuppressWarnings("deprecation")
		public static void main(String[] args) {
			String END_FLAG = "End!!!";
			String ENTER = "\n";
			
			try {		
				System.out.println("请输入明文, 一行上剪除空白符后等于 End!!! 表示输入结束:");
				StringBuffer buf = new StringBuffer("");
				while(true){
					DataInputStream dis = new DataInputStream(System.in);
					String line = dis.readLine();
					if(END_FLAG.equals(line.trim()))break;
					buf.append(line).append(ENTER);
				}
				String input = buf.substring(0, buf.length()-ENTER.length());
				System.out.println("输入结束！你提供的有效明文是：");
				System.out.println("*********************************************************************");
				System.out.println(input);
				System.out.println("*********************************************************************");
				System.out.println("\n\n");
				
				String encryptStr = encrypt(input);
				System.out.println("加密结束！得到的密文是：");
				System.out.println("*********************************************************************");
				System.out.println(encryptStr);
				System.out.println("*********************************************************************");
				System.out.println("\n\n");
				
				String decryptStr = decrypt(encryptStr);
				System.out.println("解密结束！还原的明文是：");
				System.out.println("*********************************************************************");
				System.out.println(decryptStr);
				System.out.println("*********************************************************************");
				System.out.println("\n\n");
				
				System.out.println("加解密成功结束！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
