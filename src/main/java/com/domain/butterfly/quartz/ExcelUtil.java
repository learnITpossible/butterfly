package com.domain.butterfly.quartz;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/14
 */
public class ExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static File writeXls(String fileName, List<Map<String, Object>> dataList) throws Exception {

        if (StringUtils.isEmpty(fileName)) throw new Exception("Please fill out target name!");
        fileName += DATE_FORMAT.format(new Date());

        String dir = System.getProperty("java.io.tmpdir");
        File excel = new File(dir, fileName + ".xls");

        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = book.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        List<String> heads = new ArrayList<>();
        dataList.stream().limit(1).forEach(data -> heads.addAll(data.keySet()));

        for (int column = 0; column < heads.size(); column++) {
            HSSFCell cell = row.createCell(column);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(heads.get(column));
            cell.setCellValue(text);
        }

        for (int index = 0; index < dataList.size();) {
            Map<String, Object> map = dataList.get(index++);
            row = sheet.createRow(index);
            for (int column = 0; column < heads.size(); column++) {
                HSSFCell cell = row.createCell(column);
                Object obj = map.get(heads.get(column));
                String text = obj.toString();
                if (StringUtils.isNotEmpty(text)) {
                    Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                    Matcher matcher = p.matcher(text);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(text));
                    } else {
                        HSSFRichTextString richString = new HSSFRichTextString(text);
                        cell.setCellValue(richString);
                    }
                }
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(excel);
            book.write(os);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("close stream...");
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    // throw new RuntimeException("Close failed!");
                }
            }
        }
        return excel;
    }
}
