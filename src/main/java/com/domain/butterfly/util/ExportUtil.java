package com.domain.butterfly.util;

import com.domain.butterfly.constant.ReportConfigConst;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * com.domain.butterfly.util
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/22
 */
public class ExportUtil {

    public static File export(int exportFileType, String fileName, List<Map<String, Object>> dataList) throws Exception {

        ReportConfigConst.ExportFileType type = ReportConfigConst.ExportFileType.valueOf(exportFileType);
        switch (type) {
            case XLS:
                return ExcelUtil.writeXls(fileName, dataList);
            case XLSX:
                return ExcelUtil.writeXlsx(fileName, dataList);
            default:
                throw new Exception("exportFileType is invalid.");
        }
    }
}
