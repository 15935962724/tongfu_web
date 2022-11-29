package com.tongfu.util;

import com.alibaba.fastjson.JSON;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    private  static Workbook workbook = null;
    private static Sheet sheet = null;
    private static FileOutputStream fileOut = null;
    public static void main(String[] args) throws IOException {

//        readExcel("C:/Users/83944/Desktop/1111.xlsx");

        String exportFilePath = "D:/writeExample.xlsx";
        File exportFile = new File(exportFilePath);
        if (!exportFile.exists()) {
            exportFile.createNewFile();
        }
        fileOut = new FileOutputStream(exportFilePath);
         workbook = new SXSSFWorkbook();
         sheet = buildDataSheet(workbook);

        test("D:/合并用");

        readExcel1("D:/合并用/【CSO公司】销售服务费明细核查-安徽源宇鑫医疗科技有限公司.xlsx");
    }

    private static String [] CELL_HEADS = {"费用发生年度","会议名称","会议类型","会议申请审批日期","会议通知日期","会议日期","参会人数（人）","参会单位","会议费","住宿费","餐费","交通费","其他"}; //列头

//    public static Workbook exportData(String [] CELL_HEADS){
//// 生成xlsx的Excel
//Workbook workbook = new SXSSFWorkbook();
//
//// 生成Sheet表，写入第一行的列头
//Sheet sheet = buildDataSheet(workbook);
////构建每行的数据内容
//int rowNum = 1;
//        for (String data :CELL_HEADS){
//            if (data == null) {
//                continue;
//            }
//            //输出行数据
//            Row row = sheet.createRow(rowNum++);
//            convertDataToRow(data, row);
//        }
////for (Iterator<ExcelDataVO> it = dataList.iterator(); it.hasNext(); ) {
////ExcelDataVO data = it.next();
////if (data == null) {
////continue;
////}
//////输出行数据
////Row row = sheet.createRow(rowNum++);
////convertDataToRow(data, row);
////}
//return workbook;
//}

       /**
   * 生成sheet表，并写入第一行数据（列头）
   * @param workbook 工作簿对象
   * @return 已经写入列头的Sheet
   */
       private static Sheet buildDataSheet(Workbook workbook) {
            Sheet sheet = workbook.createSheet();
            // 设置列头宽度
            for (int i=0; i<CELL_HEADS.length; i++) {
            sheet.setColumnWidth(i, 4000);
            }
            // 设置默认行高
            sheet.setDefaultRowHeight((short) 400);
            // 构建头单元格样式
            CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
            // 写入第一行各列的数据
            Row head = sheet.createRow(0);
            for (int i = 0; i < CELL_HEADS.length; i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(CELL_HEADS[i]);
            cell.setCellStyle(cellStyle);
            }
        return sheet;
}

        /**
   * 设置第一行列头的样式
   * @param workbook 工作簿对象
   * @return 单元格样式对象
   */
        private static CellStyle buildHeadCellStyle(Workbook workbook) {
            CellStyle style = workbook.createCellStyle();
            //对齐方式设置
            style.setAlignment(HorizontalAlignment.CENTER);
            //边框颜色和宽度设置
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
            //设置背景颜色
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //粗体字设置
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            return style;
            }

       /**
   * 将数据转换成行
   * @param data 源数据
   * @param row 行对象
   * @return
//   */
//        private static void convertDataToRow(ExcelDataVO data, Row row){
//            int cellNum = 0;
//            Cell cell;
//            // 姓名
//            cell = row.createCell(cellNum++);
//            cell.setCellValue(null == data.getName() ? "" : data.getName());
//            // 年龄
//            cell = row.createCell(cellNum++);
//            if (null != data.getAge()) {
//            cell.setCellValue(data.getAge());
//            } else {
//            cell.setCellValue("");
//            }
//            // 所在城市
//            cell = row.createCell(cellNum++);
//            cell.setCellValue(null == data.getLocation() ? "" : data.getLocation());
//            // 职业
//            cell = row.createCell(cellNum++);
//            cell.setCellValue(null == data.getJob() ? "" : data.getJob());
//            }


public static String readExcel1(String path) {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<String> activationCode = new ArrayList<>();
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
                        if (row == null && row.toString()=="") {
                            continue;
                        } else {
                            int rowNum = row.getRowNum() + 1;
//                            System.out.println("当前行:" + rowNum);

                            //获取这一行的第一列
                            int firstcell=    row.getFirstCellNum();
                            //获取这一行的最后一列
                            int lastcell=    row.getLastCellNum();
                            for (int j = firstcell; j <lastcell-2; j++) {
                                // 总列(格)
                                Cell cell = row.getCell(j);

                                if (cell!=null) {
                                    String data = cell.toString()+"\t";
                                    if (j==4){
                                        data = "0000年01月\t";
                                    }
                                    System.out.print(data);
//                                    int cellNum = 0;
//                                    Row rowA = sheet.createRow(rowNum++);
//                                    Cell cellA = rowA.createCell(cellNum++);
//                                    cellA.setCellValue(cell.toString()==""?"0":cell.toString());
//                                    workbook.write(fileOut);
//                                    fileOut.flush();
                                }
                            }


                        }
                        System.out.println();
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
//        System.out.println(JSON.toJSON(activationCode).toString());
        return null;
    }




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
            readExcel1(fileDir+f1.getName());
        }
    }



    public static String readExcel(String path) {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> activationCode = new ArrayList<>();
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
                    String cellValue = "";
                    for (int r = 0; r < rowsOfSheet; r++) {
                        Row row = sheetAt.getRow(r);
                        if (row == null) {
                            continue;
                        } else {
                            int rowNum = row.getRowNum() + 1;
                            System.out.println("当前行:" + rowNum);
                            // 总列(格)
                            Cell cell = row.getCell(0);
                            switch (cell.getCellType()) {
                                case NUMERIC: // 数字
                                    //short s = cell.getCellStyle().getDataFormat();
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                                        SimpleDateFormat sdf = null;
                                        // 验证short值
                                        if (cell.getCellStyle().getDataFormat() == 14) {
                                            sdf = new SimpleDateFormat("yyyy/MM/dd");
                                        } else if (cell.getCellStyle().getDataFormat() == 21) {
                                            sdf = new SimpleDateFormat("HH:mm:ss");
                                        } else if (cell.getCellStyle().getDataFormat() == 22) {
                                            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                        } else {
                                            throw new RuntimeException("日期格式错误!!!");
                                        }
                                        Date date = cell.getDateCellValue();
                                        cellValue = sdf.format(date);
                                    } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                                        cell.setCellType(CellType.STRING);
                                        cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                                    }
                                    break;
                                case STRING: // 字符串
                                    cellValue = String.valueOf(cell.getStringCellValue());
                                    break;
                                case BOOLEAN: // Boolean
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case FORMULA: // 公式
                                    cellValue = String.valueOf(cell.getCellFormula());
                                    break;
                                case BLANK: // 空值
                                    cellValue = "";
                                    break;
                                case ERROR: // 故障
                                    cellValue = "非法字符";
                                    break;
                                default:
                                    cellValue = "未知类型";
                                    break;
                            }

//                            System.out.println(cellValue);
                            activationCode.add(cellValue);
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
        System.out.println(JSON.toJSON(activationCode).toString());
        return JSON.toJSON(activationCode).toString();
    }

    public static String readExcel(File file) {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> activationCode = new ArrayList<>();
//        File file = new File(path);
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
                    String cellValue = "";
                    for (int r = 0; r < rowsOfSheet; r++) {
                        Row row = sheetAt.getRow(r);
                        if (row == null) {
                            continue;
                        } else {
                            int rowNum = row.getRowNum() + 1;
                            System.out.println("当前行:" + rowNum);
                            // 总列(格)
                            Cell cell = row.getCell(0);
                            switch (cell.getCellType()) {
                                case NUMERIC: // 数字
                                    //short s = cell.getCellStyle().getDataFormat();
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                                        SimpleDateFormat sdf = null;
                                        // 验证short值
                                        if (cell.getCellStyle().getDataFormat() == 14) {
                                            sdf = new SimpleDateFormat("yyyy/MM/dd");
                                        } else if (cell.getCellStyle().getDataFormat() == 21) {
                                            sdf = new SimpleDateFormat("HH:mm:ss");
                                        } else if (cell.getCellStyle().getDataFormat() == 22) {
                                            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                        } else {
                                            throw new RuntimeException("日期格式错误!!!");
                                        }
                                        Date date = cell.getDateCellValue();
                                        cellValue = sdf.format(date);
                                    } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                                        cell.setCellType(CellType.STRING);
                                        cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                                    }
                                    break;
                                case STRING: // 字符串
                                    cellValue = String.valueOf(cell.getStringCellValue());
                                    break;
                                case BOOLEAN: // Boolean
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case FORMULA: // 公式
                                    cellValue = String.valueOf(cell.getCellFormula());
                                    break;
                                case BLANK: // 空值
                                    cellValue = "";
                                    break;
                                case ERROR: // 故障
                                    cellValue = "非法字符";
                                    break;
                                default:
                                    cellValue = "未知类型";
                                    break;
                            }

//                            System.out.println(cellValue);
                            activationCode.add(cellValue);
//                            System.out.println("第" + rowNum + "行，第一列[" + title[0] + "]数据错误！");

                        }
                    }
                }
                if (fis != null) {
                    if (file != null) {
                        File del = new File(file.toURI());
                        del.delete();
                    }
                    fis.close();


                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println(JSON.toJSON(activationCode).toString());
        return JSON.toJSON(activationCode).toString();
    }



}
