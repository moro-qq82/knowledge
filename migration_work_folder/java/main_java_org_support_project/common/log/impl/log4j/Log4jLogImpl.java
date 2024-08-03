/*     */ package main_java_org_support_project.common.log.impl.log4j;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.support.project.common.log.Log;
/*     */ import org.support.project.common.log.LogLevel;
/*     */ import org.support.project.common.util.PropertyUtil;
/*     */ 
/*     */ class Log4jLogImpl implements Log {
/*  20 */   private static final String FQCN = org.support.project.common.log.impl.log4j.Log4jLogImpl.class.getName();
/*     */   
/*  23 */   private volatile transient Logger logger = null;
/*     */   
/*     */   Log4jLogImpl(Logger logger) {
/*  32 */     this.logger = logger;
/*     */   }
/*     */   
/*     */   public void trace(Object msg) {
/*  37 */     print(LogLevel.TRACE, msg);
/*     */   }
/*     */   
/*     */   public void trace(Object msg, Throwable t) {
/*  42 */     print(LogLevel.TRACE, msg, t);
/*     */   }
/*     */   
/*     */   public void debug(Object msg) {
/*  47 */     print(LogLevel.DEBUG, msg);
/*     */   }
/*     */   
/*     */   public void debug(Object msg, Throwable t) {
/*  52 */     print(LogLevel.DEBUG, msg, t);
/*     */   }
/*     */   
/*     */   public void info(Object msg) {
/*  57 */     print(LogLevel.INFO, msg);
/*     */   }
/*     */   
/*     */   public void info(Object msg, Throwable t) {
/*  62 */     print(LogLevel.INFO, msg, t);
/*     */   }
/*     */   
/*     */   public void warn(Object msg) {
/*  67 */     print(LogLevel.WARN, msg);
/*     */   }
/*     */   
/*     */   public void warn(Object msg, Throwable t) {
/*  72 */     print(LogLevel.WARN, msg, t);
/*     */   }
/*     */   
/*     */   public void error(Object msg) {
/*  77 */     print(LogLevel.ERROR, msg);
/*     */   }
/*     */   
/*     */   public void error(Object msg, Throwable t) {
/*  82 */     print(LogLevel.ERROR, msg, t);
/*     */   }
/*     */   
/*     */   public void fatal(Object msg) {
/*  87 */     print(LogLevel.FATAL, msg);
/*     */   }
/*     */   
/*     */   public void fatal(Object msg, Throwable t) {
/*  92 */     print(LogLevel.FATAL, msg, t);
/*     */   }
/*     */   
/*     */   public boolean isTraceEnabled() {
/*  97 */     return isEnabled(LogLevel.TRACE);
/*     */   }
/*     */   
/*     */   public boolean isDebugEnabled() {
/* 102 */     return isEnabled(LogLevel.DEBUG);
/*     */   }
/*     */   
/*     */   public boolean isInfoEnabled() {
/* 107 */     return isEnabled(LogLevel.INFO);
/*     */   }
/*     */   
/*     */   public boolean isWarnEnabled() {
/* 112 */     return isEnabled(LogLevel.WARN);
/*     */   }
/*     */   
/*     */   public boolean isErrorEnabled() {
/* 117 */     return isEnabled(LogLevel.ERROR);
/*     */   }
/*     */   
/*     */   public boolean isFatalEnabled() {
/* 122 */     return isEnabled(LogLevel.FATAL);
/*     */   }
/*     */   
/*     */   public void print(LogLevel level, Object msg) {
/* 127 */     print(level, msg, null);
/*     */   }
/*     */   
/*     */   public void print(LogLevel level, Object msg, Throwable t) {
/* 132 */     String str = null;
/* 133 */     if (msg != null)
/* 134 */       if (msg instanceof String) {
/* 135 */         str = (String)msg;
/*     */       } else {
/* 137 */         str = PropertyUtil.reflectionToString(msg);
/*     */       }  
/* 140 */     if (LogLevel.TRACE == level) {
/* 141 */       if (t == null) {
/* 142 */         this.logger.trace(str);
/*     */       } else {
/* 144 */         this.logger.trace(str, t);
/*     */       } 
/* 147 */     } else if (t == null) {
/* 148 */       this.logger.log(FQCN, getLevel(level), str, null);
/*     */     } else {
/* 150 */       removeDisuseStackTraceInfo(t);
/* 151 */       this.logger.log(FQCN, getLevel(level), str, t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeDisuseStackTraceInfo(Throwable t) {
/* 162 */     List<StackTraceElement> list = new ArrayList<>();
/* 163 */     StackTraceElement[] stackTraceElements = t.getStackTrace();
/* 164 */     for (StackTraceElement stackTraceElement : stackTraceElements) {
/* 166 */       if (!stackTraceElement.toString().startsWith("sun.reflect.") && 
/* 167 */         !stackTraceElement.toString().startsWith("java.lang.reflect.Method.invoke") && 
/* 168 */         !stackTraceElement.toString().startsWith("org.support.project.aop.Intercepter.invoke") && stackTraceElement
/* 169 */         .toString().indexOf("_$$_") == -1)
/* 170 */         list.add(stackTraceElement); 
/*     */     } 
/* 173 */     StackTraceElement[] elements = list.<StackTraceElement>toArray(new StackTraceElement[0]);
/* 174 */     t.setStackTrace(elements);
/* 175 */     if (t.getCause() != null)
/* 176 */       removeDisuseStackTraceInfo(t.getCause()); 
/*     */   }
/*     */   
/*     */   public boolean isEnabled(LogLevel level) {
/* 182 */     if (LogLevel.TRACE == level)
/* 183 */       return this.logger.isTraceEnabled(); 
/* 185 */     return this.logger.isEnabledFor(getLevel(level));
/*     */   }
/*     */   
/*     */   private Priority getLevel(LogLevel level) {
/* 194 */     Priority priority = Priority.INFO;
/* 195 */     if (level == LogLevel.TRACE) {
/* 196 */       priority = Priority.DEBUG;
/* 197 */     } else if (level == LogLevel.DEBUG) {
/* 198 */       priority = Priority.DEBUG;
/* 199 */     } else if (level == LogLevel.INFO) {
/* 200 */       priority = Priority.INFO;
/* 201 */     } else if (level == LogLevel.WARN) {
/* 202 */       priority = Priority.WARN;
/* 203 */     } else if (level == LogLevel.ERROR) {
/* 204 */       priority = Priority.ERROR;
/* 205 */     } else if (level == LogLevel.FATAL) {
/* 206 */       priority = Priority.FATAL;
/*     */     } 
/* 208 */     return priority;
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\impl\log4j\Log4jLogImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */