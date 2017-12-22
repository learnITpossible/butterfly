package com.domain.butterfly.constant;

/**
 * TODO describe the file
 * @author lijing
 * @version 1.0.0
 * @since 2017/12/13
 */
public class ReportConfigConst {

    public enum Status {

        READY(1),
        SCHEDULED(2),
        RUNNING(3),
        COMPLETE(4),
        EXCEPTION(5);

        public int value;

        Status(int value) {

            this.value = value;
        }
    }

    public enum RunImmediately {

        NO(0),
        YES(1);

        public int value;

        RunImmediately(int value) {

            this.value = value;

        }
    }

    public enum ExportFileType {

        NULL(-1),
        XLS(0),
        XLSX(1);

        public int value;

        ExportFileType(int value) {

            this.value = value;
        }

        public static ExportFileType valueOf(int value) {

            for (ExportFileType type : ExportFileType.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            return NULL;
        }
    }
}
