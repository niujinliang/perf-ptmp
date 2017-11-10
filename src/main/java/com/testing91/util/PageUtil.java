package com.testing91.util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by tesla on 15/12/7.
 */
public class PageUtil<T> {
    private int currentPage = 1;// 当前页
    private int rowCount = 0;// 总行数,可为0
    private int pageSize = 100;// 页大小
    private int pageCount = 1;// 总页数,当rowCount=0,pageSize=1
    private boolean hasNextPage = false;// 是否有下一页
    private boolean hasPreviousPage = false;// 是否上一页
    private List<T> arrayList = new ArrayList<T>();// 记录集

    public PageUtil(int pageSize, String currentPage) {
        this.pageSize = pageSize;
        if (currentPage == null || "".equals(currentPage)) {
            this.currentPage = 1;
        } else {
            try {
                this.currentPage = Integer.parseInt(currentPage);
            } catch (NumberFormatException e) {
                this.currentPage = 1;
            }
        }
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        if (rowCount < 0) {
            rowCount = 0;
        }
        this.rowCount = rowCount;
        this.getPageCount();
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        if (currentPage >= pageCount) {
            currentPage = pageCount;
        }
        if (currentPage <= 1) {
            currentPage = 1;
        }
        return currentPage;
    }

    public int getPageCount() {
        pageCount = (rowCount + pageSize) / pageSize;
        if (pageCount < 1) {
            pageCount = 1;
        }
        return pageCount;
    }

    public boolean isHasNextPage() {
        if (currentPage >= pageCount) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }
        return hasNextPage;
    }

    public boolean isHasPreviousPage() {
        if (currentPage <= 1) {
            hasPreviousPage = false;
        } else {
            hasPreviousPage = true;
        }
        return hasPreviousPage;
    }

    public int getFirstPage() {
        return 1;
    }

    public int getPreviousPage() {
        if (this.isHasPreviousPage()) {
            return currentPage - 1;
        }
        return currentPage;
    }

    public int getNextPage() {
        if (this.isHasNextPage()) {
            return this.currentPage + 1;
        } else {
            return currentPage;
        }
    }

    public int getLastPage() {
        return pageCount;
    }

    public List<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<T> arrayList) {
        this.arrayList = arrayList;
    }
}