package com.example.votingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.votingSystem.model.dto.UserResponeDto;
import com.example.votingSystem.model.dto.VoteItemDto;
import com.example.votingSystem.model.dto.VoteItemRequestDto;
import com.example.votingSystem.model.dto.VoteRecordDto;
import com.example.votingSystem.model.dto.VoteRequestDto;
import com.example.votingSystem.service.VotingService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/*
 * WEB REST API
 * ----------------------------------
 * GET     /items            獲取所有投票項目
 * POST    /items            新增投票項目
 * PUT     /items/{itemId}   更新投票項目
 * DELETE  /items/{itemId}   刪除投票項目
 * PUT     /records          新增投票記錄
 * GET     /records/user/    獲取用戶投票記錄
 * */

@RestController
@RequestMapping("/api/voting")
@CrossOrigin(origins = "http://localhost:5173")
public class VotingController {

	@Autowired
    private VotingService votingService;
	
    @GetMapping("/items")
    public ResponseEntity<List<VoteItemDto>> getAllVoteItems() {
        try {
            List<VoteItemDto> items = votingService.getAllVoteItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/items")
    public ResponseEntity<Void> addVoteItem(@RequestBody @Valid VoteItemRequestDto itemName) {
        try {
            votingService.addVoteItem(itemName.getItemName());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateVoteItem(
            @PathVariable Long itemId,
            @RequestBody VoteItemRequestDto itemName) {
        try {
            votingService.updateVoteItem(itemId, itemName.getItemName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteVoteItem(@PathVariable Long itemId) {
        try {
            votingService.deleteVoteItem(itemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/records")
    public ResponseEntity<Void> addVoteRecords(
    		@RequestBody VoteRequestDto voteRequest,
    		HttpSession session
    		) {
        try {
        	UserResponeDto loginDto = (UserResponeDto) session.getAttribute("loginDTO");
            votingService.addVoteRecord(loginDto.getId(), voteRequest.getVoteItemIds());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/records/user/")
    public ResponseEntity<List<Object[]>> getVoteRecordsByUserId(HttpSession session) {
        try {
        	UserResponeDto loginDto = (UserResponeDto) session.getAttribute("loginDTO");
            List<Object[]> records = votingService.getVoteRecordsByUserId(loginDto.getId());
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
}
