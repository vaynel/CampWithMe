package com.trillon.camp.campingHome.board.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.trillon.camp.campingHome.board.dto.BoardForm;
import com.trillon.camp.common.reply.Reply;


public interface BoardService {
    int insertBoard(BoardForm boardForm,List<String> addItemName,List<MultipartFile> files) throws ParseException, IOException;

    int insertReply(Reply reply);

    List<BoardForm> selectBoardAll();

    List<Reply> selectReplyAll(int bdIdx);

    Map<String, Object> selectBoardByBdIdx(int bdIdx);

    Map<String, Object> selectBoardList(int page);

    void updateBoard(BoardForm boardForm,List<String> addItemName) throws ParseException, IOException;

    void deleteBoard(int bdIdx);


}