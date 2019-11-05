package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class TestPoi {
    public static void main(String[] args){
        //准备exce文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 4);
        //设置日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        //获取单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //绑定日期格式
        cellStyle.setDataFormat(format);
        //设置样式为居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建字体的对象
        HSSFFont font = workbook.createFont();
        //设置字体加粗
        font.setBold(true);
        //设置字体

    }
}
