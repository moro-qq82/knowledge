/*    */ package main_java_org_support_project.knowledge.listener;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import org.apache.log4j.FileAppender;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogFactory;
/*    */ import org.support.project.common.util.StringUtils;
/*    */ import org.support.project.knowledge.config.AppConfig;
/*    */ 
/*    */ public class GlobalInitializationListener implements ServletContextListener {
/* 16 */   private static final Log LOG = LogFactory.getLog(org.support.project.knowledge.listener.GlobalInitializationListener.class);
/*    */   
/*    */   public void contextInitialized(ServletContextEvent config) {
/* 20 */     AppConfig.get();
/* 21 */     String envValue = System.getenv(AppConfig.getEnvKey());
/* 22 */     if (StringUtils.isNotEmpty(envValue))
/* 23 */       LOG.info("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "]."); 
/* 25 */     String rootPath = AppConfig.get().getBasePath();
/* 26 */     System.setProperty("user.dir", rootPath);
/* 27 */     String logsPath = AppConfig.get().getLogsPath();
/* 28 */     File logDir = new File(logsPath);
/* 29 */     if (!logDir.exists())
/* 30 */       logDir.mkdirs(); 
/* 32 */     Logger log = Logger.getRootLogger();
/* 33 */     FileAppender appendar = (FileAppender)log.getAppender("APP_FILEOUT");
/* 34 */     appendar.setFile(logDir + "/app.log");
/* 35 */     appendar.activateOptions();
/* 36 */     LOG.info("[APP LOG] " + logDir.getAbsolutePath() + "/app.log");
/*    */   }
/*    */   
/*    */   public void contextDestroyed(ServletContextEvent config) {}
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\knowledge\listener\GlobalInitializationListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */