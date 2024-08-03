/*    */ package main_java_org_support_project.common.bean;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.support.project.common.config.Resources;
/*    */ import org.support.project.common.log.LogLevel;
/*    */ 
/*    */ public class ValidateError {
/* 11 */   public static final int WARN = LogLevel.WARN.getValue();
/*    */   
/* 12 */   public static final int ERROR = LogLevel.ERROR.getValue();
/*    */   
/*    */   private Integer level;
/*    */   
/*    */   private String key;
/*    */   
/*    */   private String[] params;
/*    */   
/*    */   public ValidateError(Integer level, String key, String... params) {
/* 24 */     this.level = level;
/* 25 */     this.key = key;
/* 26 */     this.params = params;
/*    */   }
/*    */   
/*    */   public ValidateError(String key, String... params) {
/* 33 */     this(Integer.valueOf(WARN), key, params);
/*    */   }
/*    */   
/*    */   public String getMsg(Locale locale) {
/* 37 */     Resources resources = Resources.getInstance("/appresource", locale);
/* 38 */     String msg = resources.getResource(this.key, this.params);
/* 39 */     return msg;
/*    */   }
/*    */   
/*    */   public Integer getLevel() {
/* 43 */     return this.level;
/*    */   }
/*    */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\common\bean\ValidateError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */