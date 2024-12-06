package com.zaberp.zab.biwtabackend.dto;

public class RequestBodyDto {
    private int zid;
    private int page;
    private int size;
    private String sortBy;
    private boolean ascending;

    public RequestBodyDto() {
    }

    public RequestBodyDto(int zid, int page, int size, String sortBy, boolean ascending) {
        this.zid = zid;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.ascending = ascending;
    }

    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
