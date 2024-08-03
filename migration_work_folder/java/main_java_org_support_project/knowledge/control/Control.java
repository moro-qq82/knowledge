/*     */ package main_java_org_support_project.knowledge.control;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.support.project.common.bean.ValidateError;
/*     */ import org.support.project.common.config.INT_FLAG;
/*     */ import org.support.project.common.config.Resources;
/*     */ import org.support.project.common.exception.ParseException;
/*     */ import org.support.project.common.log.Log;
/*     */ import org.support.project.common.log.LogFactory;
/*     */ import org.support.project.common.log.LogLevel;
/*     */ import org.support.project.common.util.HtmlUtils;
/*     */ import org.support.project.common.util.StringUtils;
/*     */ import org.support.project.di.DI;
/*     */ import org.support.project.di.Instance;
/*     */ import org.support.project.knowledge.dao.NotifyConfigsDao;
/*     */ import org.support.project.knowledge.entity.NotifyConfigsEntity;
/*     */ import org.support.project.knowledge.vo.UserConfigs;
/*     */ import org.support.project.web.bean.LoginedUser;
/*     */ import org.support.project.web.boundary.ForwardBoundary;
/*     */ import org.support.project.web.common.HttpUtil;
/*     */ import org.support.project.web.control.Control;
/*     */ import org.support.project.web.logic.DateConvertLogic;
/*     */ import org.support.project.web.logic.NotificationLogic;
/*     */ import org.support.project.web.logic.SanitizingLogic;
/*     */ 
/*     */ @DI(instance = Instance.Prototype)
/*     */ public abstract class Control extends Control {
/*  35 */   private static final Log LOG = LogFactory.getLog(org.support.project.knowledge.control.Control.class);
/*     */   
/*     */   public static final String MSG_INFO = "NOTIFY_MSG_INFO";
/*     */   
/*     */   public static final String MSG_SUCCESS = "NOTIFY_MSG_SUCCESS";
/*     */   
/*     */   public static final String MSG_WARN = "NOTIFY_MSG_WARN";
/*     */   
/*     */   public static final String MSG_ERROR = "NOTIFY_MSG_ERROR";
/*     */   
/*     */   public static final String NOTIFY_UNREAD_COUNT = "NOTIFY_UNREAD_COUNT";
/*     */   
/*  43 */   private List<String> infos = null;
/*     */   
/*  44 */   private List<String> successes = null;
/*     */   
/*  45 */   private List<String> warns = null;
/*     */   
/*  46 */   private List<String> errors = null;
/*     */   
/*     */   public void setRequest(HttpServletRequest request) {
/*  50 */     super.setRequest(request);
/*  51 */     this.infos = new ArrayList<>();
/*  52 */     this.successes = new ArrayList<>();
/*  53 */     this.warns = new ArrayList<>();
/*  54 */     this.errors = new ArrayList<>();
/*  56 */     request.setAttribute("NOTIFY_MSG_INFO", this.infos);
/*  57 */     request.setAttribute("NOTIFY_MSG_SUCCESS", this.successes);
/*  58 */     request.setAttribute("NOTIFY_MSG_WARN", this.warns);
/*  59 */     request.setAttribute("NOTIFY_MSG_ERROR", this.errors);
/*  62 */     if (getLoginedUser() != null)
/*     */       try {
/*  64 */         Integer count = NotificationLogic.get().countUnRead(getLoginUserId());
/*  65 */         if (count == null)
/*  66 */           count = Integer.valueOf(0); 
/*  68 */         request.setAttribute("NOTIFY_UNREAD_COUNT", count);
/*  69 */       } catch (Exception e) {
/*  70 */         LOG.warn("Error on get user notification count. " + e.getClass().getSimpleName());
/*     */       }  
/*     */   }
/*     */   
/*     */   protected String getResource(String key) {
/*  76 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  77 */     return resources.getResource(key);
/*     */   }
/*     */   
/*     */   protected String getResource(String key, String... params) {
/*  81 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  82 */     return resources.getResource(key, params);
/*     */   }
/*     */   
/*     */   protected void addMsgInfo(String key, String... params) {
/*  86 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  87 */     String msg = resources.getResource(key, params);
/*  88 */     this.infos.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void addMsgSuccess(String key, String... params) {
/*  92 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  93 */     String msg = resources.getResource(key, params);
/*  94 */     this.successes.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void addMsgWarn(String key, String... params) {
/*  98 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/*  99 */     String msg = resources.getResource(key, params);
/* 100 */     this.warns.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void addMsgError(String key, String... params) {
/* 104 */     Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
/* 105 */     String msg = resources.getResource(key, params);
/* 106 */     this.errors.add(HtmlUtils.escapeHTML(msg));
/*     */   }
/*     */   
/*     */   protected void setResult(String successMsg, List<ValidateError> errors, String... params) {
/* 110 */     if (errors == null || errors.isEmpty()) {
/* 111 */       addMsgSuccess(successMsg, params);
/*     */     } else {
/* 113 */       for (ValidateError validateError : errors) {
/* 114 */         if (validateError.getLevel().intValue() == LogLevel.ERROR.getValue()) {
/* 115 */           addMsgError(validateError.getMsg(HttpUtil.getLocale(getRequest())), new String[0]);
/*     */           continue;
/*     */         } 
/* 117 */         addMsgWarn(validateError.getMsg(HttpUtil.getLocale(getRequest())), new String[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String sanitize(String str) throws ParseException {
/* 124 */     return SanitizingLogic.get().sanitize(str);
/*     */   }
/*     */   
/*     */   protected void copy(Control control) {
/* 134 */     super.copy(control);
/* 135 */     if (control instanceof org.support.project.knowledge.control.Control) {
/* 136 */       org.support.project.knowledge.control.Control c = (org.support.project.knowledge.control.Control)control;
/* 137 */       for (String string : this.infos)
/* 138 */         c.addMsgInfo(string, new String[0]); 
/* 140 */       for (String string : this.successes)
/* 141 */         c.addMsgSuccess(string, new String[0]); 
/* 143 */       for (String string : this.warns)
/* 144 */         c.addMsgWarn(string, new String[0]); 
/* 146 */       for (String string : this.errors)
/* 147 */         c.addMsgError(string, new String[0]); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ForwardBoundary forward(String path) {
/* 162 */     if (getLoginUserId().intValue() > 0) {
/* 163 */       NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
/* 164 */       NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(getLoginUserId());
/* 165 */       if (notifyConfigsEntity != null && 
/* 166 */         flagCheck(notifyConfigsEntity.getNotifyDesktop()))
/* 169 */         if (flagCheck(notifyConfigsEntity.getMyItemComment()) || flagCheck(notifyConfigsEntity.getMyItemLike()) || 
/* 170 */           flagCheck(notifyConfigsEntity.getMyItemStock()) || flagCheck(notifyConfigsEntity.getStockItemSave()) || 
/* 171 */           flagCheck(notifyConfigsEntity.getStokeItemComment()) || flagCheck(notifyConfigsEntity.getToItemComment()) || 
/* 172 */           flagCheck(notifyConfigsEntity.getToItemSave())) {
/* 173 */           if (LOG.isTraceEnabled())
/* 174 */             LOG.info("Notify On to [" + getLoginUserId() + "]"); 
/* 176 */           setAttribute("desktopNotify", Boolean.valueOf(true));
/*     */         }  
/*     */     } 
/* 181 */     return super.forward(path);
/*     */   }
/*     */   
/*     */   private boolean flagCheck(Integer check) {
/* 185 */     if (check == null)
/* 186 */       return false; 
/* 188 */     if (check.intValue() == INT_FLAG.ON.getValue())
/* 189 */       return true; 
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   protected UserConfigs getUserConfigs() {
/* 195 */     UserConfigs userConfigs = (UserConfigs)getRequest().getAttribute("REQUEST_USER_CONFIG_KEY");
/* 196 */     if (userConfigs == null)
/* 197 */       userConfigs = new UserConfigs(); 
/* 199 */     LoginedUser login = getLoginedUser();
/* 200 */     if (login != null) {
/* 201 */       userConfigs.setLocale(login.getLocale());
/*     */     } else {
/* 203 */       userConfigs.setLocale(getLocale());
/*     */     } 
/* 206 */     if (userConfigs.getTimezone().equals("UTC")) {
/* 207 */       String offset = HttpUtil.getCookie(getRequest(), "TIME_ZONE_OFFSET");
/* 208 */       if (StringUtils.isInteger(offset)) {
/* 209 */         int off = Integer.parseInt(offset);
/* 210 */         userConfigs.setTimezoneOffset(off);
/* 211 */         TimeZone zone = DateConvertLogic.get().getTimezone(getLocale(), off);
/* 212 */         userConfigs.setTimezone(zone.getDisplayName());
/*     */       } 
/*     */     } 
/* 215 */     return userConfigs;
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\knowledge\control\Control.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */