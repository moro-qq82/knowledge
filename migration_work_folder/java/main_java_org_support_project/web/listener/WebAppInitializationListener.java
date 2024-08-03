/*    */ package main_java_org_support_project.web.listener;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import org.apache.log4j.FileAppender;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogFactory;
/*    */ import org.support.project.web.config.AppConfig;
/*    */ 
/*    */ public class WebAppInitializationListener implements ServletContextListener {
/* 20 */   private static final Log LOG = LogFactory.getLog(org.support.project.web.listener.WebAppInitializationListener.class);
/*    */   
/*    */   public void contextInitialized(ServletContextEvent sce) {
/* 24 */     String rootPath = AppConfig.get().getBasePath();
/* 25 */     System.setProperty("user.dir", rootPath);
/* 28 */     String logsPath = AppConfig.get().getLogsPath();
/* 29 */     File logDir = new File(logsPath);
/* 30 */     if (!logDir.exists())
/* 31 */       logDir.mkdirs(); 
/* 33 */     Logger log = Logger.getRootLogger();
/* 34 */     FileAppender appendar = (FileAppender)log.getAppender("APP_FILEOUT");
/* 35 */     if (appendar != null) {
/* 36 */       File logfile = new File(logDir, "app.log");
/* 37 */       appendar.setFile(logfile.getAbsolutePath());
/* 38 */       appendar.activateOptions();
/*    */     } 
/* 42 */     AppConfig appConfig = AppConfig.get();
/* 43 */     String tmpDir = appConfig.getTmpPath();
/* 44 */     File tmp = new File(tmpDir);
/* 45 */     if (!tmp.exists()) {
/* 46 */       tmp.mkdirs();
/* 47 */       LOG.info("tmp directory created." + tmpDir);
/*    */     } 
/* 50 */     String path = sce.getServletContext().getRealPath("/");
/* 51 */     LOG.info("WebApp start");
/* 52 */     LOG.info("WebApp install path: '" + path + "'");
/* 53 */     LOG.info("WebApp home path: '" + appConfig.getBasePath() + "'");
/* 54 */     LOG.info("[APP LOG] " + logDir.getAbsolutePath() + "/app.log");
/* 55 */     AppConfig.setWebRealPath(path);
/*    */   }
/*    */   
/*    */   public void contextDestroyed(ServletContextEvent sce) {}
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\web\listener\WebAppInitializationListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */