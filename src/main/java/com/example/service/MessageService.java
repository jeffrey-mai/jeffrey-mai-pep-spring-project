package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Message> addMessage(Message msg){
        Optional<Account> existingAcc = accountRepository.findById(msg.getPostedBy());

        if(!existingAcc.isPresent() || msg.getMessageText().isEmpty() || msg.getMessageText().length() >= 255){
            return ResponseEntity.status(400).body(null);
        }

        Message newMsg = messageRepository.save(msg);
        return ResponseEntity.status(200).body(newMsg);
    }

    public ResponseEntity<List<Message>> getAllMsgs(){
        // messageRepository.finalAll() returns a Iterator<T> -> convert Iterator<T> to List<T>
        List<Message> existingMsgs = StreamSupport.stream(messageRepository.findAll().spliterator(), false).collect(Collectors.toList());
        if(existingMsgs.size() == 0) return ResponseEntity.status(200).body(null);
        return ResponseEntity.status(200).body(existingMsgs);
    }

    public ResponseEntity<Message> getMsgByMessageId(Integer messageId){
        Optional<Message> msg = messageRepository.findById(messageId);
        if(!msg.isPresent()) return ResponseEntity.status(200).body(null);
        return ResponseEntity.status(200).body(msg.get());
    }

    public ResponseEntity<Integer> deleteMsgByMessageId(Integer messageId){
        Optional<Message> msg = messageRepository.findById(messageId);
        if(!msg.isPresent()) return ResponseEntity.status(200).body(null);
        messageRepository.deleteById(messageId);
        return ResponseEntity.status(200).body(1);
    }

    public ResponseEntity<Integer> patchMsgByMessageId(Integer messageId, String editMsg){
        Optional<Message> msg = messageRepository.findById(messageId);
        
        if(!msg.isPresent() || editMsg.isBlank() || editMsg.isEmpty() || editMsg.length() >= 255){
            return ResponseEntity.status(400).body(null);
        }

        msg.get().setMessageText(editMsg);
        messageRepository.save(msg.get());
        return ResponseEntity.status(200).body(1);
    }

    // public ResponseEntity<List<Message>> getAllMsgsByAccountId(Integer accountId){
    //     List<Message> existingMsgs = messageRepository.getAllMsgsByAccountId(accountId);
    //     if(existingMsgs.size() == 0) return ResponseEntity.status(200).body(null);
    //     return ResponseEntity.status(200).body(existingMsgs);
    // }
}
