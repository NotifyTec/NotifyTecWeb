package br.com.notifytec.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Path;
import javax.annotation.security.PermitAll;

@Controller
public class HomeController
{

   @Inject
   private Result result;

   @Path("/")
   @PermitAll
   public void index()
   {
      result.include("msg", "Message from your controller");
   }
}