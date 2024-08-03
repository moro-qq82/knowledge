/*     */ package main_java_org_support_project.common.log.impl;
/*     */ 
/*     */ import org.support.project.common.log.Log;
/*     */ import org.support.project.common.log.LogLevel;
/*     */ 
/*     */ public abstract class AbstractLog implements Log {
/*     */   public void trace(Object msg) {
/*  10 */     print(LogLevel.TRACE, msg);
/*     */   }
/*     */   
/*     */   public void trace(Object msg, Throwable t) {
/*  15 */     print(LogLevel.TRACE, msg, t);
/*     */   }
/*     */   
/*     */   public void debug(Object msg) {
/*  20 */     print(LogLevel.DEBUG, msg);
/*     */   }
/*     */   
/*     */   public void debug(Object msg, Throwable t) {
/*  25 */     print(LogLevel.DEBUG, msg, t);
/*     */   }
/*     */   
/*     */   public void info(Object msg) {
/*  30 */     print(LogLevel.INFO, msg);
/*     */   }
/*     */   
/*     */   public void info(Object msg, Throwable t) {
/*  35 */     print(LogLevel.INFO, msg, t);
/*     */   }
/*     */   
/*     */   public void warn(Object msg) {
/*  40 */     print(LogLevel.WARN, msg);
/*     */   }
/*     */   
/*     */   public void warn(Object msg, Throwable t) {
/*  45 */     print(LogLevel.WARN, msg, t);
/*     */   }
/*     */   
/*     */   public void error(Object msg) {
/*  50 */     print(LogLevel.ERROR, msg);
/*     */   }
/*     */   
/*     */   public void error(Object msg, Throwable t) {
/*  55 */     print(LogLevel.ERROR, msg, t);
/*     */   }
/*     */   
/*     */   public void fatal(Object msg) {
/*  60 */     print(LogLevel.FATAL, msg);
/*     */   }
/*     */   
/*     */   public void fatal(Object msg, Throwable t) {
/*  65 */     print(LogLevel.FATAL, msg, t);
/*     */   }
/*     */   
/*     */   public boolean isTraceEnabled() {
/*  70 */     return isEnabled(LogLevel.TRACE);
/*     */   }
/*     */   
/*     */   public boolean isDebugEnabled() {
/*  75 */     return isEnabled(LogLevel.DEBUG);
/*     */   }
/*     */   
/*     */   public boolean isInfoEnabled() {
/*  80 */     return isEnabled(LogLevel.INFO);
/*     */   }
/*     */   
/*     */   public boolean isWarnEnabled() {
/*  85 */     return isEnabled(LogLevel.WARN);
/*     */   }
/*     */   
/*     */   public boolean isErrorEnabled() {
/*  90 */     return isEnabled(LogLevel.ERROR);
/*     */   }
/*     */   
/*     */   public boolean isFatalEnabled() {
/*  95 */     return isEnabled(LogLevel.FATAL);
/*     */   }
/*     */   
/*     */   public void print(LogLevel level, Object msg) {
/* 100 */     print(level, msg, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\impl\AbstractLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */