/*     */ package main_java_org_support_project.web.logic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.support.project.common.log.Log;
/*     */ import org.support.project.common.log.LogFactory;
/*     */ import org.support.project.common.util.StringUtils;
/*     */ import org.support.project.common.util.SystemUtils;
/*     */ import org.support.project.di.Container;
/*     */ import org.support.project.di.DI;
/*     */ import org.support.project.di.Instance;
/*     */ import org.support.project.web.bean.Batchinfo;
/*     */ import org.support.project.web.config.AppConfig;
/*     */ 
/*     */ @DI(instance = Instance.Singleton)
/*     */ public class ScheduledBatchLogic {
/*  30 */   private static final Log LOG = LogFactory.getLog(org.support.project.web.logic.ScheduledBatchLogic.class);
/*     */   
/*     */   private ScheduledThreadPoolExecutor service;
/*     */   
/*     */   public static org.support.project.web.logic.ScheduledBatchLogic get() {
/*  38 */     return (org.support.project.web.logic.ScheduledBatchLogic)Container.getComp(org.support.project.web.logic.ScheduledBatchLogic.class);
/*     */   }
/*     */   
/*  44 */   private List<ScheduledFuture<?>> futures = new ArrayList<>();
/*     */   
/*     */   public void scheduleInitialize() {
/*  52 */     this.service = new ScheduledThreadPoolExecutor(1);
/*  53 */     List<Batchinfo> batchs = AppConfig.get().getBatchs();
/*  54 */     for (Batchinfo batchinfo : batchs) {
/*  55 */       if (batchinfo.getType() != null && batchinfo.getType().toLowerCase().equals("java")) {
/*  56 */         ScheduledFuture<?> future = createJavaBatch(batchinfo);
/*  57 */         this.futures.add(future);
/*     */         continue;
/*     */       } 
/*  60 */       LOG.warn("batch type [" + batchinfo.getType() + "] is not impl.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private ScheduledFuture createJavaBatch(Batchinfo batchinfo) {
/*  72 */     String envValue = SystemUtils.getenv(AppConfig.getEnvKey());
/*  73 */     int waitStart = this.futures.size() * 2 + 1;
/*  75 */     TimeUnit timeUnit = TimeUnit.MINUTES;
/*  76 */     if (StringUtils.isNotEmpty(batchinfo.getTimeUnit())) {
/*  77 */       String unit = batchinfo.getTimeUnit().toUpperCase();
/*  78 */       if (unit.equals("MILLISECONDS")) {
/*  79 */         timeUnit = TimeUnit.MILLISECONDS;
/*  80 */       } else if (unit.equals("SECONDS")) {
/*  81 */         timeUnit = TimeUnit.SECONDS;
/*  82 */       } else if (unit.equals("HOURS")) {
/*  83 */         timeUnit = TimeUnit.HOURS;
/*  84 */       } else if (unit.equals("DAYS")) {
/*  85 */         timeUnit = TimeUnit.DAYS;
/*     */       } 
/*     */     } 
/*  89 */     ScheduledFuture<?> future = this.service.scheduleAtFixedRate((Runnable)new Object(this, batchinfo, envValue), waitStart, batchinfo
/*     */         
/* 128 */         .getTerm().intValue(), timeUnit);
/* 130 */     LOG.info("Add batch program. [" + batchinfo.getName() + "] " + batchinfo.getCommand());
/* 132 */     return future;
/*     */   }
/*     */   
/*     */   public void scheduleDestroy() {
/* 139 */     LOG.info("finish batch processes.");
/* 140 */     for (ScheduledFuture<?> scheduledFuture : this.futures) {
/*     */       try {
/* 142 */         scheduledFuture.cancel(true);
/* 143 */         scheduledFuture.get();
/* 144 */       } catch (Exception e) {
/* 145 */         LOG.debug("An error has occurred in the end processing of the batch", e);
/*     */       } 
/*     */     } 
/*     */     try {
/* 149 */       this.service.shutdown();
/* 150 */     } catch (Exception e) {
/* 151 */       LOG.debug("An error has occurred in the end processing of the batch", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\web\logic\ScheduledBatchLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */