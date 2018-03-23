package com.tu.poi.transform;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * @author Tu enum@foxmail.com.
 */
public class PoiWriter<T> {

  private static final Logger logger = LoggerFactory.getLogger(PoiWriter.class);

  private FieldExtractor extractor;
  private String fileName;
  private Boolean append = Boolean.TRUE;


  public PoiWriter(String fileName, FieldExtractor extractor) {
    Assert.notNull(fileName, "fileName must be non-null");
    Assert.notNull(extractor, "extractor must be non-null");
    this.fileName = fileName;
    this.extractor = extractor;
  }

  public PoiWriter append(Boolean append) {
    this.append = append;
    return this;
  }

  public int write(List<T> list) {
    int rowLength = 0;
    logger.info("文件{}开始写入", fileName);
    try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName))) {
      Workbook workbook = new HSSFWorkbook(fs);
      HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
      final int preLastRowNum = append ? sheet.getLastRowNum() : 0;
      logger.info("文件{},上次行数为{}", fileName, preLastRowNum);
      int index = 0;
      for (int i = 0; i < list.size(); i++) {
        T item = list.get(i);
        index++;
        Row row = sheet.createRow(preLastRowNum + index);
        row.createCell(0).setCellValue(preLastRowNum + index);
        Object[] values = extractor.extract(item);
        for (int j = 0; j < values.length; j++) {
          if (!ObjectUtils.isEmpty(values[j])) {
            if (values[j] instanceof Double) {
              row.createCell(j + 1).setCellValue((Double) values[j]);
            } else if (values[j] instanceof Date) {
              row.createCell(j + 1).setCellValue((Date) values[j]);
            } else if (values[j] instanceof Calendar) {
              row.createCell(j + 1).setCellValue((Calendar) values[j]);
            } else if (values[j] instanceof RichTextString) {
              row.createCell(j + 1).setCellValue((RichTextString) values[j]);
            } else if (values[j] instanceof Boolean) {
              row.createCell(j + 1).setCellValue((Boolean) values[j]);
            } else {
              row.createCell(j + 1).setCellValue(String.valueOf(values[j]));
            }
          } else {
            continue;
          }
        }
      }
      FileOutputStream fileOut = new FileOutputStream(fileName);
      workbook.write(fileOut);
      IOUtils.closeQuietly(fileOut);
      logger.info("文件{}写入完成,{}-{}行", fileName, preLastRowNum + 1, preLastRowNum + index + 1);
    } catch (IOException e) {
      logger.error("excel-{}处理出错", fileName, e);
    }

    return rowLength;
  }
//
//  public int write(String fileName, String[] names, List<T> list, boolean append) {
//    int rowLength = 0;
//    logger.info("文件{}开始写入", fileName);
//    try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName))) {
//      Workbook workbook = new HSSFWorkbook(fs);
//      HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
//      final int preLastRowNum = append ? sheet.getLastRowNum() : 0;
//      logger.info("文件{},上次行数为{}", fileName, preLastRowNum);
//      int index = 0;
//      for (int i = 0; i < list.size(); i++) {
//        T item = list.get(i);
//        index++;
//        Row row = sheet.createRow(preLastRowNum + index);
//        row.createCell(0).setCellValue(preLastRowNum + index);
//        BeanWrapper bw = new BeanWrapperImpl(item);
//        for (int j = 0; j < names.length; j++) {
//          Object value = bw.getPropertyValue(names[j]);
//          if (!ObjectUtils.isEmpty(value)) {
//            if (value instanceof Double) {
//              row.createCell(j + 1).setCellValue((Double) value);
//            } else if (value instanceof Date) {
//              row.createCell(j + 1).setCellValue((Date) value);
//            } else if (value instanceof Calendar) {
//              row.createCell(j + 1).setCellValue((Calendar) value);
//            } else if (value instanceof RichTextString) {
//              row.createCell(j + 1).setCellValue((RichTextString) value);
//            } else if (value instanceof Boolean) {
//              row.createCell(j + 1).setCellValue((Boolean) value);
//            } else {
//              row.createCell(j + 1).setCellValue(String.valueOf(value));
//            }
//          } else {
//            continue;
//          }
//        }
//      }
//      FileOutputStream fileOut = new FileOutputStream(fileName);
//      workbook.write(fileOut);
//      IOUtils.closeQuietly(fileOut);
//      logger.info("文件{}写入完成,{}-{}行", fileName, preLastRowNum + 1, preLastRowNum + index + 1);
//    } catch (IOException e) {
//      logger.error("excel-{}处理出错", fileName, e);
//    }
//
//    return rowLength;
//  }
}
