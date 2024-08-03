/*    */ package main_java_org_support_project.common.log.impl.def;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.logging.LogManager;
/*    */ import java.util.logging.Logger;
/*    */ import org.support.project.common.log.Log;
/*    */ import org.support.project.common.log.LogFactory;
/*    */ import org.support.project.common.log.LogInitializer;
/*    */ import org.support.project.common.log.impl.def.JavaDefaultLogImpl;
/*    */ 
/*    */ public class JavaDefaultLogInitializerImpl implements LogInitializer {
/*    */   private static boolean init = false;
/*    */   
/*    */   public Log createLog(Class<?> type) {
/* 17 */     if (!init) {
/*    */       try {
/* 20 */         InputStream inputStream = LogFactory.class.getResourceAsStream("/logging.properties");
/* 21 */         if (inputStream != null)
/* 22 */           LogManager.getLogManager().readConfiguration(); 
/* 24 */       } catch (SecurityException e1) {
/* 25 */         e1.printStackTrace();
/* 26 */       } catch (IOException e1) {
/* 27 */         e1.printStackTrace();
/*    */       } 
/* 29 */       init = true;
/*    */     } 
/* 31 */     Logger logger = Logger.getLogger(type.getCanonicalName());
/* 32 */     return (Log)new JavaDefaultLogImpl(logger);
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\impl\def\JavaDefaultLogInitializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */