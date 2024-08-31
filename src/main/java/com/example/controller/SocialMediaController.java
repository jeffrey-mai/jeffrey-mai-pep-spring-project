package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){ 
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account acc){
        return accountService.register(acc);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acc){
        return accountService.login(acc);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message msg){
        return messageService.addMessage(msg);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMsgs(){
        return messageService.getAllMsgs();
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMsgByMessageId(@PathVariable("messageId") Integer messageId){
        return messageService.getMsgByMessageId(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMsgByMessageId(@PathVariable("messageId") Integer messageId){
        return messageService.deleteMsgByMessageId(messageId);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMsgByMessageId(@PathVariable("messageId") Integer messageId, @RequestBody Map<String, String> editMsg){
        return messageService.patchMsgByMessageId(messageId, editMsg.get("messageText"));
    }

    // @GetMapping("/accounts/{accountId}/messages")
    // public ResponseEntity<List<Message>> getAllMsgsByAccountId(@PathVariable("accountId") Integer accountId){
    //     return messageService.getAllMsgsByAccountId(accountId);
    // }
}
