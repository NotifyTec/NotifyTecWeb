package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.notifytec.models.Resultado;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class BaseController {

    @Inject
    protected Result result;

    public void returnSuccess(Object object, String warning) {
        List<String> warnings = new ArrayList<>();
        warnings.add(warning);
        returnJson(object, warnings, false);
    }

    public void returnError(Object object, String error) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        returnJson(object, errors, true);
    }

    public void returnSuccess(Object object, List<String> warning) {
        returnJson(object, warning, false);
    }

    public void returnError(Object object, List<String> error) {
        returnJson(object, error, true);
    }

    public void returnSuccess(Object object) {
        returnJson(object, null, false);
    }

    public void returnError(Object object) {
        returnJson(object, null, true);
    }
    
    public void returnError(String error){
        returnError(null, error);
    }

    public void returnError(Object object, Exception ex) {
        returnError(object, ex.getMessage());
    }
    
    private void returnJson(Object object, List<String> message, boolean isError) {
        Resultado jsonResult = new Resultado();
        if (object != null) {
            jsonResult.setResult(object);
        }

        if (message != null && !message.isEmpty()) {
            if (isError) {
                for (String m : message) {
                    jsonResult.addError(m);
                }
            } else {
                for (String m : message) {
                    jsonResult.addWarning(m);
                }
            }
        }
        result.use(Results.json()).withoutRoot().from(jsonResult).recursive().serialize();
    }
}
