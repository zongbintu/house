package com.tu.poi;

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
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

/**
 * @author Tu enum@foxmail.com.
 * @since 0.1.0
 */
public class PoiWriter<T> {

  private FieldExtractor extractor;
  private String fileName;
  private Boolean append = Boolean.TRUE;

  public PoiWriter(String fileName, FieldExtractor extractor) {
    if (fileName == null) {
      throw new IllegalArgumentException("fileName must be non-null");
    }
    if (extractor == null) {
      throw new IllegalArgumentException("extractor must be non-null");
    }
    this.fileName = fileName;
    this.extractor = extractor;
  }

  public PoiWriter append(Boolean append) {
    this.append = append;
    return this;
  }

  public int writeExcel(List<T> list) {
    int rowLength = 0;
    try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName))) {
      Workbook workbook = new HSSFWorkbook(fs);
      HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
      final int preLastRowNum = append ? sheet.getLastRowNum() : 0;
      int index = 0;
      for (int i = 0; i < list.size(); i++) {
        T item = list.get(i);
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
    return rowLength;
  }
}
