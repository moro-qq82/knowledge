/*     */ package main_java_org_support_project.ormapping.dao;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.support.project.common.exception.SystemException;
/*     */ import org.support.project.common.log.Log;
/*     */ import org.support.project.common.log.LogFactory;
/*     */ import org.support.project.di.Container;
/*     */ import org.support.project.ormapping.connection.ConnectionManager;
/*     */ import org.support.project.ormapping.dao.AbstractDao;
/*     */ import org.support.project.ormapping.entity.ColumnDefinition;
/*     */ import org.support.project.ormapping.entity.TableDefinition;
/*     */ import org.support.project.ormapping.exception.ORMappingException;
/*     */ 
/*     */ public class DatabaseMetaDataDao extends AbstractDao {
/*  31 */   private static final Log LOG = LogFactory.getLog(org.support.project.ormapping.dao.DatabaseMetaDataDao.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private Map<String, TableDefinition> tableMap;
/*     */   
/*     */   public Collection<TableDefinition> getTableInfos() {
/*  44 */     return this.tableMap.values();
/*     */   }
/*     */   
/*     */   public static org.support.project.ormapping.dao.DatabaseMetaDataDao get() {
/*  51 */     return (org.support.project.ormapping.dao.DatabaseMetaDataDao)Container.getComp(org.support.project.ormapping.dao.DatabaseMetaDataDao.class);
/*     */   }
/*     */   
/*     */   public DatabaseMetaDataDao() {
/*  58 */     this.tableMap = new HashMap<>();
/*     */   }
/*     */   
/*     */   public void dbAnalysis() {
/*  65 */     Connection con = null;
/*  66 */     PreparedStatement stmt = null;
/*  67 */     ResultSet rs = null;
/*     */     try {
/*  69 */       con = getConnection();
/*  71 */       List<TableDefinition> tables = readTableDefinition(con);
/*  72 */       for (TableDefinition tableDefinition : tables) {
/*  74 */         List<ColumnDefinition> columns = readColumnDefinition(con, tableDefinition);
/*  75 */         tableDefinition.setColumns(columns);
/*  76 */         this.tableMap.put(tableDefinition.getTable_name(), tableDefinition);
/*     */       } 
/*  78 */     } catch (NoSuchMethodException|SQLException e) {
/*  79 */       throw new ORMappingException(e);
/*     */     } finally {
/*  81 */       close(stmt, rs, con);
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<TableDefinition> readTableDefinition(Connection con) throws SQLException, SystemException, NoSuchMethodException {
/*  94 */     List<TableDefinition> definitions = new ArrayList<>();
/*  95 */     PreparedStatement stmt = null;
/*  96 */     ResultSet rs = null;
/*     */     try {
/*  98 */       DatabaseMetaData databaseMetaData = con.getMetaData();
/* 100 */       String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
/* 101 */       if ("org.h2.Driver".equals(driverClass)) {
/* 103 */         rs = databaseMetaData.getTables(null, ConnectionManager.getInstance().getSchema().toUpperCase(), "%", null);
/*     */       } else {
/* 106 */         rs = databaseMetaData.getTables(null, ConnectionManager.getInstance().getSchema(), "%", null);
/*     */       } 
/* 109 */       while (rs.next()) {
/* 110 */         TableDefinition definition = (TableDefinition)load(rs, TableDefinition.class);
/* 111 */         if (!"INDEX".equals(definition.getTable_type()) && !"SEQUENCE".equals(definition.getTable_type())) {
/* 112 */           LOG.info("Read Table info: " + definition.getTable_name());
/* 113 */           definitions.add(definition);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 117 */       close(stmt, rs);
/*     */     } 
/* 119 */     return definitions;
/*     */   }
/*     */   
/*     */   private List<ColumnDefinition> readColumnDefinition(Connection con, TableDefinition tableDefinition) throws SQLException, SystemException, NoSuchMethodException {
/* 134 */     LOG.debug("Read Column info on Table: " + tableDefinition.getTable_name());
/* 136 */     List<ColumnDefinition> columns = new ArrayList<>();
/* 137 */     PreparedStatement stmt = null;
/* 138 */     ResultSet rs = null;
/*     */     try {
/* 140 */       DatabaseMetaData databaseMetaData = con.getMetaData();
/* 147 */       List<String> keys = new ArrayList<>();
/* 149 */       String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
/* 150 */       if ("org.h2.Driver".equals(driverClass)) {
/* 152 */         rs = databaseMetaData.getPrimaryKeys(null, ConnectionManager.getInstance().getSchema().toUpperCase(), tableDefinition
/* 153 */             .getTable_name());
/*     */       } else {
/* 156 */         rs = databaseMetaData.getPrimaryKeys(null, ConnectionManager.getInstance().getSchema(), tableDefinition.getTable_name());
/*     */       } 
/* 158 */       while (rs.next()) {
/* 159 */         String pkColumn = rs.getString("COLUMN_NAME");
/* 160 */         keys.add(pkColumn);
/*     */       } 
/* 162 */       rs.close();
/* 164 */       if ("org.h2.Driver".equals(driverClass)) {
/* 166 */         rs = databaseMetaData.getColumns(null, ConnectionManager.getInstance().getSchema().toUpperCase(), tableDefinition.getTable_name(), "%");
/*     */       } else {
/* 170 */         rs = databaseMetaData.getColumns("", ConnectionManager.getInstance().getSchema(), tableDefinition.getTable_name(), "%");
/*     */       } 
/* 173 */       while (rs.next()) {
/* 174 */         ColumnDefinition definition = (ColumnDefinition)load(rs, ColumnDefinition.class);
/* 175 */         columns.add(definition);
/* 177 */         LOG.debug("Read Column info : " + tableDefinition.getTable_name() + "#" + definition.getColumn_name());
/* 179 */         boolean key = false;
/* 180 */         int idx = -1;
/* 181 */         for (int i = 0; i < keys.size(); i++) {
/* 182 */           String keyName = keys.get(i);
/* 183 */           if (keyName.equals(definition.getColumn_name())) {
/* 184 */             key = true;
/* 185 */             idx = i;
/*     */             break;
/*     */           } 
/*     */         } 
/* 189 */         definition.setPrimary(key);
/* 190 */         definition.setPrimary_no(idx);
/*     */       } 
/*     */     } finally {
/* 193 */       close(stmt, rs);
/*     */     } 
/* 195 */     return columns;
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\ormapping\dao\DatabaseMetaDataDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */