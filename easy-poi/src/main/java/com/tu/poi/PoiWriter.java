package com.tu.poi;

import static com.tu.poi.enums.MSTypeEnum.XLS;
import static com.tu.poi.enums.MSTypeEnum.XLSX;

import com.tu.poi.enums.MSTypeEnum;
import com.tu.poi.transform.FieldExtractor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Tu enum@foxmail.com.
 * @since 0.1.0
 */
public class PoiWriter {

  private FieldExtractor extractor;
  private String fileName;
  private Boolean append = Boolean.TRUE;
  MSTypeEnum msTypeEnum;

  public PoiWriter(String fileName, FieldExtractor extractor) {
    if (fileName == null) {
      throw new IllegalArgumentException("fileName must be non-null");
    }
    if (extractor == null) {
      throw new IllegalArgumentException("extractor must be non-null");
    }
    this.fileName = fileName;
    msTypeEnum = MSTypeEnum.fromCode(fileName.substring(fileName.lastIndexOf(".") + 1));
    if (null == msTypeEnum) {
      throw new IllegalArgumentException("file format error");
    }
    this.extractor = extractor;
  }

  public PoiWriter append(Boolean append) {
    this.append = append;
    return this;
  }

  public int write(Object data) {
    int rowLength = 0;

    if (XLS == msTypeEnum || XLSX == msTypeEnum) {
      Workbook workbook;
      if (data instanceof List) {
        List list = (List) data;
        try (FileInputStream fs = new FileInputStream(fileName)) {
          workbook = XLS == msTypeEnum ? new HSSFWorkbook(fs) : new XSSFWorkbook(fs);
          Sheet sheet =  workbook.getSheetAt(0);
          final int preLastRowNum = append ? sheet.getLastRowNum() : 0;
          int index = 0;
          for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            index++;
            Row row = sheet.createRow(preLastRowNum + index);
            Object[] values = extractor.extract(item);
            for (int j = 0; j < values.length; j++) {
              if (null != values[j]) {
                if (values[j] instanceof Double) {
                  row.createCell(j).setCellValue((Double) values[j]);
                } else if (values[j] instanceof Date) {
                  row.createCell(j).setCellValue((Date) values[j]);
                } else if (values[j] instanceof Calendar) {
                  row.createCell(j).setCellValue((Calendar) values[j]);
                } else if (values[j] instanceof RichTextString) {
                  row.createCell(j).setCellValue((RichTextString) values[j]);
                } else if (values[j] instanceof Boolean) {
                  row.createCell(j).setCellValue((Boolean) values[j]);
                } else {
                  row.createCell(j).setCellValue(String.valueOf(values[j]));
                }
              } else {
                continue;
              }
            }
          }
          FileOutputStream fileOut = new FileOutputStream(fileName);
          workbook.write(fileOut);
          IOUtils.closeQuietly(fileOut);
        } catch (IOException e) {
          System.out.println(MessageFormat.format("excel-{0}处理出错{1}", fileName, e.getMessage()));
        }
      }
    }

    return rowLength;
  }
}
