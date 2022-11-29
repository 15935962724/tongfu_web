package com.tongfu.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class XlsxTest {

    public static void main(String[] args) {

        readExcel("C:/Users/83944/Desktop/1111.xlsx");


    }


    public static void readExcel(String path) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                    System.out.println("工作表名称：" + sheetName);
                    // 获取当前Sheet的总行数
                    int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
                    System.out.println("当前表格的总行数:" + rowsOfSheet);
                    // 第一行
                    Row row0 = sheetAt.getRow(0);
//                    int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();
//                    String[] title = new String[physicalNumberOfCells];
//                    for (int i = 0; i < physicalNumberOfCells; i++) {
//                        title[i] = row0.getCell(i).getStringCellValue();
//                    }
                    for (int r = 0; r < rowsOfSheet; r++) {
                        Row row = sheetAt.getRow(r);
                        if (row == null) {
                            continue;
                        } else {
                            int rowNum = row.getRowNum() + 1;
                            System.out.println("当前行:" + rowNum);
                            // 总列(格)
                            Cell cell0 = row.getCell(0);

                            int numericCellValue = (int) cell0.getNumericCellValue();
                            System.out.println(numericCellValue);
//                            System.out.println("第" + rowNum + "行，第一列[" + title[0] + "]数据错误！");

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
    }


}
