/*    */ package main_java_org_support_project.common.log;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogInitializer;
/*    */ import org.support.project.common.log.impl.def.JavaDefaultLogInitializerImpl;
/*    */ import org.support.project.common.log.impl.log4j.Log4jLogInitializerImpl;
/*    */ 
/*    */ public class LogFactory {
/*    */   private static boolean init = false;
/*    */   
/*    */   private static Map<Class, Log> logMap;
/*    */   
/*    */   private static final int LOG_MODE_DEFAULT = 0;
/*    */   
/*    */   private static final int LOG_MODE_LOG4J = 1;
/*    */   
/* 23 */   private static int logmode = 0;
/*    */   
/* 25 */   private static LogInitializer logInitializer = null;
/*    */   
/*    */   public static final Log getLog(Class<?> clazz) {
/* 28 */     if (!init) {
/* 29 */       logMap = (Map)new HashMap<>();
/*    */       try {
/* 31 */         Class.forName("org.apache.log4j.Logger");
/* 33 */         logmode = 1;
/* 34 */         logInitializer = (LogInitializer)new Log4jLogInitializerImpl();
/* 35 */       } catch (ClassNotFoundException e) {
/* 37 */         logmode = 0;
/* 38 */         logInitializer = (LogInitializer)new JavaDefaultLogInitializerImpl();
/*    */       } 
/* 40 */       init = true;
/*    */     } 
/* 43 */     if (!logMap.containsKey(clazz)) {
/* 44 */       Log log = logInitializer.createLog(clazz);
/* 45 */       logMap.put(clazz, log);
/*    */     } 
/* 47 */     return logMap.get(clazz);
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\LogFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */