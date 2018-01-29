
import org.apache.log4j.Logger;

public class CC  {
    private static Logger logger = Logger.getLogger(CC.class);

//    @Override
//    public List<ModelInfo> loadModelInfo(ConnectConfig connectConfig) {
//        ModelInfo.tableNamePex = connectConfig.getTableNamePattern();
//        logger.info("表前缀：{}", ModelInfo.tableNamePex);
//
//        ModelInfo.sEQNamePex = connectConfig.getsEQNamePattern();
//        logger.info("序列前缀：{}", ModelInfo.sEQNamePex);
//
//        List<ModelInfo> mList = new ArrayList<ModelInfo>();
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        logger.info("驱动类：{}", connectConfig.getDriverClassName());
//        dataSource.setDriverClassName(connectConfig.getDriverClassName());
//
//        logger.info("url:{}", connectConfig.getUrl());
//        dataSource.setUrl(connectConfig.getUrl());
//
//        logger.info("userName:{}", connectConfig.getUsername());
//        dataSource.setUsername(connectConfig.getUsername());
//
//        logger.info("password:{}", connectConfig.getPassword());
//        dataSource.setPassword(connectConfig.getPassword());
//
//        Properties pro = connectConfig.getConnectionPros();
//
//        logger.info("设置连接属性：{}", pro.stringPropertyNames());
//        dataSource.setConnectionProperties(pro);
//
//        Connection conn = null;
//        //表查询结果集
//        ResultSet tableRS = null;
//
//        try {
//            conn = dataSource.getConnection();
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//
//            logger.info("Catalog:{}", connectConfig.getCatalog());
//            logger.info("SchemaPattern:{}", connectConfig.getSchemaPattern());
//            logger.info("TableNamePattern:{}", connectConfig.getTableNamePattern());
//            logger.info("Types:{}", Arrays.toString(connectConfig.getTypes()));
//
//            //获取表的结果集
//            tableRS = dbMetaData.getTables(connectConfig.getCatalog(), connectConfig.getSchemaPattern(), connectConfig.getTableNamePattern(),
//                    connectConfig.getTypes());
//
//            while (tableRS.next()) {
//                String tableName = tableRS.getString("TABLE_NAME");
//                logger.info("tableName:{}", tableName);
//                if (tableName.indexOf("$") == -1 && !connectConfig.getExclusionMap().containsKey(tableName)) {
//                    if (ConfigUtil.getConfig(tableName) == null) {
//                        continue;
//                    }
//                    ModelInfo modelInfo = new ModelInfo();
//                    TableInfo tableInfo = new TableInfo();
//                    tableInfo.setName(tableName);
//
//                    logger.info("表{}的注释:{}", tableName, tableRS.getString("REMARKS"));
//                    tableInfo.setRemark(tableRS.getString("REMARKS"));
//
//                    logger.info("表{}的类型:{}", tableName, tableRS.getString("TABLE_TYPE"));
//                    tableInfo.setType(tableRS.getString("TABLE_TYPE"));
//
//                    //获取列的结果集
//                    ResultSet columnRS = dbMetaData.getColumns(null, connectConfig.getSchemaPattern(), tableName, "%");
//
//                    List<ColumnInfo> ciList = new ArrayList<ColumnInfo>();
//                    while (columnRS.next()) {
//                        String columnName = columnRS.getString("COLUMN_NAME");
//                        logger.info("字段名称：{}", columnName);
//
//                        String columnType = columnRS.getString("TYPE_NAME").toUpperCase();
//                        logger.info("字段类型：{}", columnType);
//
//                        String columnRemark = columnRS.getString("REMARKS");
//                        logger.info("字段注释：{}", columnRemark);
//
//                        int len = columnRS.getInt("COLUMN_SIZE");
//                        logger.info("字段长度：{}", len);
//
//                        int precision = columnRS.getInt("DECIMAL_DIGITS");
//                        logger.info("字段类型精度：{}", precision);
//
//                        if (columnName == null || "".equals(columnName)) {
//                            continue;
//                        }
//
//                        ColumnInfo ci = new ColumnInfo();
//                        ci.setName(columnName);
//                        ci.setType(columnType);
//                        ci.setRemark(columnRemark);
//                        ci.setLen(len);
//                        ci.setPrecision(precision);
//
//                        ciList.add(ci);
//                    }
//                    tableInfo.setColumnList(ciList);
//                    modelInfo.setmTable(tableInfo, connectConfig.getIdMap());
//                    mList.add(modelInfo);
//                    columnRS.close();
//                }
//            }
//            tableRS.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("获取表、字段相关信息异常。。", e);
//        } finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return mList;
//    }

}