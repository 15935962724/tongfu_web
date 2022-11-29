package com.tongfu.util;

import com.alibaba.druid.sql.visitor.functions.Substring;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtils {


    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://106.13.123.1:3306/tongfu";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "Test@123";

    private static void test(String fileDir) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(fileDir);
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        if (files == null) {// 如果目录为空，直接退出
            return;
        }
        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
//                System.out.println(f.getAbsolutePath());
                test(f.getAbsolutePath());
            }
        }
        for (File f1 : fileList) {
//            System.out.println(f1.getName());
//            insertSql(fileDir + f1.getName());
            readExcel1("D:/合并用/"+f1.getName());
        }
    }


    public static String readExcel1(String path) {



        Connection conn = null;
        PreparedStatement pst = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();


            File file = new File(path);
            FileInputStream fis = null;
            Workbook workBook = null;
            if (file.exists()) {
                try {
                    fis = new FileInputStream(file);
                    workBook = WorkbookFactory.create(fis);
//                int numberOfSheets = workBook.getNumberOfSheets();
                    int numberOfSheets = 1;//读取第一个sheet工作表的内容


                    // sheet工作表
                    for (int s = 0; s < numberOfSheets; s++) {
                        Sheet sheetAt = workBook.getSheetAt(s);
                        //获取工作表名称
                        String sheetName = sheetAt.getSheetName();
//                    System.out.println("工作表名称：" + sheetName);
                        // 获取当前Sheet的总行数
                        int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
//                    System.out.println("当前表格的总行数:" + rowsOfSheet);
                        // 第一行
                        Row row0 = sheetAt.getRow(0);
//                    int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();
//                    String[] title = new String[physicalNumberOfCells];
//                    for (int i = 0; i < physicalNumberOfCells; i++) {
//                        title[i] = row0.getCell(i).getStringCellValue();
//                    }
                        String cellValue = "";
                        for (int r = 4; r < rowsOfSheet; r++) {
                            Row row = sheetAt.getRow(r);
                            if (row == null && row.toString() == "") {
                                continue;
                            } else {
                                int rowNum = row.getRowNum() + 1;
//                            System.out.println("当前行:" + rowNum);

                                //获取这一行的第一列
                                int firstcell = row.getFirstCellNum()+1;
                                //获取这一行的最后一列
                                int lastcell = row.getLastCellNum();
                                String[] datas = new String[lastcell];
                                for (int j = firstcell; j < lastcell - 2; j++) {
                                    // 总列(格)
                                    Cell cell = row.getCell(j);

                                    if (cell != null) {
                                        String data = cell.toString()==""?"0":cell.toString();

                                        datas[j] = data;
//                                        System.out.print(data);
                                    }
                                }
                String sql = "insert into `test` (`name`, `year`, `meeting`, `meeting_type`, `apply_date`, `notice_date`, `meeting_date`, `attendance_num`, `attendance_titel`, `meeting_price`, `hotel_expense`, `meal_expenses`, `traffic_expense`, `other_expenses`)" +
                        " values('"+datas[1]+"','"+datas[2]+"','"+datas[3]+"'," +
                        "'"+datas[4]+"','"+datas[5]+"','"+datas[6]+"','"+datas[7]+"'," +
                        "'"+datas[8]+"','"+datas[9]+"','"+datas[10]+"','"+datas[11]+"'," +
                        "'"+datas[12]+"','"+datas[13]+"','"+datas[14]+"');";
//                String sql = "INSERT INTO `tf_hospital` ( `name`,`create_date`, `hospital_rank_id`, `hospital_category_id`, `area_name`) " +
//                        "VALUES('"+values[1]+"','2015-11-10 00:03:52',"+values[2]+","+values[3]+",'"+values[4]+"');";
                System.out.println(sql);
//                insertSql(sql);
                                stmt.executeUpdate(sql);
                            }
                        }
                    }
                    if (fis != null) {
                        fis.close();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            } else {
                System.out.println("文件不存在!");
            }


            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

//        System.out.println(JSON.toJSON(activationCode).toString());
            return null;

    }
        public static void insertSql (String sql){
            Connection conn = null;
            PreparedStatement pst = null;
            Statement stmt = null;
            try {
                // 注册 JDBC 驱动
                Class.forName(JDBC_DRIVER);

                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // 执行查询
                System.out.println(" 实例化Statement对象...");
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                stmt.close();
                conn.close();
            } catch (SQLException se) {
                // 处理 JDBC 错误
                se.printStackTrace();
            } catch (Exception e) {
                // 处理 Class.forName 错误
                e.printStackTrace();
            } finally {
                // 关闭资源
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se2) {
                }// 什么都不做
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            System.out.println("Goodbye!");
        }

        public static void main (String[]args){
            test("D:/合并用/");

        }
}
