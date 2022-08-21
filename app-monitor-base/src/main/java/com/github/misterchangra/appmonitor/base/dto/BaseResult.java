package com.github.misterchangra.appmonitor.base.dto;

import java.util.List;

public class BaseResult<T> {
    private int code;
    private T data;
    private String sign;
    private String msg;


    public static BaseResult fail(int code, String msg) {
        return new BaseResult(code, msg);
    }

    public String getSign() {
        return sign;
    }

    public BaseResult<T> setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public BaseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <K> BaseResult<PaginationDTO<K>> page(List<K> list) {
        BaseResult<PaginationDTO<K>> res = new BaseResult<>(0);

        PaginationDTO r = new PaginationDTO<>();
        r.setList(list);
        r.setTotal(list.size());
        res.setData(r);

        return res;
    }

    public static <K> BaseResult<PaginationDTO<K>> page(List<K> list, int total) {
        BaseResult<PaginationDTO<K>> res = new BaseResult<>(0);

        PaginationDTO r = new PaginationDTO<>();
        r.setList(list);
        r.setTotal(total);
        res.setData(r);

        return res;

    }

    public BaseResult(int code) {
        this.code = code;
    }
    public BaseResult(int code, String msg) {
            this.code = code;
            this.msg = msg;
    }

    public static BaseResult success() {
        return new BaseResult(0);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
