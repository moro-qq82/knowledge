/*    */ package main_java_org_support_project.common.log.impl.log4j;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogInitializer;
/*    */ import org.support.project.common.log.impl.log4j.Log4jLogImpl;
/*    */ 
/*    */ public class Log4jLogInitializerImpl implements LogInitializer {
/*    */   public Log createLog(Class<?> type) {
/* 11 */     Logger logger = Logger.getLogger(type.getCanonicalName());
/* 12 */     return (Log)new Log4jLogImpl(logger);
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\impl\log4j\Log4jLogInitializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */