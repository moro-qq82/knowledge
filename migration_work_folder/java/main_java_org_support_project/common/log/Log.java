package main_java_org_support_project.common.log;

import org.support.project.common.log.LogLevel;

public interface Log {
  void trace(Object paramObject);
  
  void trace(Object paramObject, Throwable paramThrowable);
  
  void debug(Object paramObject);
  
  void debug(Object paramObject, Throwable paramThrowable);
  
  void info(Object paramObject);
  
  void info(Object paramObject, Throwable paramThrowable);
  
  void warn(Object paramObject);
  
  void warn(Object paramObject, Throwable paramThrowable);
  
  void error(Object paramObject);
  
  void error(Object paramObject, Throwable paramThrowable);
  
  void fatal(Object paramObject);
  
  void fatal(Object paramObject, Throwable paramThrowable);
  
  boolean isTraceEnabled();
  
  boolean isDebugEnabled();
  
  boolean isInfoEnabled();
  
  boolean isWarnEnabled();
  
  boolean isErrorEnabled();
  
  boolean isFatalEnabled();
  
  void print(LogLevel paramLogLevel, Object paramObject);
  
  void print(LogLevel paramLogLevel, Object paramObject, Throwable paramThrowable);
  
  boolean isEnabled(LogLevel paramLogLevel);
}


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */