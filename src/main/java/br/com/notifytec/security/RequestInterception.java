package br.com.notifytec.security;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.view.Results;
import br.com.notifytec.models.OperationResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Intercepts
@RequestScoped
public class RequestInterception {

    private final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Inject
    private Result result;

    @Inject
    private HttpServletRequest request;

    @Inject
    private UserSession user;

    @Inject
    private JwtManager jwtManager;

    @AroundCall
    public void around(SimpleInterceptorStack stack) {
        if (user.isLogged()) {
            stack.next();
            return;
        }

        String authorization = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authorization == null || authorization.isEmpty()) {
            unauthorizedAccess("Usuário não autorizado.");
        } else if (!jwtManager.validadeToken(authorization)) {
            unauthorizedAccess("A chave de acesso fornecida não é válida ou está expirada.");
        } else {
            stack.next();
        }
    }

    @Accepts
    public boolean accepts(ControllerMethod method) {
        return !method.containsAnnotation(PermitAll.class);
    }

    private void unauthorizedAccess(String message) {        
        result.use(Results.http()).sendError(401, message);                
        result.nothing();
    }
}
