/*    */ package main_java_org_support_project.knowledge.bat;
/*    */ 
/*    */ import java.util.TimeZone;
/*    */ import org.apache.log4j.FileAppender;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogFactory;
/*    */ import org.support.project.common.util.PropertyUtil;
/*    */ import org.support.project.common.util.SystemUtils;
/*    */ import org.support.project.knowledge.config.AppConfig;
/*    */ import org.support.project.web.logic.DBConnenctionLogic;
/*    */ 
/*    */ public abstract class AbstractBat {
/* 15 */   private static final Log LOG = LogFactory.getLog(org.support.project.knowledge.bat.AbstractBat.class);
/*    */   
/*    */   public static void initLogName(String logname) {
/* 18 */     Logger log = Logger.getRootLogger();
/* 19 */     FileAppender appendar = (FileAppender)log.getAppender("APP_FILEOUT");
/* 20 */     appendar.setFile(logname);
/* 21 */     appendar.activateOptions();
/*    */   }
/*    */   
/*    */   protected static void configInit(String batName) {
/* 26 */     TimeZone zone = TimeZone.getTimeZone("GMT");
/* 27 */     TimeZone.setDefault(zone);
/* 29 */     AppConfig.get();
/* 30 */     String envValue = System.getenv(AppConfig.getEnvKey());
/* 31 */     LOG.info(batName + " is start.");
/* 32 */     if (LOG.isDebugEnabled()) {
/* 33 */       LOG.debug("Env [" + AppConfig.getEnvKey() + "] is [" + envValue + "].");
/* 34 */       LOG.debug("Config :" + PropertyUtil.reflectionToString(AppConfig.get()));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected static void finishInfo() {
/* 39 */     if (LOG.isDebugEnabled()) {
/* 40 */       String sysinfo = SystemUtils.systemInfo();
/* 41 */       LOG.debug(sysinfo);
/*    */     } 
/* 43 */     LOG.info("Finished");
/*    */   }
/*    */   
/*    */   public void dbInit() {
/* 50 */     DBConnenctionLogic.get().connectCustomConnection();
/*    */   }
/*    */   
/*    */   protected void send(String msg) {
/* 54 */     LOG.info("[SEND]" + msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\knowledge\bat\AbstractBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */