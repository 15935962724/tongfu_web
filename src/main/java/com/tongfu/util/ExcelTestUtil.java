package com.tongfu.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelTestUtil {

    public static void main(String[] args) {

//        readExcel("C:/Users/83944/Desktop/1111.xlsx");
        readExcel1("D:/合并用/【CSO公司】销售服务费明细核查-蒋庆友.xlsx");

    }

    public static String readExcel1(String path) {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(path);
        FileInputStream fis = null;
        Workbook workBook = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                workBook = WorkbookFactory.create(fis);
//                int numberOfSheets = workBook.getNumberOfSheets();
                int numberOfSheets = 1;//读取第一个sheet工作表的内容
                Sheet sheetAt = workBook.getSheetAt(0);
                //获取工作表名称
                String sheetName = sheetAt.getSheetName();
                System.out.println("工作表名称：" + sheetName);
                // 获取当前Sheet的总行数
                int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
                System.out.println("当前表格的总行数:" + rowsOfSheet);
                //获取第一行
                int firstrow=    5;
                //获取最后一行
                int lastrow=    sheetAt.getLastRowNum();
                for (int i = firstrow; i < lastrow+1; i++) {
                    //获取哪一行i
                    System.out.println("第"+i+"行数据");
                    Row row = sheetAt.getRow(i);
                        //获取这一行的第一列
                        int firstcell = row.getFirstCellNum()+1;
                        //获取这一行的最后一列
                        int lastcell = row.getLastCellNum();
                        for (int j = firstcell; j <lastcell-2; j++) {
                            //获取第j列
                            Cell cell=row.getCell(j);
                            System.out.print(cell+"\t");
//                                list.add(cell.toString());

                        }
                    System.out.println();

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在!");
        }
        return "";
    }



}
