package hello.hellospring.controller;

import hello.hellospring.domain.Board;
import hello.hellospring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping("/board/**")
@RequiredArgsConstructor
public class BoardController {  //IntelliJ, Gradle, SpringBoot, Mybatis, H2-Database, Thymeleaf

    private final BoardService boardService;

    @GetMapping("/view")
    public String viewBoard(Model model, Long boardId){
        model.addAttribute("view", boardService.getBoard(boardId));

        return "/boards/view";
    }

    @GetMapping("/upload")
    public String uploadBoardForm(){
        return "/boards/upload";
    }

    @PostMapping("/upload")
    public String uploadBoard(Board board){
        boardService.uploadBoard(board);
        return "redirect:/board/main";
    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("list", boardService.boardList());
        return "/boards/main";
    }

    @PutMapping("/update")
    public String updateBoard(Board board){
        boardService.updateBoard(board);
        return "redirect:/board/main";
    }

    @DeleteMapping("/delete")
    public String deleteBoard(Long boardId){
        boardService.deleteBoard(boardId);
        return "redirect:/board/main";
    }
}

