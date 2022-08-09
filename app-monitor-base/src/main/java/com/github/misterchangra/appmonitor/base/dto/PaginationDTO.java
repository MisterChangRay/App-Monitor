package com.github.misterchangra.appmonitor.base.dto;

import java.util.List;

public class PaginationDTO<T> {
    private List<T> list;
    private int total;


    public List<T> getList() {
        return list;
    }

    public PaginationDTO setList(List<T> list) {
        this.list = list;

        return this;
    }

    public int getTotal() {
        return total;
    }

    public PaginationDTO setTotal(int total) {
        this.total = total;

        return this;
    }
}
