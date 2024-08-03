/*    */ package main_java_org_support_project.common.log.impl.def;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.support.project.common.log.LogLevel;
/*    */ import org.support.project.common.log.impl.AbstractLog;
/*    */ import org.support.project.common.util.PropertyUtil;
/*    */ 
/*    */ class JavaDefaultLogImpl extends AbstractLog {
/*    */   private Logger logger;
/*    */   
/*    */   public JavaDefaultLogImpl(Logger logger) {
/* 15 */     this.logger = logger;
/*    */   }
/*    */   
/*    */   public void print(LogLevel level, Object msg, Throwable t) {
/* 20 */     String str = null;
/* 21 */     if (msg != null)
/* 22 */       if (msg instanceof String) {
/* 23 */         str = (String)msg;
/*    */       } else {
/* 25 */         str = PropertyUtil.reflectionToString(msg);
/*    */       }  
/* 29 */     if (t == null) {
/* 30 */       this.logger.log(getLevel(level), str);
/*    */     } else {
/* 32 */       this.logger.log(getLevel(level), str, t);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isEnabled(LogLevel level) {
/* 38 */     return this.logger.isLoggable(getLevel(level));
/*    */   }
/*    */   
/*    */   private Level getLevel(LogLevel level) {
/* 43 */     Level jlevel = Level.INFO;
/* 44 */     if (level == LogLevel.TRACE) {
/* 45 */       jlevel = Level.FINE;
/* 46 */     } else if (level == LogLevel.DEBUG) {
/* 47 */       jlevel = Level.CONFIG;
/* 48 */     } else if (level == LogLevel.INFO) {
/* 49 */       jlevel = Level.INFO;
/* 50 */     } else if (level == LogLevel.WARN) {
/* 51 */       jlevel = Level.WARNING;
/* 52 */     } else if (level == LogLevel.ERROR) {
/* 53 */       jlevel = Level.SEVERE;
/* 54 */     } else if (level == LogLevel.FATAL) {
/* 55 */       jlevel = Level.SEVERE;
/*    */     } 
/* 57 */     return jlevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\impl\def\JavaDefaultLogImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */