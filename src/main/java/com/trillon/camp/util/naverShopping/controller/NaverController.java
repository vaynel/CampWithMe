package com.trillon.camp.util.naverShopping.controller;


import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trillon.camp.util.naverShopping.dto.Item;
import com.trillon.camp.util.naverShopping.service.NaverShoppingSearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
public class NaverController {

    private final NaverShoppingSearch shopping;

    @GetMapping("naver/shopping")
    @ResponseBody
    public void getItem(@RequestParam("query") String query) throws IOException, ParseException {
        shopping.search(query);
        Item item = shopping.search(query);
       // log.info("item={}",item);




    }
}