package com.example.customtest.api.vo;

/**
 * Created by ZQiong on 2019/3/15.
 */
public class BaseResponse<T> {
    private boolean error;
    private T results;

    public BaseResponse() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
