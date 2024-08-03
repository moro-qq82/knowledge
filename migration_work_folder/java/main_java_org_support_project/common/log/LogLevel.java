/*    */ package main_java_org_support_project.common.log;
/*    */ 
/*    */ public enum LogLevel {
/*  5 */   TRACE, DEBUG, INFO, WARN, ERROR, FATAL;
/*    */   
/*    */   public int getValue() {
/*  8 */     return ordinal();
/*    */   }
/*    */   
/*    */   public org.support.project.common.log.LogLevel getType(int type) {
/* 12 */     org.support.project.common.log.LogLevel[] values = values();
/* 13 */     return values[type];
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\log\LogLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */