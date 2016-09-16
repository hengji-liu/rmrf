package bean;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

    private static final long serialVersionUID = -8741766802354222579L;

    private int pageSize; // how many record in this page

    private int currentPage; // the current page number

    private int totalRecord; // how many record in total

    private int totalPage; // how many pages in total

    private List<T> dataList;

    public Pager() {

    }

    public Pager(int pageSize, int currentPage, int totalRecord,
                 List<T> dataList) {
        super();
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.totalPage = totalPageCal(totalRecord,pageSize);
        this.dataList = dataList;
    }

    private int totalPageCal(int totalRecord, int pageSize) {
        int totalPage = totalRecord / pageSize;
        if (totalRecord % pageSize > 0) ++totalPage;
        return totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
