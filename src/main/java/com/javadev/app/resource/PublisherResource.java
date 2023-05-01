    package com.javadev.app.resource;

import com.javadev.app.dto.MessageDTO;
import com.javadev.app.service.MessageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/publisher")
public class PublisherResource {

    @Autowired
    private MessageQueueService messageService;

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    ResponseEntity<HttpStatus> publisherMessage(@RequestBody MessageDTO message){
        messageService.publishExpense(message);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
