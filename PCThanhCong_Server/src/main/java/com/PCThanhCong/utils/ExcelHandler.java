package com.PCThanhCong.utils;

import com.PCThanhCong.entity.OrderItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class ExcelHandler {

    private final Environment env;

    public ExcelHandler(Environment env) {
        this.env = env;
    }

    public String writeReportToExcel(List<OrderItem> orderItemList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Doanh thu");
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 20000);
        sheet.setColumnWidth(2, 15000);
        sheet.setColumnWidth(3, 20000);
        sheet.setColumnWidth(4, 20000);
        sheet.setColumnWidth(5, 20000);
        sheet.setColumnWidth(6, 15000);
        sheet.setColumnWidth(7, 20000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        String headers[] = new String[]{"STT", "Product name", "Amount", "Price", "Total", "Sale date", "Client", "Telephone"};
        int idx = 0;
        for (String h : headers) {
            Cell headerCell = header.createCell(idx++);
            headerCell.setCellValue(h);
            headerCell.setCellStyle(headerStyle);
        }

        idx = 1;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        Set<String> sets = new HashSet<>();
        for (OrderItem item : orderItemList) {
            Row row = sheet.createRow(idx++);

            Cell cell = row.createCell(0);
            cell.setCellValue(idx);
            cell.setCellStyle(style);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(item.getProduct().getTitle());
            cell1.setCellStyle(style);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(item.getAmount());
            cell2.setCellStyle(style);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(item.getProduct().getPromotionPrice());
            cell3.setCellStyle(style);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(item.getAmount() * item.getProduct().getPromotionPrice());
            cell4.setCellStyle(style);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue(DateTimeUtils.format(item.getReceivedDate() , DateTimeUtils.HHmmss_ddMMyyyy));
            cell5.setCellStyle(style);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue(item.getOrder().getUser().getFullName());
            cell6.setCellStyle(style);

            Cell cell7 = row.createCell(7);
            cell7.setCellValue(item.getOrder().getUser().getPhoneNumber());
            cell7.setCellStyle(style);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        File currDir = new File("");
        String path = env.getProperty("pathTemp");
        String fileLocation = path + "doanhThu.xlsx";
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();


        FileInputStream inputStream = new FileInputStream(fileLocation);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            bos.write(buf, 0, len);
        }
        inputStream.close();
        byte[] fileBytes = bos.toByteArray();
        String base64String = Base64.getEncoder().encodeToString(fileBytes);
        return base64String;
    }
}
