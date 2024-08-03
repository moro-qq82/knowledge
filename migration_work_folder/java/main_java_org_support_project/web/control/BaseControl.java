/*     */ package main_java_org_support_project.web.control;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.support.project.common.bean.ValidateError;
/*     */ import org.support.project.common.config.Resources;
/*     */ import org.support.project.common.log.LogLevel;
/*     */ import org.support.project.common.util.HtmlUtils;
/*     */ import org.support.project.di.DI;
/*     */ import org.support.project.di.Instance;
/*     */ import org.support.project.web.common.HttpUtil;
/*     */ import org.support.project.web.control.Control;
/*     */ 
/*     */ @DI(instance = Instance.Prototype)
/*     */ public abstract class BaseControl extends Control {
/*     */   public static final String MSG_INFO = "NOTIFY_MSG_INFO";
/*     */   
/*     */   public static final String MSG_SUCCESS = "NOTIFY_MSG_SUCCESS";
/*     */   
/*     */   public static final String MSG_WARN = "NOTIFY_MSG_WARN";
/*     */   
/*     */   public static final String MSG_ERROR = "NOTIFY_MSG_ERROR";
/*     */   
/*  32 */   private List<String> infos = null;
/*     */   
/*  34 */   private List<String> successes = null;
/*     */   
/*  36 */   private List<String> warns = null;
/*     */   
/*  38 */   private List<String> errors = null;
/*     */   
/*     */   public void setRequest(HttpServletRequest request) {
/*  42 */     super.setRequest(request);
/*  43 */     this.infos = new ArrayList<>();
/*  44 */     this.successes = new ArrayList<>();
/*  45 */     this.warns = new ArrayList<>();
/*  46 */     this.errors = new ArrayList<>();
/*  48 */     request.setAttribute("NOTIFY_MSG_INFO", this.infos);
/*  49 */     request.setAttribute("NOTIFY_MSG_SUCCESS", this.successes);
/*  50 */     request.setAttribute("NOTIFY_MSG_WARN", this.warns);
/*  51 */     request.setAttribute("NOTIFY_MSG_ERROR", this.errors);
/*     */   }
/*     */   
/*     */   protected void addMsgInfo(String key, String... params) {
/*  60 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  61 */     String msg = resources.getResource(key, params);
/*  62 */     this.infos.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void addMsgSuccess(String key, String... params) {
/*  71 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  72 */     String msg = resources.getResource(key, params);
/*  73 */     this.successes.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void addMsgWarn(String key, String... params) {
/*  82 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  83 */     String msg = resources.getResource(key, params);
/*  84 */     this.warns.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void addMsgError(String key, String... params) {
/*  93 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  94 */     String msg = resources.getResource(key, params);
/*  95 */     this.errors.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void setErrors(List<ValidateError> errors) {
/* 103 */     if (errors != null)
/* 104 */       for (ValidateError validateError : errors) {
/* 105 */         if (validateError.getLevel().intValue() == LogLevel.ERROR.getValue()) {
/* 106 */           addMsgError(validateError.getMsg(HttpUtil.getLocale(getRequest())), new String[0]);
/*     */           continue;
/*     */         } 
/* 108 */         addMsgWarn(validateError.getMsg(HttpUtil.getLocale(getRequest())), new String[0]);
/*     */       }  
/*     */   }
/*     */   
/*     */   protected void copy(Control control) {
/* 119 */     super.copy(control);
/* 120 */     if (control instanceof org.support.project.web.control.BaseControl) {
/* 121 */       org.support.project.web.control.BaseControl c = (org.support.project.web.control.BaseControl)control;
/* 122 */       for (String string : this.infos)
/* 123 */         c.addMsgInfo(string, new String[0]); 
/* 125 */       for (String string : this.successes)
/* 126 */         c.addMsgSuccess(string, new String[0]); 
/* 128 */       for (String string : this.warns)
/* 129 */         c.addMsgWarn(string, new String[0]); 
/* 131 */       for (String string : this.errors)
/* 132 */         c.addMsgError(string, new String[0]); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\web\control\BaseControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */