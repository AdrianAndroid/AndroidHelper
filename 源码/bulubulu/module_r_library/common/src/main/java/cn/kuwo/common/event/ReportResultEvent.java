package cn.kuwo.common.event;

public class ReportResultEvent {
    private String reportTaggetId;   //被举报目标id
    private boolean reportResult;    //举报操作结果

    public String getReportTaggetId() {
        return reportTaggetId;
    }

    public void setReportTaggetId(String reportTaggetId) {
        this.reportTaggetId = reportTaggetId;
    }

    public boolean isReportResult() {
        return reportResult;
    }

    public void setReportResult(boolean reportResult) {
        this.reportResult = reportResult;
    }

    public ReportResultEvent(String reportTaggetId, boolean reportResult) {
        this.reportTaggetId = reportTaggetId;
        this.reportResult = reportResult;
    }

    public static ReportResultEvent buildReportEvent(String id,boolean result){
        ReportResultEvent event = new ReportResultEvent(id, result);
        return event;
    }
}
