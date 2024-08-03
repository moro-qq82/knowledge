/*    */ package main_java_org_support_project.web.batch;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.apache.log4j.FileAppender;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogFactory;
/*    */ import org.support.project.common.util.PropertyUtil;
/*    */ import org.support.project.common.util.SystemUtils;
/*    */ import org.support.project.web.config.AppConfig;
/*    */ import org.support.project.web.logic.DBConnenctionLogic;
/*    */ 
/*    */ public abstract class AbstractBat {
/* 20 */   private static final Log LOG = LogFactory.getLog(org.support.project.web.batch.AbstractBat.class);
/*    */   
/*    */   public static void initLogName(String logname) {
/* 29 */     String logsPath = AppConfig.get().getLogsPath();
/* 30 */     File logDir = new File(logsPath);
/* 31 */     if (!logDir.exists())
/* 32 */       logDir.mkdirs(); 
/* 34 */     Logger log = Logger.getRootLogger();
/* 35 */     FileAppender appendar = (FileAppender)log.getAppender("APP_FILEOUT");
/* 36 */     if (appendar != null) {
/* 37 */       File logfile = new File(logDir, logname);
/* 38 */       appendar.setFile(logfile.getAbsolutePath());
/* 39 */       appendar.activateOptions();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected static void configInit(String batName) {
/* 49 */     AppConfig.get();
/* 50 */     LOG.info(batName + " is start.");
/* 51 */     if (LOG.isDebugEnabled()) {
/* 52 */       String envValue = System.getenv(AppConfig.getEnvKey());
/* 53 */       LOG.debug("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "].");
/* 54 */       LOG.debug("Config :" + PropertyUtil.reflectionToString(AppConfig.get()));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected static void finishInfo() {
/* 62 */     if (LOG.isDebugEnabled()) {
/* 63 */       String sysinfo = SystemUtils.systemInfo();
/* 64 */       LOG.debug(sysinfo);
/*    */     } 
/* 66 */     LOG.info("Finished");
/*    */   }
/*    */   
/*    */   public void dbInit() {
/* 73 */     DBConnenctionLogic.get().connectCustomConnection();
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\web\batch\AbstractBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */