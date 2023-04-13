package com.trillon.camp.campingHome.board.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trillon.camp.campingHome.board.dto.BoardForm;
import com.trillon.camp.campingHome.board.repository.BoardRepository;
import com.trillon.camp.common.paging.Paging;
import com.trillon.camp.common.reply.Reply;
import com.trillon.camp.util.file.FileInfo;
import com.trillon.camp.util.file.FileRepository;
import com.trillon.camp.util.file.FileUtil;
import com.trillon.camp.util.naverShopping.dto.Item;
import com.trillon.camp.util.naverShopping.service.NaverShoppingSearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final FileUtil fileUtil;
    private final NaverShoppingSearch shopping;

    /**
     * 게시글 등록
     */
    @Override
    public int insertBoard(BoardForm boardForm,List<String> addItemName ,List<MultipartFile> files) throws ParseException, IOException {
        boardRepository.insertBoard(boardForm);
        int bdIdx = boardForm.getBdIdx();  // 해당 insert의 bd_idx값

        // 파일업로드
        FileInfo fileInfo = new FileInfo();
        fileInfo.setGnIdx(boardForm.getBdIdx());
        fileUtil.uploadFile(fileInfo, files);

        //제품등록
        for (String s : addItemName) {
            if(s!=null) {
                Item item = shopping.search(s);
                item.setBdIdx(bdIdx);
                boardRepository.insertItem(item);
            }
        }

        return bdIdx;
    }



    /**
     * 전체 게시글 조회
     */
    @Override
    public List<BoardForm> selectBoardAll() {
        return boardRepository.selectBoardAll();
    }

    /**
     * 댓글 작성
     */
    @Override
    public int insertReply(Reply reply) {
        boardRepository.insertReply(reply);
        return reply.getReIdx();
    }

    /**
     * 전체 댓글 조회
     */
    @Override
    public List<Reply> selectReplyAll(int bdIdx) {
        return boardRepository.selectReplyAll(bdIdx);
    }



    /**
     * 특정 게시글 조회
     */
    @Override
    public Map<String, Object> selectBoardByBdIdx(int bdIdx) {
        BoardForm boardForm = boardRepository.selectBoardByBdIdx(bdIdx);
        List<FileInfo> files =fileRepository.selectFileWithGroup(bdIdx);
        List<Item> item = boardRepository.selectItemAll(bdIdx);
        return Map.of("board",boardForm,"files",files,"item",item);
    }

    /**
     * 페이징 처리
     */
    @Override
    public Map<String, Object> selectBoardList(int page) {

        int total = boardRepository.countAllBoard();  //총 게시물 수

        Paging paging = Paging.builder()
                .cntPerPage(6)
                .currentPage(page)        //현재 페이지는 파라미터로 넘어온 값
                .total(total)
                .blockCnt(10)
                .build();
        return Map.of("boards",boardRepository.selectBoardList(paging),"paging",paging);
    }

    @Override
    public void updateBoard(BoardForm boardForm,List<String> addItemName) throws ParseException, IOException {
        int bdIdx = boardForm.getBdIdx();
        boardRepository.updateBoard(boardForm);


        int prevItemNum = boardRepository.selectItemAll(bdIdx).size();
        int nextItemNum = addItemName.size();


        // 기존보다 아이템 수가 변경되면 삭제하고 추가하는 과정
        if(nextItemNum != prevItemNum){
            boardRepository.deleteItem(bdIdx);
            for (String s : addItemName) {
                if(s!=null) {
                    Item item = shopping.search(s);
                    item.setBdIdx(bdIdx);
                    boardRepository.insertItem(item);
                    System.out.println("새로운 아이템"+item);
                }
            }
        }
        // 기존과 등록된 아이템 수가 같으면 바로 업데이트 실행
        else if(nextItemNum == prevItemNum){
            for (String s : addItemName) {
                if(s!=null) {
                    Item item = shopping.search(s);
                    item.setBdIdx(bdIdx);
                    boardRepository.updateItem(item);
                }
            }
        }





    }

    @Override
    public void deleteBoard(int bdIdx) {
        boardRepository.deleteBoard(bdIdx);
    }

}
