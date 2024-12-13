package vttp.batch5.ssf.noticeboard.controllers;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.*;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeSvc;

    @GetMapping
    public String getNotice(Model model){
        model.addAttribute("notice", new Notice());
        return "notice";
    }

    @PostMapping
    public String postNotice(@Valid @ModelAttribute("notice") Notice notice, BindingResult bindings, @RequestBody MultiValueMap<String, String> form, Model model){
        System.out.printf(">>>bindings: %b\n", bindings.hasErrors());
        System.out.printf(">>>notice object: %s/n", notice);
        if(bindings.hasErrors())
            return "notice";
        
        FieldError titleError = new FieldError("notice", "title", "Please include a title");
        FieldError emailError = new FieldError("notice", "email", "must be a well-formed email address");
        FieldError dateError = new FieldError("notice", "postDate", "Date must be in the future");
        FieldError textError= new FieldError("notice", "text", "Mandatory field");
        FieldError categoryError= new FieldError("notice", "categories", "Mandatory field");

        bindings.addError(titleError);
        bindings.addError(emailError);
        bindings.addError(dateError);
        bindings.addError(textError);
        bindings.addError(categoryError);

        noticeSvc.postToNoticeServer(notice);

        return "notice"; 
    }


    
    //task 6 healthcheck
    @GetMapping(path="/status", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> healthCheck(){
       String healthStatus= noticeSvc.getRandomKey();
       if (healthStatus.equals("successful")){

        return ResponseEntity.ok(healthStatus);
       } else {
        String errorResponse="Service Unavailable";
        return ResponseEntity.status(503).body(errorResponse);
       }

    }

   

    



}

