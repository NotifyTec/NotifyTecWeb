package br.com.notifytec.models;

import java.util.ArrayList;
import java.util.List;

public class JsonResult<T> {

    private List<String> messages;
    private boolean success;
    private T result;

    public JsonResult() {
        this.messages = new ArrayList<>();
        this.success = true;
    }

    public JsonResult(T result) {
        this();
        setResult(result);
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return this.result;
    }

    public void addWarning(String warning) {
        this.messages.add(warning);
    }

    public void addError(String error) {
        this.addWarning(error);
        this.success = false;
    }

}
