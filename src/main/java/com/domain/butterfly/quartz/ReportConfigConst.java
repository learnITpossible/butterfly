package com.domain.butterfly.quartz;

/**
 * TODO describe the file
 * @author lijing
 * @version 1.0.0
 * @since 2017/12/13
 */
public class ReportConfigConst {

    enum Status {

        READY(1),
        SCHEDULED(2),
        RUNNING(3),
        COMPLETE(4),
        EXCEPTION(5);

        int value;

        Status(int value) {

            this.value = value;
        }
    }

    enum RunImmediately {

        NO(0),
        YES(1);

        int value;

        RunImmediately(int value) {

            this.value = value;

        }
    }
}
