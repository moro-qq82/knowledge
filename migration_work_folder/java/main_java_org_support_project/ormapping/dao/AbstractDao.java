/*     */ package main_java_org_support_project.ormapping.dao;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.support.project.common.log.Log;
/*     */ import org.support.project.common.log.LogFactory;
/*     */ import org.support.project.common.util.DateUtils;
/*     */ import org.support.project.common.util.StringUtils;
/*     */ import org.support.project.ormapping.common.ResultSetLoader;
/*     */ import org.support.project.ormapping.connection.ConnectionManager;
/*     */ import org.support.project.ormapping.conv.DatabaseAccessType;
/*     */ import org.support.project.ormapping.conv.ObjectToDatabaseConv;
/*     */ import org.support.project.ormapping.conv.ObjectToDatabaseConvFactory;
/*     */ import org.support.project.ormapping.exception.ORMappingException;
/*     */ 
/*     */ public abstract class AbstractDao implements Serializable {
/*  40 */   private static Log logger = LogFactory.getLog(org.support.project.ormapping.dao.AbstractDao.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private String name;
/*     */   
/*     */   public void setConnectionName(String name) {
/*  74 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getConnectionName() {
/*  82 */     if (StringUtils.isEmpty(this.name))
/*  83 */       return ConnectionManager.getInstance().getDefaultConnectionName(); 
/*  85 */     return this.name;
/*     */   }
/*     */   
/*     */   protected Connection getConnection() {
/*  97 */     ConnectionManager connectionManager = ConnectionManager.getInstance();
/* 105 */     if (!StringUtils.isEmpty(this.name))
/* 106 */       return connectionManager.getConnection(this.name); 
/* 108 */     return connectionManager.getConnection();
/*     */   }
/*     */   
/*     */   protected void close(PreparedStatement stmt, ResultSet rs) {
/*     */     try {
/* 162 */       if (rs != null)
/* 163 */         rs.close(); 
/* 165 */       if (stmt != null)
/* 166 */         stmt.close(); 
/* 168 */     } catch (SQLException e) {
/* 169 */       throw new ORMappingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void close(PreparedStatement stmt, ResultSet rs, Connection conn) {
/*     */     try {
/* 185 */       if (rs != null)
/* 186 */         rs.close(); 
/* 188 */       if (stmt != null)
/* 189 */         stmt.close(); 
/* 191 */       if (conn != null)
/* 192 */         conn.close(); 
/* 194 */     } catch (SQLException e) {
/* 195 */       throw new ORMappingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void close(PreparedStatement stmt, Connection conn) {
/*     */     try {
/* 210 */       if (stmt != null)
/* 211 */         stmt.close(); 
/* 213 */       if (conn != null)
/* 214 */         conn.close(); 
/* 216 */     } catch (SQLException e) {
/* 217 */       throw new ORMappingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void close(Connection conn) {
/*     */     try {
/* 229 */       if (conn != null)
/* 230 */         conn.close(); 
/* 232 */     } catch (SQLException e) {
/* 233 */       throw new ORMappingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected <E> E load(ResultSet rs, Class<? extends E> class1) {
/*     */     try {
/* 259 */       if (class1.equals(Integer.class)) {
/* 260 */         Integer result = Integer.valueOf(rs.getInt(1));
/* 261 */         return (E)result;
/*     */       } 
/* 262 */       if (class1.equals(Double.class)) {
/* 263 */         Double result = Double.valueOf(rs.getDouble(1));
/* 264 */         return (E)result;
/*     */       } 
/* 265 */       if (class1.equals(Float.class)) {
/* 266 */         Float result = Float.valueOf(rs.getFloat(1));
/* 267 */         return (E)result;
/*     */       } 
/* 268 */       if (class1.equals(Long.class)) {
/* 269 */         Long result = Long.valueOf(rs.getLong(1));
/* 270 */         return (E)result;
/*     */       } 
/* 271 */       if (class1.equals(Short.class)) {
/* 272 */         Short result = Short.valueOf(rs.getShort(1));
/* 273 */         return (E)result;
/*     */       } 
/* 274 */       if (class1.equals(String.class)) {
/* 275 */         String result = rs.getString(1);
/* 276 */         return (E)result;
/*     */       } 
/* 277 */       if (class1.equals(BigDecimal.class)) {
/* 278 */         BigDecimal result = rs.getBigDecimal(1);
/* 279 */         return (E)result;
/*     */       } 
/* 280 */       if (class1.equals(Blob.class)) {
/* 281 */         Blob result = rs.getBlob(1);
/* 282 */         return (E)result;
/*     */       } 
/* 283 */       if (class1.equals(Boolean.class)) {
/* 284 */         Boolean result = Boolean.valueOf(rs.getBoolean(1));
/* 285 */         return (E)result;
/*     */       } 
/* 286 */       if (class1.equals(Byte.class)) {
/* 287 */         Byte result = Byte.valueOf(rs.getByte(1));
/* 288 */         return (E)result;
/*     */       } 
/* 289 */       if (class1.equals(Timestamp.class)) {
/* 290 */         Timestamp result = rs.getTimestamp(1);
/* 291 */         return (E)result;
/*     */       } 
/* 292 */       if (class1.equals(Time.class)) {
/* 293 */         Time result = rs.getTime(1);
/* 294 */         return (E)result;
/*     */       } 
/* 295 */       if (class1.equals(Date.class)) {
/* 296 */         Date result = rs.getDate(1);
/* 297 */         return (E)result;
/*     */       } 
/* 298 */       if (class1.equals(Date.class)) {
/* 299 */         Date result = rs.getDate(1);
/* 300 */         return (E)result;
/*     */       } 
/* 303 */       E object = class1.newInstance();
/* 304 */       load(rs, object);
/* 305 */       return object;
/* 306 */     } catch (InstantiationException|IllegalAccessException|SQLException e) {
/* 307 */       throw new ORMappingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void load(ResultSet rs, Object object) {
/* 320 */     String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
/* 321 */     ResultSetLoader.load(rs, object, driverClass);
/*     */   }
/*     */   
/*     */   private <E> E executeQueryOnKey(String sql, Class<? extends E> class1, Object... params) {
/* 333 */     Connection con = null;
/* 334 */     PreparedStatement stmt = null;
/* 335 */     ResultSet rs = null;
/*     */     try {
/* 337 */       con = getConnection();
/* 338 */       stmt = con.prepareStatement(sql);
/* 339 */       StringBuilder builder = new StringBuilder();
/* 340 */       builder.append("[connection]").append(con.hashCode());
/* 341 */       builder.append("[executeQuery]").append(sql).append("\n     ");
/* 342 */       if (params != null) {
/* 343 */         int count = 1;
/* 344 */         for (Object param : params) {
/* 345 */           String result = setParam(stmt, param, count);
/* 346 */           builder.append(result);
/* 347 */           count++;
/*     */         } 
/*     */       } 
/* 350 */       logger.debug(builder.toString());
/* 351 */       rs = stmt.executeQuery();
/* 353 */       if (rs.next()) {
/* 354 */         E obj = load(rs, class1);
/* 355 */         return obj;
/*     */       } 
/* 357 */       return null;
/* 358 */     } catch (SQLException e) {
/* 359 */       ORMappingException exception = new ORMappingException(e);
/* 360 */       exception.setSql(sql);
/* 361 */       exception.setParams(params);
/* 362 */       throw exception;
/*     */     } finally {
/* 364 */       close(stmt, rs, con);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <E> List<E> executeQuery(String sql, Class<? extends E> class1, Object... params) {
/* 377 */     Connection con = null;
/* 378 */     PreparedStatement stmt = null;
/* 379 */     ResultSet rs = null;
/*     */     try {
/* 381 */       con = getConnection();
/* 382 */       stmt = con.prepareStatement(sql);
/* 383 */       StringBuilder builder = new StringBuilder();
/* 384 */       builder.append("[connection]").append(con.hashCode());
/* 385 */       builder.append("[executeQuery]").append(sql).append("\n     ");
/* 386 */       if (params != null) {
/* 387 */         int count = 1;
/* 388 */         for (Object param : params) {
/* 389 */           String result = setParam(stmt, param, count);
/* 390 */           builder.append(result);
/* 391 */           count++;
/*     */         } 
/*     */       } 
/* 394 */       logger.debug(builder.toString());
/* 395 */       rs = stmt.executeQuery();
/* 397 */       List<E> list = new ArrayList<>();
/* 398 */       while (rs.next()) {
/* 399 */         E obj = load(rs, class1);
/* 400 */         list.add(obj);
/*     */       } 
/* 402 */       return list;
/* 403 */     } catch (SQLException e) {
/* 404 */       ORMappingException exception = new ORMappingException(e);
/* 405 */       exception.setSql(sql);
/* 406 */       exception.setParams(params);
/* 407 */       throw exception;
/*     */     } finally {
/* 409 */       close(stmt, rs, con);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected <E> E executeQuerySingle(String sql, Class<? extends E> class1, Object... params) {
/* 426 */     return executeQueryOnKey(sql, class1, params);
/*     */   }
/*     */   
/*     */   protected <E> List<E> executeQueryList(String sql, Class<? extends E> class1, Object... params) {
/* 442 */     return executeQuery(sql, class1, params);
/*     */   }
/*     */   
/*     */   protected int executeUpdate(String sql, Object... params) {
/* 455 */     Connection con = null;
/* 456 */     PreparedStatement stmt = null;
/*     */     try {
/* 458 */       con = getConnection();
/* 460 */       stmt = con.prepareStatement(sql);
/* 461 */       StringBuilder builder = new StringBuilder();
/* 462 */       builder.append("[connection]").append(con.hashCode());
/* 463 */       builder.append("[executeUpdate]").append(sql).append("\n     ");
/* 464 */       if (params != null) {
/* 465 */         int i = 1;
/* 466 */         for (Object param : params) {
/* 467 */           String result = setParam(stmt, param, i);
/* 468 */           builder.append(result);
/* 469 */           i++;
/*     */         } 
/*     */       } 
/* 472 */       logger.debug(builder.toString());
/* 473 */       int count = stmt.executeUpdate();
/* 475 */       return count;
/* 476 */     } catch (Exception e) {
/* 477 */       if (con != null)
/*     */         try {
/* 479 */           con.rollback();
/* 480 */         } catch (SQLException e1) {
/* 481 */           throw new ORMappingException(e);
/*     */         }  
/* 484 */       ORMappingException exception = new ORMappingException(e);
/* 485 */       exception.setSql(sql);
/* 486 */       exception.setParams(params);
/* 487 */       throw exception;
/*     */     } finally {
/* 489 */       close(stmt, con);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object executeInsert(String sql, Class<?> type, Object... params) {
/* 504 */     Connection con = null;
/* 505 */     PreparedStatement stmt = null;
/*     */     try {
/* 507 */       con = getConnection();
/* 509 */       stmt = con.prepareStatement(sql, 1);
/* 510 */       StringBuilder builder = new StringBuilder();
/* 511 */       builder.append("[connection]").append(con.hashCode());
/* 512 */       builder.append("[executeUpdate]").append(sql).append("\n     ");
/* 513 */       if (params != null) {
/* 514 */         int count = 1;
/* 515 */         for (Object param : params) {
/* 516 */           String result = setParam(stmt, param, count);
/* 517 */           builder.append(result);
/* 518 */           count++;
/*     */         } 
/*     */       } 
/* 521 */       logger.debug(builder.toString());
/* 522 */       stmt.executeUpdate();
/* 525 */       ResultSet keys = stmt.getGeneratedKeys();
/* 526 */       Object value = null;
/* 527 */       if (keys.next()) {
/* 528 */         ObjectToDatabaseConv conv = ObjectToDatabaseConvFactory.getObjectToDatabaseConv(keys);
/* 529 */         DatabaseAccessType accessType = conv.getObjectToDatabaseType(type);
/* 530 */         if (accessType == DatabaseAccessType.Int) {
/* 531 */           value = Integer.valueOf(keys.getInt(1));
/* 532 */         } else if (accessType == DatabaseAccessType.Long) {
/* 533 */           value = Long.valueOf(keys.getLong(1));
/* 534 */         } else if (accessType == DatabaseAccessType.Short) {
/* 535 */           value = Short.valueOf(keys.getShort(1));
/*     */         } else {
/* 537 */           logger.warn("オートインクリメントは、Int,Long,Shortの型のみ対応しています");
/*     */         } 
/*     */       } 
/* 541 */       return value;
/* 542 */     } catch (Exception e) {
/* 543 */       if (con != null)
/*     */         try {
/* 545 */           con.rollback();
/* 546 */         } catch (SQLException e1) {
/* 547 */           throw new ORMappingException(e);
/*     */         }  
/* 550 */       ORMappingException exception = new ORMappingException(e);
/* 551 */       exception.setSql(sql);
/* 552 */       exception.setParams(params);
/* 553 */       throw exception;
/*     */     } finally {
/* 555 */       close(stmt, con);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String setParam(PreparedStatement stmt, Object param, int count) throws SQLException {
/* 572 */     StringBuilder builder = new StringBuilder();
/* 573 */     builder.append("[param-").append(count).append("] ");
/* 574 */     if (param == null) {
/* 575 */       stmt.setNull(count, 0);
/* 576 */       builder.append("Null@Null");
/* 578 */     } else if (param.getClass().isPrimitive()) {
/* 579 */       if (param.getClass().equals(short.class)) {
/* 580 */         stmt.setShort(count, ((Short)param).shortValue());
/* 581 */         builder.append("Short@").append(param);
/* 582 */       } else if (param.getClass().equals(int.class)) {
/* 583 */         stmt.setInt(count, ((Integer)param).intValue());
/* 584 */         builder.append("Int@").append(param);
/* 585 */       } else if (param.getClass().equals(long.class)) {
/* 586 */         stmt.setLong(count, ((Long)param).longValue());
/* 587 */         builder.append("Long@").append(param);
/* 588 */       } else if (param.getClass().equals(float.class)) {
/* 589 */         stmt.setFloat(count, ((Float)param).floatValue());
/* 590 */         builder.append("Float@").append(param);
/* 591 */       } else if (param.getClass().equals(double.class)) {
/* 592 */         stmt.setDouble(count, ((Double)param).doubleValue());
/* 593 */         builder.append("Double@").append(param);
/* 594 */       } else if (param.getClass().equals(boolean.class)) {
/* 595 */         stmt.setBoolean(count, ((Boolean)param).booleanValue());
/* 596 */         builder.append("Boolean@").append(param);
/*     */       } else {
/* 598 */         throw new ORMappingException("この型は未対応 : " + param.getClass().getName());
/*     */       } 
/* 601 */     } else if (param instanceof Blob) {
/* 603 */       stmt.setBlob(count, (Blob)param);
/* 604 */       builder.append("Blob@").append("Binary");
/* 605 */     } else if (param instanceof Boolean) {
/* 606 */       stmt.setBoolean(count, ((Boolean)param).booleanValue());
/* 607 */       builder.append("Boolean@").append(param);
/* 608 */     } else if (param instanceof Clob) {
/* 610 */       stmt.setClob(count, (Clob)param);
/* 611 */       builder.append("Clob@").append("Text");
/* 612 */     } else if (param instanceof Date) {
/* 613 */       Date date = (Date)param;
/* 614 */       stmt.setTimestamp(count, new Timestamp(date.getTime()));
/* 615 */       builder.append("Timestamp@").append(DateUtils.formatTransferDateTime(date));
/* 616 */     } else if (param instanceof Date) {
/* 617 */       stmt.setDate(count, (Date)param);
/* 618 */       builder.append("Date@").append(DateUtils.formatTransferDateTime((Date)param));
/* 619 */     } else if (param instanceof BigDecimal) {
/* 620 */       stmt.setBigDecimal(count, (BigDecimal)param);
/* 621 */       builder.append("BigDecimal@").append(param);
/* 622 */     } else if (param instanceof Double) {
/* 623 */       stmt.setDouble(count, ((Double)param).doubleValue());
/* 624 */       builder.append("Double@").append(param);
/* 625 */     } else if (param instanceof Float) {
/* 626 */       stmt.setFloat(count, ((Float)param).floatValue());
/* 627 */       builder.append("Float@").append(param);
/* 628 */     } else if (param instanceof Integer) {
/* 629 */       stmt.setInt(count, ((Integer)param).intValue());
/* 630 */       builder.append("Int@").append(param);
/* 631 */     } else if (param instanceof Long) {
/* 632 */       stmt.setLong(count, ((Long)param).longValue());
/* 633 */       builder.append("Long@").append(param);
/* 634 */     } else if (param instanceof Time) {
/* 635 */       stmt.setTime(count, (Time)param);
/* 636 */       builder.append("Time@").append(param);
/* 637 */     } else if (param instanceof Timestamp) {
/* 638 */       stmt.setTimestamp(count, (Timestamp)param);
/* 639 */       builder.append("Timestamp@").append(DateUtils.formatTransferDateTime((Timestamp)param));
/* 640 */     } else if (param instanceof String) {
/* 641 */       stmt.setString(count, (String)param);
/* 642 */       builder.append("String@").append(param);
/* 643 */     } else if (param instanceof InputStream) {
/*     */       try {
/* 645 */         InputStream inputStream = (InputStream)param;
/* 646 */         stmt.setBinaryStream(count, inputStream, inputStream.available());
/* 647 */         builder.append("BinaryStream@").append("InputStream");
/* 648 */       } catch (IOException e) {
/* 649 */         throw new ORMappingException(e);
/*     */       } 
/*     */     } 
/* 654 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\moro-\OneDrive\ドキュメント\21_仕事\knowledge_war_展開\class\!\main_java_org_support_project\ormapping\dao\AbstractDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */